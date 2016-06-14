package com.nosliw.data;

import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.pattern.HAPPatternProcessorImp;
import com.nosliw.common.test.HAPResultTestCase;
import com.nosliw.common.test.HAPTestCase;
import com.nosliw.common.test.HAPTestEnv;
import com.nosliw.common.test.HAPTestItemDescription;
import com.nosliw.common.test.HAPAssert;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.info.HAPDataTypeInfo;

public class HAPPatternProcessorDataTypeInfo extends HAPPatternProcessorImp{

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

	@HAPTestCase(name="${getName()}", description="type{{CONS_SEPERATOR_PART}}categary, ")
	public void test(HAPResultTestCase result, HAPTestEnv testEnv) {
		//prepare test items
		HAPTestItemDescriptionImp[] testItems = {
				new HAPTestItemDescriptionImp(this, "type"+HAPConstant.CONS_SEPERATOR_PART+"categary", "type", "categary","type"+ HAPConstant.CONS_SEPERATOR_PART+"categary"),
				new HAPTestItemDescriptionImp(this, HAPConstant.CONS_SEPERATOR_PART+"categary", null, "categary", HAPConstant.CONS_SEPERATOR_PART+"categary"),
				new HAPTestItemDescriptionImp(this, "type"+HAPConstant.CONS_SEPERATOR_PART, "type", null, "type"+HAPConstant.CONS_SEPERATOR_PART),
				new HAPTestItemDescriptionImp(this, "type", "type", null, "type"+HAPConstant.CONS_SEPERATOR_PART),
				new HAPTestItemDescriptionImp(this, null, null, null, ""+HAPConstant.CONS_SEPERATOR_PART+""),
				new HAPTestItemDescriptionImp(this, "", null, null, ""+HAPConstant.CONS_SEPERATOR_PART+""),
		};

		for(HAPTestItemDescriptionImp testItem : testItems){
			testItem.test(result, testEnv);
		}
	}
	
	/*
	 * test item description
	 */
	class HAPTestItemDescriptionImp extends HAPTestItemDescription{
		public final static String ATTR_OUTTYPE = "outType";
		public final static String ATTR_OUTCATEGARY = "outCategary";
		
		private HAPPatternProcessorDataTypeInfo m_testObj;
		
		public HAPTestItemDescriptionImp(HAPPatternProcessorDataTypeInfo testObj, String input, String type, String categary, String output){
			this.setInput(input);
			this.setOutput(output);
			this.setValue(ATTR_OUTTYPE, type);
			this.setValue(ATTR_OUTCATEGARY, categary);
			this.m_testObj = testObj;
		}
		
		@Override
		public String log() {
			return this.getInputStr() + "----->" + "type:" + this.getStringValue(ATTR_OUTTYPE) + "  /  " + "categary:" +  this.getStringValue(ATTR_OUTCATEGARY);
		}
		
		@Override
		public void doTest(HAPResultTestCase testResult, HAPTestEnv testEnv){
			HAPDataTypeInfo dataTypeInfo = (HAPDataTypeInfo)m_testObj.parse(this.getInputStr(), null);
			String dataTypeInfoCompse = m_testObj.compose(dataTypeInfo, null);
			HAPAssert.assertEquals(dataTypeInfoCompse, this.getOputputStr(), testResult);
			HAPAssert.assertEquals(dataTypeInfo.getType(), this.getValue(HAPTestItemDescriptionImp.ATTR_OUTTYPE), testResult);
			HAPAssert.assertEquals(dataTypeInfo.getCategary(), this.getValue(HAPTestItemDescriptionImp.ATTR_OUTCATEGARY), testResult);
		}
	}
}
