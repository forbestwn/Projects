package com.nosliw.data;

import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.pattern.HAPPatternProcessor;
import com.nosliw.common.test.HAPResultTestCase;
import com.nosliw.common.test.HAPTestCase;
import com.nosliw.common.test.HAPTestEnv;
import com.nosliw.common.test.HAPAssert;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.info.HAPDataTypeInfo;

public class HAPPatternProcessorDataTypeInfo implements HAPPatternProcessor{

	@Override
	public String getName() {
		return HAPConstant.CONS_PATTERN_DATATYPEINFO;
	}

	@Override
	public Object parse(String text, Object data) {
		String categary = null;
		String type = null;
		if(!HAPBasicUtility.isStringEmpty(text)){
	    	String[] parts = HAPNamingConversionUtility.splitText(text, HAPConstant.CONS_SEPERATOR_PART);
			type = parts[0];
			if(HAPBasicUtility.isStringEmpty(type))  type = null;
			if(parts.length>=2)   categary = parts[1];
			if(HAPBasicUtility.isStringEmpty(categary))  categary = null;
		}
		
		return new HAPDataTypeInfo(categary, type);
	}

	@Override
	public String compose(Object obj, Object data) {
		HAPDataTypeInfo dataTypeInfo = (HAPDataTypeInfo)obj;
		return HAPNamingConversionUtility.cascadeTexts(dataTypeInfo.getType(), dataTypeInfo.getCategary(), HAPConstant.CONS_SEPERATOR_PART);
	}

	@HAPTestCase(description="DataTypeInfo:  type{{CONS_SEPERATOR_PART}}categary, ")
	public void dataTypeInfo(HAPResultTestCase result, HAPTestEnv testEnv) {
		String input;
		String output;
		
		input = "type"+HAPConstant.CONS_SEPERATOR_PART+"categary";
		output = input;
		this.test(input, "type", "categary", output, result);

		input = HAPConstant.CONS_SEPERATOR_PART+"categary";
		output = input;
		this.test(input, null, "categary", output, result);
		
		input = "type"+HAPConstant.CONS_SEPERATOR_PART;
		output = input;
		this.test(input, "type", null, output, result);
		
		input = "type";
		output = "type"+HAPConstant.CONS_SEPERATOR_PART;
		this.test(input, "type", null, output, result);

		input = null;
		output = ""+HAPConstant.CONS_SEPERATOR_PART+"";
		this.test(input, null, null, output, result);

		input = "";
		output = ""+HAPConstant.CONS_SEPERATOR_PART+"";
		this.test(input, null, null, output, result);
	}
	
	private void test(String input, String expectType, String expectCategary, String output, HAPResultTestCase result){
		try{
			HAPDataTypeInfo dataTypeInfo = (HAPDataTypeInfo)this.parse(input, null);
			String dataTypeInfoCompse = this.compose(dataTypeInfo, null);
			HAPAssert.assertEquals(dataTypeInfoCompse, output, result);
			HAPAssert.assertEquals(dataTypeInfo.getType(), expectType, result);
			HAPAssert.assertEquals(dataTypeInfo.getCategary(), expectCategary, result);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
