package com.nosliw.data.simple.integer;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.application.core.data.HAPSimpleData;
import com.nosliw.application.utils.HAPConstant;

public class HAPIntegerData extends HAPSimpleData{

	private int m_data;
	
	HAPIntegerData(int data, HAPDataType dataType){
		super(dataType);
		this.m_data = data;
	}

	@Override
	public HAPData cloneData() {
		return new HAPIntegerData(this.m_data, this.getDataType());
	}

	@Override
	public String toDataStringValue(String format) {
		if(format.equals(HAPConstant.FORMAT_JSON)){
			return String.valueOf(this.m_data);
		}
		return null;
	}
	
	public int getValue(){ return this.m_data;}
	public void setValue(int data){this.m_data = data;}
	
}
