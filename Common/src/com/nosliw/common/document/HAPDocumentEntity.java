package com.nosliw.common.document;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.interpolate.HAPPatternProcessorDocExpression;
import com.nosliw.common.interpolate.HAPPatternProcessorDocVariable;
import com.nosliw.common.pattern.HAPPatternManager;
import com.nosliw.common.utils.HAPBasicUtility;

/*
 * this is parent class for class that store data information for document
 */
public class HAPDocumentEntity {
	//store object value
	private Map<String, Object> m_values;
	//store string value for present in document 
	private Map<String, String> m_strValues;
	
	public HAPDocumentEntity(){
		this.m_values = new LinkedHashMap<String, Object>();
		this.m_strValues = new LinkedHashMap<String, String>();
	}
		
	public HAPDocumentEntity(Map<String, Object> values){
		this();
		for(String name : values.keySet()){
			this.setValue(name, values.get(name));
		}
		this.m_values.putAll(values);
	}

	public Object getValue(String name){	return this.m_values.get(name);	}
	public String getStringValue(String name){ return this.m_strValues.get(name); }
	
	public void setValue(String name, Object value){
		this.m_values.put(name, value);
		this.m_strValues.put(name, this.valueToString(value));
	}
	
	public Set<String> getProperties(){		return this.m_values.keySet();	}
	
	/*
	 * update document element by replacing the varible with variable value
	 */
	public void updateDocument(Map<String, String> vars, Object baseObj){
		for(String name : this.getProperties()){
			String fieldValue = this.getStringValue(name);
			if(HAPBasicUtility.isStringNotEmpty(fieldValue)){
				//replace variable
				fieldValue = (String)HAPPatternManager.getInstance().getPatternProcessor(HAPPatternProcessorDocVariable.class.getName()).parse(fieldValue, vars);
				//replace expression
				fieldValue = (String)HAPPatternManager.getInstance().getPatternProcessor(HAPPatternProcessorDocExpression.class.getName()).parse(fieldValue, baseObj);
			}
			this.setValue(name, fieldValue);
		}
	}
	
	private String valueToString(Object value){
		if(value==null)  return null;
		return value.toString();
	}
}
