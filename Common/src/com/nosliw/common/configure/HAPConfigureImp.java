package com.nosliw.common.configure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import com.nosliw.common.interpolate.HAPExpressionProcessor;
import com.nosliw.common.interpolate.HAPInterpolateOutput;
import com.nosliw.common.interpolate.HAPInterpolateUtility;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.common.utils.HAPSegmentParser;

/*
 * node for tree structure configuration
 * configuration has two type of value: configure value and variable value
 * 		configure value has tree structure: every configure is identified by path
 * 		variable value has no tree structure, it is identified by single name
 * configure value cannot refered to another configure, 
 * configure value can only refer to variable value	
 * 		in that case, configure search the variable name within its own scope
 * 		if not found, then search the variable name within parent scrope
 * 	when clone a configure from a child configure, the variables in new configure are all the variables from child configure to root parent configure by variable in child override parent
 *  for external party that use this configure, variable within this configure root can also be a configure item
 *  so that when a party get configure value from configure
 *  	it search within configure first
 *  	if not found, then search within variables in root configure 
 */
public class HAPConfigureImp extends HAPConfigureItem implements HAPConfigure{
	private String m_startToken = HAPConstant.CONS_SEPERATOR_VARSTART;
	private String m_endToken = HAPConstant.CONS_SEPERATOR_VAREND;
	
	//child configure nested
	private Map<String, HAPConfigureImp> m_childConfigures;
	//configure values
	private Map<String, HAPConfigureValueString> m_values;
	
	//all variables within this configur node
	private Map<String, HAPVariableValue> m_variables;
	
	/*
	 * empty constructor
	 */
	HAPConfigureImp(){
		this.m_childConfigures = new LinkedHashMap<String, HAPConfigureImp>();
		this.m_values = new LinkedHashMap<String, HAPConfigureValueString>();
		this.m_variables = new LinkedHashMap<String, HAPVariableValue>();
	}
	
	@Override
	public HAPConfigureValue getConfigureValue(String attr){  
		return (HAPConfigureValue)this.getConfigureItemByPath(attr, HAPConfigureItem.VALUE);
	}


	/*
	 * this method is for external party to find property from this configure
	 */
	public String getStringValue(String name){
		HAPConfigureValue configureValue = this.getConfigureValue(name);
		if(configureValue!=null)  return configureValue.toString();
		else{
			HAPVariableValue varValue = this.getVariableValue(name);
			if(varValue!=null)  return varValue.toString();
			else  return null;
		}
	}
	
	HAPConfigureItem getConfigureItemByPath(String path, String type){
		HAPConfigureItem out = this;
		if(HAPBasicUtility.isStringNotEmpty(path)){
			int index = path.indexOf(HAPConstant.CONS_SEPERATOR_PATH);
			if(index==-1){
				//single
				switch(type){
					case HAPConfigureItem.CONFIGURE:
						out = this.getChildConfigure(path);	
						break;
					case HAPConfigureItem.VALUE:
						out = this.getChildConfigureValue(path);
						break;
					case HAPConfigureItem.VARIABLE:
						out = this.getChildVaraibleValue(path);
				}
			}
			else{
				//multiple path
				HAPConfigureImp subConf = this.getChildConfigure(path.substring(0, index));
				if(subConf==null)  out = null;
				else	out = subConf.getChildConfigure(path.substring(index+1));
			}
		}
		return out;
	}
	
	public void addConfigureItem(String name, String value){
		String isGlobalVar = HAPConfigureUtility.isGlobalVariable(name);
		if(isGlobalVar==null){
			//normal configure
			this.addConfigureItem(name, value, false);
		}
		else{
			//global variable definition
			this.addConfigureItem(isGlobalVar, value, true);
		}
	}

	private void addConfigureItem(String name, String value, boolean isVariable) {
		HAPSegmentParser nameSegs = new HAPSegmentParser(name);
		if(nameSegs.getSegmentSize()>1){
			//if name have path structure, then have configurable nested
			String childName = nameSegs.next();
			HAPConfigureImp childConf = (HAPConfigureImp)this.getChildConfigure(childName);
			if(childConf==null){
				childConf = new HAPConfigureImp();
				this.addChildConfigure(childName, childConf);
			}
			childConf.addConfigureItem(nameSegs.getRestPath(), value);
		}
		else{
			if(isVariable){
				//variable
				this.addVariableValue(name, value);
			}
			else{
				//configure value
				this.addChildConfigureValue(name, value);
			}
		}
	}
	
	void addVariableValue(String name, String value){
		HAPVariableValue varValue = new HAPVariableValue(value);
		this.addVariableValue(name, varValue);
	}
	
	private void addVariableValue(String name, HAPVariableValue value){
		value.setParent(this);
		this.m_variables.put(name, value);
	}
	
	private void addChildConfigureValue(String name, String value){ 
		this.addChildConfigureValue(name, new HAPConfigureValueString(value)); 
	}
	private void addChildConfigureValue(String name, HAPConfigureValueString value){
		value.setParent(this);
		this.m_values.put(name, value); 
	}

	private void addChildConfigure(String name, HAPConfigureImp configure){
		configure.setParent(this);
		this.m_childConfigures.put(name, configure); 
	}
	
	/*
	 * resolve all configureation
	 */
	public void resolve(){
		for(String name : this.m_variables.keySet()){
			HAPResolvableConfigureItem item = this.m_variables.get(name);
			this.resolveConfigureItem(item, true);
		}
		for(String name : this.m_values.keySet()){
			HAPResolvableConfigureItem item = this.m_values.get(name);
			this.resolveConfigureItem(item, true);
		}
		for(String name : this.m_childConfigures.keySet()){
			HAPConfigureImp childConfigure = this.m_childConfigures.get(name);
			childConfigure.resolve();
		}
	}
	
	/*
	 * resolve content
	 */
//	public String resolve(String content){
//		HAPInterpolateOutput interpolateOut = this.resolve(content, false);
//		return interpolateOut.getOutput();
//	}
	
	private HAPInterpolateOutput resolve(String content, final boolean resursive){
		final HAPConfigureImp that = this;
		HAPInterpolateOutput interpolateOut = HAPInterpolateUtility.process(content, this, this.m_startToken, this.m_endToken, new HAPExpressionProcessor(){
			@Override
			public String process(String expression, Object object) {
				HAPConfigureImp configure = (HAPConfigureImp)object;
				//through variable
				HAPVariableValue value = configure.getVariableValue(expression);
				if(value==null)  return null;
				else{
					//found variable
					if(value.resolved())  return value.getResolvedString();
					else{
						if(resursive){
							value.getParent().resolveConfigureItem(value, resursive);
							if(value.resolved())  return value.getResolvedString();
							else return null;
						}
						else{
							return null;
						}
					}
				}
			}
		});
		return interpolateOut;
	}
	
	/*
	 * resolve resolvable item (configure value or variable value)
	 */
	private void resolveConfigureItem(HAPResolvableConfigureItem resolvableItem, final boolean resursive){
		HAPInterpolateOutput interpolateOut = this.resolve(resolvableItem.getRawString(), resursive);
		if(interpolateOut.isResolved()){
			resolvableItem.resolved(interpolateOut.getOutput());
		}
	}

	/*
	 * get variable value according to its name
	 * if value does not exits in current configure scope, try to search in parent scope
	 */
	private HAPVariableValue getVariableValue(String name){
		HAPVariableValue out = this.m_variables.get(name);
		if(out==null){
			HAPConfigureImp parent = this.getParent();
			if(parent!=null){
				//if not find in current scope, try to find in parent
				out = parent.getVariableValue(name);
			}
		}
		return out;
	}


	private HAPConfigureImp getChildConfigure(String childName){return this.m_childConfigures.get(childName);}
	private HAPConfigureValueString getChildConfigureValue(String childName){ return this.m_values.get(childName);  }
	private HAPVariableValue getChildVaraibleValue(String childName){ return this.m_variables.get(childName);}
	private Map<String, HAPConfigureImp> getChildConfigurables(){return this.m_childConfigures;}
	private Map<String, HAPConfigureValueString> getChildConfigureValues(){ return this.m_values;  }
	
	/*
	 * sort out variable values for this configureation base
	 */
	private void mergeVariables(Map<String, HAPVariableValue> vars){
		HAPConfigureImp parent = this.getParent();
		if(parent!=null){
			parent.mergeVariables(vars);
		}
		for(String name : this.m_variables.keySet()){
			vars.put(name, (HAPVariableValue)this.m_variables.get(name).clone());
		}
	}
	
	/*
	 * create a new configure with same configure, value and merged variable
	 */
	public HAPConfigureImp cloneConfigure(){
		HAPConfigureImp out = new HAPConfigureImp();
		
		Map<String, HAPVariableValue> vars = new LinkedHashMap<String, HAPVariableValue>();
		this.mergeVariables(vars);
		for(String name : vars.keySet()){
			out.addVariableValue(name, (HAPVariableValue)vars.get(name));
		}
		
		//clone configure
		for(String name : this.getChildConfigurables().keySet()){
			out.addChildConfigure(name, (HAPConfigureImp)this.getChildConfigurables().get(name).clone());
		}
		
		//clone configure values
		for(String name : this.getChildConfigureValues().keySet()){
			out.addChildConfigureValue(name, (HAPConfigureValueString)this.getChildConfigureValues().get(name).clone());
		}
		return out;
	}
	
	public HAPConfigureItem clone(){
		HAPConfigureImp out = new HAPConfigureImp();
		out.setParent(this);getParent();
		for(String name : this.m_variables.keySet()){
			out.addVariableValue(name, this.m_variables.get(name).clone());
		}
		
		//clone configure
		for(String name : this.getChildConfigurables().keySet()){
			out.addChildConfigure(name, (HAPConfigureImp)this.getChildConfigurables().get(name).clone());
		}
		
		//clone configure values
		for(String name : this.getChildConfigureValues().keySet()){
			out.addChildConfigureValue(name, (HAPConfigureValueString)this.getChildConfigureValues().get(name).clone());
		}
		return out;
	}
	
	@Override
	public HAPConfigure cloneChildConfigure(String path) {
		HAPConfigureImp configure = (HAPConfigureImp)this.getConfigureItemByPath(path, HAPConfigureItem.CONFIGURE);
		if(configure==null)  return null;
		else{
			return configure.cloneConfigure();
		}
	}
	
	HAPConfigureImp importFromValueMap(Map<String, String> valueMap){
		for(String name : valueMap.keySet()){
			this.addConfigureItem(name, valueMap.get(name));
		}
		return this;
	}

	public HAPConfigureImp importFromProperty(String file, Class class1){
		InputStream input = HAPFileUtility.getInputStreamOnClassPath(class1, file);
		return this.importFromProperty(input);
	}
	
	/*
	 * read configure items from property as file
	 */
	public HAPConfigureImp importFromProperty(File file){
		try {
			FileInputStream input = new FileInputStream(file);
			this.importFromProperty(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
		
	/*
	 * read configure items from property as inputstream
	 */
	public HAPConfigureImp importFromProperty(InputStream input){
		try {
			Properties prop = new HAPOrderedProperties();
			prop.load(input);
			this.importFromProperty(prop);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public void importFromProperty(Properties prop){
		Enumeration<?> e = prop.propertyNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String value = prop.getProperty(name).trim();
			this.addConfigureItem(name, value);
		}		
	}

	public HAPConfigureImp merge(HAPConfigure configuration, boolean ifNewConf, boolean isHard){
		HAPConfigureImp out = this;
		if(ifNewConf){
			out = (HAPConfigureImp)this.clone();
		}
		out.merge((HAPConfigureImp)configuration, isHard);
		return out;
	}

	private void merge(HAPConfigureImp configuration, boolean isHard){
		if(configuration==null)  return;
		//merge child configurs
		for(String attr : configuration.getChildConfigurables().keySet()){
			HAPConfigureImp configure = this.getChildConfigure(attr);
			HAPConfigureImp mergeConfigure = configuration.getChildConfigure(attr);
			if(configure!=null)			configure.merge(mergeConfigure, isHard);
			else{
				this.addChildConfigure(attr, (HAPConfigureImp)mergeConfigure.clone());
			}
		}

		//merge child configure values
		for(String attr : configuration.getChildConfigureValues().keySet()){
			HAPConfigureValue configureValue = this.getChildConfigureValue(attr);
			HAPConfigureValueString mergeConfigureValue = configuration.getChildConfigureValue(attr);
			if(isHard || configureValue==null)  this.addChildConfigureValue(attr, mergeConfigureValue.clone());
		}
		
		//merge variable values
		for(String name : configuration.m_variables.keySet()){
			HAPVariableValue var = this.m_variables.get(name);
			HAPVariableValue mergeVar = configuration.m_variables.get(name);
			if(isHard || var==null)  this.addVariableValue(name, mergeVar);
		}
	}
	
	@Override
	public String toString(){ return HAPJsonUtility.formatJson(this.toStringValue(HAPConstant.CONS_SERIALIZATION_JSON));}

	@Override
	public String toStringValue(String format) {
		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
		
		jsonMap.put("variables", HAPJsonUtility.getMapObjectJson(this.m_variables));
		jsonMap.put("childValues", HAPJsonUtility.getMapObjectJson(this.getChildConfigureValues()));
		jsonMap.put("childConfigurables", HAPJsonUtility.getMapObjectJson(this.getChildConfigurables()));
		return HAPJsonUtility.getMapJson(jsonMap);
	}

	@Override
	String getType() {
		return HAPConfigureItem.CONFIGURE;
	}
}
