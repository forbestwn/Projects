package com.nosliw.data.simple.text;

import java.util.List;

import com.nosliw.application.core.data.HAPDataOperation;
import com.nosliw.application.core.data.HAPDataTypeManager;
import com.nosliw.application.core.data.HAPOperation;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.data.simple.integer.HAPInteger;
import com.nosliw.data.simple.integer.HAPIntegerData;

public class HAPTextOperation extends HAPDataOperation{

	private HAPText m_textDef;
	HAPTextOperation(HAPText textDef, HAPDataTypeManager dataTypeMan){
		super(dataTypeMan);
		this.m_textDef = textDef;
	}
	
	@HAPOperation(in = { "simple:text" }, out = "simple:text")
	public HAPTextData reverse(List parms){
		HAPTextData textData = (HAPTextData)parms.get(0);
		String text = textData.getText();
		text = text + "operation";
		return new HAPTextData(text, this.m_textDef);
	}
	
	@HAPOperation(in = { "simple:text" }, out = "simple:text")
	public String reverse_javascript(){
		String script = "return parms[0]+'operation';";
		return script;
	}

	@HAPOperation(in = { "simple:text" }, out = "simple:integer")
	public HAPIntegerData length(List parms){
		HAPTextData textData = (HAPTextData)parms.get(0);
		String text = textData.getText();
		HAPInteger intDataType = (HAPInteger)this.getDataType(HAPConstant.CATEGARY_SIMPLE, HAPConstant.DATATYPE_SIMPLE_INTEGER);
		return intDataType.createDataByValue(text.length());
	}
	
	@HAPOperation(in = { "simple:text" }, out = "simple:integer")
	public String length_javascript(){
		String script = "return parms[0].length;";
		return script;
	}
	
	
}
