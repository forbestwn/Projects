package com.nosliw.data.simple.text;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.application.core.data.HAPSimpleData;
import com.nosliw.application.utils.HAPConstant;

public class HAPTextData extends HAPSimpleData{

	private String m_text;
	
	HAPTextData(String text, HAPDataType dataType){
		super(dataType);
		this.m_text = text;
	}

	@Override
	public HAPData cloneData() {
		return new HAPTextData(this.m_text, this.getDataType());
	}

	@Override
	public String toDataStringValue(String format) {
		if(format.equals(HAPConstant.FORMAT_JSON)){
			return ""+this.m_text+"";
		}
		return null;
	}
	
	public String getText(){ return this.m_text;}
	public void setText(String text){this.m_text = text;}
	
	@Override
	public String toString(){
		return this.m_text;
	}
}
