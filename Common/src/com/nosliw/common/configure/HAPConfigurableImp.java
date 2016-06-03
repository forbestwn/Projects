package com.nosliw.common.configure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import com.nosliw.common.serialization.HAPStringable;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.common.utils.HAPSegmentParser;

public class HAPConfigurableImp  implements HAPConfigurable, HAPStringable{
	//child configure nested
	Map<String, HAPConfigurableImp> m_childConfigures;
	//configure values
	Map<String, HAPConfigureValue> m_values;
	
	Map<String, String> m_globalConfigures;
	
	private String TOKEN_VAR_START = "\\{\\{";
	private String TOKEN_VAR_END = "\\}\\}";
	
	/*
	 * empty constructor
	 */
	HAPConfigurableImp(){
		this.m_childConfigures = new LinkedHashMap<String, HAPConfigurableImp>();
		this.m_values = new LinkedHashMap<String, HAPConfigureValue>();
		this.m_globalConfigures = new LinkedHashMap<String, String>();
	}

	public HAPConfigurableImp importFromValueMap(Map<String, String> valueMap){
		for(String name : valueMap.keySet()){
			this.addStringValue(name, valueMap.get(name));
		}
		return this;
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
	
	/*
	 * read configure items from property as file
	 */
	public HAPConfigurableImp importFromFile(File file){
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
	public HAPConfigurableImp importFromFile(InputStream input){
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

	/*
	 * process string value
	 * 		replace variable place holder with variable value
	 */
	public String processStringValue(String value){
		for(String var : this.m_globalConfigures.keySet()){
			value = value.replaceAll(TOKEN_VAR_START+var+TOKEN_VAR_END, this.m_globalConfigures.get(var));
		}
		return value;
	}
	
	private void addStringValue(String name, String value) {
		HAPSegmentParser nameSegs = new HAPSegmentParser(name);
		if(nameSegs.getSegmentSize()>1){
			//if name have path structure, then have configurable nested
			String childName = nameSegs.next();
			HAPConfigurableImp childConf = (HAPConfigurableImp)this.getChildConfigurable(childName);
			if(childConf==null){
				childConf = new HAPConfigurableImp();
				this.addChildConfigure(childName, childConf);
			}
			childConf.addStringValue(nameSegs.getRestPath(), value);
		}
		else{
			this.addChildConfigureValue(name, value);
		}
	}

	@Override
	public HAPConfigurable softMerge(HAPConfigurable configuration, boolean ifNewConf){
		HAPConfigurableImp out = this;
		if(ifNewConf){
			out = (HAPConfigurableImp)this.clone();
		}
		out.softMerge((HAPConfigurableImp)configuration);
		return out;
	}

	@Override
	public HAPConfigurable hardMerge(HAPConfigurable configuration, boolean ifNewConf){
		HAPConfigurableImp out = this;
		if(ifNewConf){
			out = (HAPConfigurableImp)this.clone();
		}
		out.hardMerge((HAPConfigurableImp)configuration);
		return out;
	}

	private void softMerge(HAPConfigurableImp configuration){
		if(configuration==null)  return;
		//merge child configurs
		for(String attr : configuration.getChildConfigurables().keySet()){
			HAPConfigurableImp configure = this.getChildConfigurable(attr);
			HAPConfigurableImp mergeConfigure = configuration.getChildConfigurables().get(attr);
			if(configure!=null)			configure.softMerge(mergeConfigure);
			else			this.addChildConfigure(attr, (HAPConfigurableImp)mergeConfigure.clone());
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

	private void hardMerge(HAPConfigurableImp configuration){
		if(configuration==null)  return;
		//merge child configurs
		for(String attr : configuration.getChildConfigurables().keySet()){
			HAPConfigurableImp configure = this.getChildConfigurable(attr);
			HAPConfigurableImp mergeConfigure = configuration.getChildConfigurables().get(attr);
			if(configure!=null)			configure.hardMerge(mergeConfigure);
			else			this.addChildConfigure(attr, (HAPConfigurableImp)mergeConfigure.clone());
		}

		//merge child configure values
		for(String attr : configuration.getChildConfigureValues().keySet()){
			HAPConfigureValue configureValue = this.getChildConfigureValue(attr);
			HAPConfigureValue mergeConfigureValue = configuration.getChildConfigureValues().get(attr);
			this.addChildConfigureValue(attr, mergeConfigureValue.clone());
		}
	}

	public HAPConfigurable getConfigurable(String attr) {	
		int index = attr.indexOf(HAPConstant.CONS_SEPERATOR_PATH);
		if(index==-1){
			//single
			return this.getChildConfigurable(attr);	
		}
		else{
			//multiple path
			HAPConfigurableImp subConf = this.getChildConfigurable(attr.substring(0, index));
			if(subConf==null)   return null;
			else	return subConf.getConfigurable(attr.substring(index+1));
		}
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
			HAPConfigurable subConf = this.getChildConfigurable(attr.substring(0, index));
			if(subConf==null)   return null;
			else	return subConf.getConfigureValue(attr.substring(index+1));
		}
	}

	private HAPConfigurableImp getChildConfigurable(String childName){return this.m_childConfigures.get(childName);}
	private HAPConfigureValue getChildConfigureValue(String childName){ return this.m_values.get(childName);  }
	private Map<String, HAPConfigurableImp> getChildConfigurables(){return this.m_childConfigures;}
	private Map<String, HAPConfigureValue> getChildConfigureValues(){ return this.m_values;  }
	private void addGlobalValue(String name, String value){		this.m_globalConfigures.put(name, value);	}
	private void addChildConfigure(String name, HAPConfigurableImp configure){  this.m_childConfigures.put(name, configure); }
	private void addChildConfigureValue(String name, HAPConfigureValue value){ this.m_values.put(name, value); }
	private void addChildConfigureValue(String name, String value){ this.addChildConfigureValue(name, new HAPConfigureValueString(value)); }
	
	@Override
	public HAPConfigurable clone(){
		HAPConfigurableImp out = new HAPConfigurableImp();
		for(String name : this.m_globalConfigures.keySet()){
			out.m_globalConfigures.put(name, this.m_globalConfigures.get(name));
		}
		
		for(String name : this.getChildConfigurables().keySet()){
			out.addChildConfigure(name, (HAPConfigurableImp)this.getChildConfigurables().get(name).clone());
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
		
		jsonMap.put("variables", HAPJsonUtility.getMapObjectJson(this.m_globalConfigures));
		jsonMap.put("childValues", HAPJsonUtility.getMapObjectJson(this.getChildConfigureValues()));
		jsonMap.put("childConfigurables", HAPJsonUtility.getMapObjectJson(this.getChildConfigurables()));
		return HAPJsonUtility.getMapJson(jsonMap);
	}
}
