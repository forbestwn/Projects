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
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.common.utils.HAPSegmentParser;

public class HAPConfigurableImp  implements HAPConfigurable, HAPStringable{

	Map<String, Object> m_configures;

	/*
	 * empty constructor
	 */
	public HAPConfigurableImp(){
		this.m_configures = new LinkedHashMap<String, Object>();
	}

	/*
	 * constructor from map
	 */
	public HAPConfigurableImp(Map<String, Object> valueMap){
		this.m_configures = new LinkedHashMap<String, Object>();
		for(String name : valueMap.keySet()){
			Object o = valueMap.get(name);
			if(o instanceof String){
				this.setStringValue(name, (String)o);
			}
			else{
				this.setValue(name, o);
			}
		}
	}

	/*
	 * read configure items from property as file
	 */
	public HAPConfigurableImp(File file){
		try {
			FileInputStream input = new FileInputStream(file);
			this.m_configures = new LinkedHashMap<String, Object>();
			Properties prop = new Properties();
			prop.load(input);
			this.readFromProperty(prop);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * read configure items from property as inputstream
	 */
	public HAPConfigurableImp(InputStream input){
		try {
			this.m_configures = new LinkedHashMap<String, Object>();
			Properties prop = new Properties();
			prop.load(input);
			this.readFromProperty(prop);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readFromProperty(Properties prop){
		Enumeration<?> e = prop.propertyNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String value = prop.getProperty(name).trim();
			this.setStringValue(name, value);
		}		
	}
	
	private void setStringValue(String name, String value){
		if(value.startsWith("[") && value.endsWith("]")){
			//value is array
			HAPSegmentParser valueSegs = new HAPSegmentParser(value.substring(1, value.length()-1), HAPConstant.CONS_SEPERATOR_ELEMENT);
			this.setValue(name, valueSegs.getSegments());
		}
		else{
			this.setValue(name, value);
		}
	}
	
	private void setValue(String name, Object value) {
		HAPSegmentParser nameSegs = new HAPSegmentParser(name);
		if(nameSegs.getSegmentSize()>1){
			//if name have path structure, then have configurable nested
			String childName = nameSegs.next();
			HAPConfigurableImp childConf = (HAPConfigurableImp)this.getConfigurableValue(childName);
			if(childConf==null){
				childConf = new HAPConfigurableImp();
				this.setValue(childName, childConf);
			}
			childConf.setValue(nameSegs.getRestPath(), value);
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
	public HAPConfigurable mergeWith(HAPConfigurable configuration) {
		HAPConfigurableImp out = new HAPConfigurableImp();
		
		for(String attr : configuration.getConfigureItems()){
			Object value = this.getValue(attr);
			if(value!=null){
				if(value instanceof HAPConfigurable){
					//if item is configurable, then do merge 
					out.setValue(attr, ((HAPConfigurable)value).mergeWith((HAPConfigurable)configuration.getValue(attr)));
				}
				else{
					out.setValue(attr, value);
				}
			}
			else{
				out.setValue(attr, configuration.getValue(attr));
			}
		}
		return out;
	}
	
	@Override
	public String toString(){ return HAPJsonUtility.formatJson(this.toStringValue(HAPConstant.CONS_SERIALIZATION_JSON));}

	@Override
	public String toStringValue(String format) {
		return HAPJsonUtility.getMapObjectJson(this.m_configures);
	}
}
