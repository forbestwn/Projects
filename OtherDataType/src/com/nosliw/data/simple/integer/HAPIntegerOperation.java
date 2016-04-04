package com.nosliw.data.simple.integer;

import java.util.List;

import com.nosliw.application.core.data.HAPDataOperation;
import com.nosliw.application.core.data.HAPDataTypeManager;
import com.nosliw.application.core.data.HAPOperation;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.data.simple.bool.HAPBool;
import com.nosliw.data.simple.bool.HAPBoolData;

public class HAPIntegerOperation extends HAPDataOperation{
	private HAPInteger m_integerDef;
	
	HAPIntegerOperation(HAPInteger integerDef, HAPDataTypeManager dataTypeMan){
		super(dataTypeMan);
		this.m_integerDef = integerDef;
	}

	@HAPOperation(in = { "simple:integer", "simple:integer" }, out = "simple:bool")
	public HAPBoolData largerThan(List parms){
		HAPIntegerData integerData1 = (HAPIntegerData)parms.get(0);
		HAPIntegerData integerData2 = (HAPIntegerData)parms.get(1);
		boolean out = integerData1.getValue() > integerData2.getValue();
		HAPBool boolDataType = (HAPBool)this.getDataType(HAPConstant.CATEGARY_SIMPLE, HAPConstant.DATATYPE_SIMPLE_BOOL);
		return boolDataType.createDataByValue(out);
	}
	
	@HAPOperation(in = { "simple:integer", "simple:integer" }, out = "simple:bool")
	public String largerThan_javascript(){
		String script = "return parms[0] > parms[1];";
		return script;
	}	
}
