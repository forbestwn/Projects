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
import com.nosliw.common.interpolate.HAPPatternProcessorDocVariable;
import com.nosliw.common.serialization.HAPStringable;
import com.nosliw.common.utils.HAPConstant;
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
 *  for party that use this configure, variable within this configure root can also be a configure item
 *  so that when a party get configure value from configure
 *  	it search within configure first
 *  	if not found, then search within variables in root configure 
 */
public class HAPConfigureImp extends HAPConfigureItem implements HAPConfiguration, HAPStringable{
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
	
	/*
	 * resolve resolvable item (configure value or variable value)
	 */
	void resolveInternal(HAPResolvableConfigureItem resolvableItem, final boolean resursive){
		final HAPConfigureImp that = this;
		HAPInterpolateOutput interpolateOut = HAPInterpolateUtility.process(resolvableItem.getRawString(), this, this.m_startToken, this.m_endToken, new HAPExpressionProcessor(){
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
							value.getParent().resolveInternal(value, resursive);
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
		
	}

	public String resolve(String content){
		return null;
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


	String resolveThroughConfigure(String content, boolean resursive){
		return null;
	}
	
	

	HAPConfigureImp importFromValueMap(Map<String, String> valueMap){
		for(String name : valueMap.keySet()){
			this.addConfigureItem(name, valueMap.get(name));
		}
		return this;
	}

	/*
	 * read configure items from property as file
	 */
	public HAPConfigureImp importFromFile(File file){
		try {
			FileInputStream input = new FileInputStream(file);
			this.importFromFile(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
		
	/*
	 * read configure items from property as inputstream
	 */
	public HAPConfigureImp importFromFile(InputStream input){
		try {
			Properties prop = new HAPOrderedProperties();
			prop.load(input);
			this.readFromProperty(prop);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	private void readFromProperty(Properties prop){
		Enumeration<?> e = prop.propertyNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String value = prop.getProperty(name).trim();
			this.addConfigureItem(name, value);
		}		
	}

	
	public void addConfigureItem(String name, String value){
		value = processStringValue(value);
		String isGlobalVar = HAPConfigureUtility.isGlobalVariable(name);
		if(isGlobalVar==null){
			//normal configure
			this.addStringValue(name, value);
		}
		else{
			//global variable definition
			this.addGlobalValue(isGlobalVar, value);
		}
	}
	
	public void addGlobalValue(String name, String value){		this.m_variables.put(name, value);	}

	


	
	/*
	 * process string value
	 * 		replace variable place holder with variable value
	 */
	public String processStringValue(String value){
		return (String)new HAPPatternProcessorDocVariable().parse(value, this.m_variables);
	}
	
	private void addStringValue(String name, String value) {
		HAPSegmentParser nameSegs = new HAPSegmentParser(name);
		if(nameSegs.getSegmentSize()>1){
			//if name have path structure, then have configurable nested
			String childName = nameSegs.next();
			HAPConfigureImp childConf = (HAPConfigureImp)this.getChildConfigure(childName);
			if(childConf==null){
				childConf = new HAPConfigureImp();
				this.addChildConfigure(childName, childConf);
			}
			childConf.addStringValue(nameSegs.getRestPath(), value);
		}
		else{
			this.addChildConfigureValue(name, value);
		}
	}

	@Override
	public HAPConfiguration cloneChildConfigure(String attr) {	
		HAPConfigureImp out = null;
		int index = attr.indexOf(HAPConstant.CONS_SEPERATOR_PATH);
		if(index==-1){
			//single
			out = this.getChildConfigure(attr);	
		}
		else{
			//multiple path
			HAPConfigureImp subConf = this.getChildConfigure(attr.substring(0, index));
			if(subConf==null)   return null;
			out = (HAPConfigureImp)subConf.cloneChildConfigure(attr.substring(index+1));
		}
		if(out!=null){
			//add global variable to output configure
			out.getGlobalVaribles().putAll(this.getGlobalVaribles());
		}
		return out;
	}
	
	@Override
	public HAPConfigureValue getConfigureValue(String attr){  
		int index = attr.indexOf(HAPConstant.CONS_SEPERATOR_PATH);
		if(index==-1){
			//single
			return this.getChildConfigureValue(attr);	
		}
		else{
			//multiple path
			HAPConfiguration subConf = this.getChildConfigure(attr.substring(0, index));
			if(subConf==null)   return null;
			else	return subConf.getConfigureValue(attr.substring(index+1));
		}
	}

	@Override
	public HAPConfiguration softMerge(HAPConfiguration configuration, boolean ifNewConf){
		HAPConfigureImp out = this;
		if(ifNewConf){
			out = (HAPConfigureImp)this.clone();
		}
		out.softMerge((HAPConfigureImp)configuration);
		return out;
	}

	@Override
	public HAPConfiguration hardMerge(HAPConfiguration configuration, boolean ifNewConf){
		HAPConfigureImp out = this;
		if(ifNewConf){
			out = (HAPConfigureImp)this.clone();
		}
		out.hardMerge((HAPConfigureImp)configuration);
		return out;
	}

	private void softMerge(HAPConfigureImp configuration){
		if(configuration==null)  return;
		//merge child configurs
		for(String attr : configuration.getChildConfigurables().keySet()){
			HAPConfigureImp configure = this.getChildConfigure(attr);
			HAPConfigureImp mergeConfigure = configuration.getChildConfigurables().get(attr);
			if(configure!=null)			configure.softMerge(mergeConfigure);
			else			this.addChildConfigure(attr, (HAPConfigureImp)mergeConfigure.clone());
		}

		//merge child configure values
		for(String attr : configuration.getChildConfigureValues().keySet()){
			HAPConfigureValue configureValue = this.getChildConfigureValue(attr);
			if(configureValue==null){
				HAPConfigureValue mergeConfigureValue = configuration.getChildConfigureValues().get(attr);
				this.addChildConfigureValue(attr, mergeConfigureValue.clone());
			}
		}
	}

	private void hardMerge(HAPConfigureImp configuration){
		if(configuration==null)  return;
		//merge child configurs
		for(String attr : configuration.getChildConfigurables().keySet()){
			HAPConfigureImp configure = this.getChildConfigure(attr);
			HAPConfigureImp mergeConfigure = configuration.getChildConfigurables().get(attr);
			if(configure!=null)			configure.hardMerge(mergeConfigure);
			else			this.addChildConfigure(attr, (HAPConfigureImp)mergeConfigure.clone());
		}

		//merge child configure values
		for(String attr : configuration.getChildConfigureValues().keySet()){
			HAPConfigureValue configureValue = this.getChildConfigureValue(attr);
			HAPConfigureValue mergeConfigureValue = configuration.getChildConfigureValues().get(attr);
			this.addChildConfigureValue(attr, mergeConfigureValue.clone());
		}
	}

	private HAPConfigureImp getChildConfigure(String childName){return this.m_childConfigures.get(childName);}
	private HAPConfigureValue getChildConfigureValue(String childName){ return this.m_values.get(childName);  }
	private Map<String, HAPConfigureImp> getChildConfigurables(){return this.m_childConfigures;}
	private Map<String, HAPConfigureValue> getChildConfigureValues(){ return this.m_values;  }
	public Map<String, String> getGlobalVaribles(){ return this.m_variables; }
	private void addChildConfigure(String name, HAPConfigureImp configure){  this.m_childConfigures.put(name, configure); }
	private void addChildConfigureValue(String name, HAPConfigureValue value){ this.m_values.put(name, value); }
	private void addChildConfigureValue(String name, String value){ this.addChildConfigureValue(name, new HAPConfigureValueString(value)); }
	
	@Override
	public HAPConfiguration clone(){
		HAPConfigureImp out = new HAPConfigureImp();
		for(String name : this.m_variables.keySet()){
			out.m_variables.put(name, this.m_variables.get(name));
		}
		
		for(String name : this.getChildConfigurables().keySet()){
			out.addChildConfigure(name, (HAPConfigureImp)this.getChildConfigurables().get(name).clone());
		}
		
		for(String name : this.getChildConfigureValues().keySet()){
			out.addChildConfigureValue(name, this.getChildConfigureValues().get(name));
		}
		return out;
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
}
