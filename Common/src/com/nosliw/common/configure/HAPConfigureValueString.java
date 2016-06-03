package com.nosliw.common.configure;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPSegmentParser;

public class HAPConfigureValueString implements HAPConfigureValue{

	private String m_value;
	
	//calulated attr
	private String[] m_stringArrays;
	
	public HAPConfigureValueString(String value){
		this.m_value = value;
		
		if(value!=null){
			//if the string value represent array, build array instead
			if(value.startsWith(HAPConstant.CONS_SEPERATOR_ARRAYSTART) && value.endsWith(HAPConstant.CONS_SEPERATOR_ARRAYEND)){
				//value is array
				HAPSegmentParser valueSegs = new HAPSegmentParser(value.substring(1, value.length()-1), HAPConstant.CONS_SEPERATOR_ELEMENT);
				this.m_stringArrays = valueSegs.getSegments();
			}
		}
	}
	
	@Override
	public String getStringValue() {
		return this.m_value;
	}

	@Override
	public Boolean getBooleanValue() {
		if(this.m_value==null)   return Boolean.FALSE;
		return Boolean.valueOf(this.m_value);
	}

	@Override
	public Integer getIntegerValue() {
		if(this.m_value==null)   return Integer.valueOf(0);
		return Integer.valueOf(this.m_value);
	}

	@Override
	public Float getFloatValue() {
		if(this.m_value==null)   return Float.valueOf(0);
		return Float.valueOf(this.m_value);
	}

	@Override
	public String[] getArrayValue(){
		return this.m_stringArrays;
	}
	
	public HAPConfigureValue clone(){
		return new HAPConfigureValueString(this.m_value);
	}

	@Override
	public String toStringValue(String format) {
		return this.m_value;
	}
	
	@Override
	public String toString(){
		return this.m_value;
	}
}
