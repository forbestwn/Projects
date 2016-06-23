package com.nosliw.common.configure;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPSegmentParser;

public class HAPConfigureValueString extends HAPResolvableConfigureItem implements HAPConfigureValue{

	//calulated attr
	private String[] m_stringArrays;
	
	public HAPConfigureValueString(String value){
		super(value);
		
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
	String getType() {
		return HAPConfigureItem.VALUE;
	}
	
	private String getValue(){
		return this.getResolvedString();
	}
	
	@Override
	public String getStringValue() {
		return this.getValue();
	}

	@Override
	public Boolean getBooleanValue() {
		if(this.getValue()==null)   return Boolean.FALSE;
		return Boolean.valueOf(this.getValue());
	}

	@Override
	public Integer getIntegerValue() {
		if(this.getValue()==null)   return Integer.valueOf(0);
		return Integer.valueOf(this.getValue());
	}

	@Override
	public Float getFloatValue() {
		if(this.getValue()==null)   return Float.valueOf(0);
		return Float.valueOf(this.getValue());
	}

	@Override
	public String[] getArrayValue(){
		return this.m_stringArrays;
	}
	
	public HAPConfigureValue clone(){
		return new HAPConfigureValueString(this.getValue());
	}

	@Override
	public String toStringValue(String format) {
		return this.getValue();
	}
	
	@Override
	public String toString(){
		return this.getValue();
	}

	@Override
	protected void setResolvedContent(String resolvedContent) {	}

}
