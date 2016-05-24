package com.nosliw.common.configure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.nosliw.common.serialization.HAPStringable;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.common.utils.HAPSegmentParser;

public class HAPConfigurableImp  implements HAPConfigurable, HAPStringable{

	Map<String, Object> m_configures;
	Map<String, String> m_globalConfigures;
	
	private String TOKEN_VAR_START = "\\{\\{";
	private String TOKEN_VAR_END = "\\}\\}";
	
	private String KEY_VARIABLE_GLOBAL = "global";
	
	/*
	 * empty constructor
	 */
	public HAPConfigurableImp(){
		this.m_configures = new LinkedHashMap<String, Object>();
		this.m_globalConfigures = new LinkedHashMap<String, String>();
		
		this.init();
	}

	protected void init(){
		//import global variables first
		InputStream input = HAPFileUtility.getInputStreamOnClassPath(HAPConfigurableImp.class, "global.properties");
		if(input!=null)  this.importFromFile(input);
	}
	
	public HAPConfigurableImp importFromMap(Map<String, Object> valueMap){
		this.m_configures = new LinkedHashMap<String, Object>();
		for(String name : valueMap.keySet()){
			Object o = valueMap.get(name);
			if(o instanceof String){
				this.addStringValue(name, (String)o);
			}
			else{
				this.addValue(name, o);
			}
		}
		return this;
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
			value = processStringValue(value);
			String isGlobalVar = this.isGlobalVariable(name);
			if(isGlobalVar==null){
				//normal configure
				this.addStringValue(name, value);
			}
			else{
				//global variable definition
				this.m_globalConfigures.put(isGlobalVar, value);
			}
		}		
	}
	
	private String isGlobalVariable(String name){
		if(!name.startsWith(KEY_VARIABLE_GLOBAL)){
			return null;
		}
		else{
			return name.substring(KEY_VARIABLE_GLOBAL.length()+1);
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
	
	@Override
	public void addStringValue(String name, String value){
		if(value.startsWith("[") && value.endsWith("]")){
			//value is array
			HAPSegmentParser valueSegs = new HAPSegmentParser(value.substring(1, value.length()-1), HAPConstant.CONS_SEPERATOR_ELEMENT);
			this.addValue(name, valueSegs.getSegments());
		}
		else{
			this.addValue(name, value);
		}
	}
	
	private void addValue(String name, Object value) {
		HAPSegmentParser nameSegs = new HAPSegmentParser(name);
		if(nameSegs.getSegmentSize()>1){
			//if name have path structure, then have configurable nested
			String childName = nameSegs.next();
			HAPConfigurableImp childConf = (HAPConfigurableImp)this.getConfigurableValue(childName);
			if(childConf==null){
				childConf = new HAPConfigurableImp();
				this.addValue(childName, childConf);
			}
			childConf.addValue(nameSegs.getRestPath(), value);
		}
		else{
			this.m_configures.put(name, value);
		}
	}

	@Override
	public Set<String> getConfigureItems() {	return this.m_configures.keySet();	}

	@Override
	public Object getValue(String attr){	
		int index = attr.indexOf(HAPConstant.CONS_SEPERATOR_PATH);
		if(index==-1){
			//single
			return this.m_configures.get(attr); 	
		}
		else{
			//multiple path
			HAPConfigurable subConf = (HAPConfigurable)this.m_configures.get(attr.substring(0, index));
			if(subConf==null)   return null;
			else{
				return subConf.getValue(attr.substring(index+1));
			}
		}
	}
	
	@Override
	public String getStringValue(String attr) {
		Object out = this.getValue(attr);
		if(out==null)   return null;
		return out.toString();
	}

	@Override
	public Boolean getBooleanValue(String attr) {
		Object out = this.getValue(attr);
		if(out==null)   return null;
		if(out instanceof String)  return Boolean.valueOf((String)out);
		else if(out instanceof Boolean)  return (Boolean)out;
		return null;
	}

	@Override
	public Integer getIntegerValue(String attr) {
		Object out = this.getValue(attr);
		if(out==null)   return null;
		if(out instanceof String)  return Integer.valueOf((String)out);
		else if(out instanceof Integer)  return (Integer)out;
		return null;
	}

	@Override
	public Float getFloatValue(String attr) {
		Object out = this.getValue(attr);
		if(out==null)   return null;
		if(out instanceof String)  return Float.valueOf((String)out);
		else if(out instanceof Float)  return (Float)out;
		return null;
	}

	@Override
	public HAPConfigurable getConfigurableValue(String attr) {
		Object out = this.getValue(attr);
		if(out==null)   return null;
		if(out instanceof HAPConfigurable)  return (HAPConfigurable)out;
		return null;
	}

	@Override
	public String[] getArrayValue(String attr){
		Object out = this.getValue(attr);
		if(out==null)   return null;
		if(out instanceof String[])  return (String[])out;
		return null;
	}
	
	@Override
	public HAPConfigurable softMerge(HAPConfigurable configuration) {
		HAPConfigurableImp out = new HAPConfigurableImp();
		
		for(String attr : configuration.getConfigureItems()){
			Object value = this.getValue(attr);
			if(value!=null){
				if(value instanceof HAPConfigurable){
					//if item is configurable, then do merge 
					out.addValue(attr, ((HAPConfigurable)value).softMerge((HAPConfigurable)configuration.getValue(attr)));
				}
				else{
					out.addValue(attr, value);
				}
			}
			else{
				out.addValue(attr, configuration.getValue(attr));
			}
		}
		return out;
	}
	
	@Override
	public String toString(){ return HAPJsonUtility.formatJson(this.toStringValue(HAPConstant.CONS_SERIALIZATION_JSON));}

	@Override
	public String toStringValue(String format) {
		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
		
		jsonMap.put("variables", HAPJsonUtility.getMapObjectJson(this.m_globalConfigures));
		jsonMap.put("configures", HAPJsonUtility.getMapObjectJson(this.m_configures));
		return HAPJsonUtility.getMapJson(jsonMap);
	}
}
