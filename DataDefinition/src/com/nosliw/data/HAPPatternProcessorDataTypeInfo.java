package com.nosliw.data;

import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.pattern.HAPPatternProcessor;
import com.nosliw.common.test.HAPResultTestCase;
import com.nosliw.common.test.HAPTestCase;
import com.nosliw.common.test.HAPTestEnv;
import com.nosliw.common.test.HAPTestItem;
import com.nosliw.common.test.HAPTestItemDescription;
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
		this.testItem(new HAPTestItemDescriptionImp(input, "type", "categary", output), result, testEnv);

		input = HAPConstant.CONS_SEPERATOR_PART+"categary";
		output = input;
		this.testItem(new HAPTestItemDescriptionImp(input, null, "categary", output), result, testEnv);
		
		input = "type"+HAPConstant.CONS_SEPERATOR_PART;
		output = input;
		this.testItem(new HAPTestItemDescriptionImp(input, "type", null, output), result, testEnv);
		
		input = "type";
		output = "type"+HAPConstant.CONS_SEPERATOR_PART;
		this.testItem(new HAPTestItemDescriptionImp(input, "type", null, output), result, testEnv);

		input = null;
		output = ""+HAPConstant.CONS_SEPERATOR_PART+"";
		this.testItem(new HAPTestItemDescriptionImp(input, null, null, output), result, testEnv);

		input = "";
		output = ""+HAPConstant.CONS_SEPERATOR_PART+"";
		this.testItem(new HAPTestItemDescriptionImp(input, null, null, output), result, testEnv);
	}
	
	/*
	 * run test item
	 */
	private void testItem(HAPTestItemDescriptionImp testItem, HAPResultTestCase result, HAPTestEnv testEnv){
		//update variable placeholder
		testEnv.updateDocument(testItem);
		HAPResultTestCase temp = new HAPResultTestCase(null);
		try{
			HAPDataTypeInfo dataTypeInfo = (HAPDataTypeInfo)this.parse(testItem.m_input, null);
			String dataTypeInfoCompse = this.compose(dataTypeInfo, null);
			HAPAssert.assertEquals(dataTypeInfoCompse, testItem.m_output, result);
			HAPAssert.assertEquals(dataTypeInfo.getType(), testItem.m_outType, result);
			HAPAssert.assertEquals(dataTypeInfo.getCategary(), testItem.m_outCategary, result);
		}
		catch(Exception e){
			e.printStackTrace();
			temp.addException(e);
		}
		temp.addTestLog(new HAPTestItem(testItem, temp.isSuccess()));
		result.importResult(temp);
	}

	/*
	 * test item description
	 */
	class HAPTestItemDescriptionImp extends HAPTestItemDescription{
		public HAPTestItemDescriptionImp(String input, String type, String categary, String output){
			this.m_input = input;
			this.m_outType = type;
			this.m_outCategary = categary;
			this.m_output = output;
		}
		
		public String m_input;
		public String m_output;
		public String m_outCategary;
		public String m_outType;
		@Override
		public String log() {
			return this.m_input + "----->" + "type:" + this.m_outType + "  /  " + "categary" +  this.m_outCategary;
		}
	}
}
