package com.nosliw.data.simple.bool;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.application.core.data.HAPSimpleData;
import com.nosliw.application.utils.HAPConstant;

public class HAPBoolData  extends HAPSimpleData{

	private boolean m_data;
	
	HAPBoolData(boolean data, HAPDataType dataType){
		super(dataType);
		this.m_data = data;
	}

	@Override
	public HAPData cloneData() {
		return new HAPBoolData(this.m_data, this.getDataType());
	}

	@Override
	public String toDataStringValue(String format) {
		if(format.equals(HAPConstant.FORMAT_JSON)){
			return this.m_data?"true":"false";
		}
		return null;
	}
	
	public boolean getValue(){ return this.m_data;}
}
