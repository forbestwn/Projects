package com.nosliw.common.configure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import com.nosliw.common.interpolate.HAPPatternProcessorDocVariable;
import com.nosliw.common.serialization.HAPStringable;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.common.utils.HAPSegmentParser;

public class HAPConfigureImp  implements HAPConfiguration, HAPStringable{
	//child configure nested
	private Map<String, HAPConfigureImp> m_childConfigures;
	//configure values
	private Map<String, HAPConfigureValue> m_values;
	
	//all variables used to replace placeholders
	private Map<String, String> m_variables;
	
	//the parent configure
	private HAPConfigureImp m_parent = null;
	
	/*
	 * empty constructor
	 */
	HAPConfigureImp(){
		this.m_childConfigures = new LinkedHashMap<String, HAPConfigureImp>();
		this.m_values = new LinkedHashMap<String, HAPConfigureValue>();
		this.m_variables = new LinkedHashMap<String, String>();
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
	public void addGlobalValue(String name, String value){		this.m_variables.put(name, value);	}
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
