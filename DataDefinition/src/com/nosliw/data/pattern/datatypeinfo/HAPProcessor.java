package com.nosliw.data.pattern.datatypeinfo;

import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.pattern.HAPPatternProcessor;
import com.nosliw.common.test.HAPResultTestCase;
import com.nosliw.common.test.HAPTestCase;
import com.nosliw.common.test.HAPTestEnv;
import com.nosliw.common.test.HAPAssert;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.data.info.HAPDataTypeInfo;

public class HAPProcessor implements HAPPatternProcessor{

	@Override
	public String getName() {
		return HAPConstant.CONS_PATTERN_DATATYPEINFO;
	}

	@Override
	public Object parse(String text, Object data) {
		if(HAPBasicUtility.isStringEmpty(text))	return null;
		
    	String[] parts = HAPNamingConversionUtility.parsePartlInfos(text);
		String type = parts[0];
		String categary = HAPDataTypeManager.DEFAULT_TYPE;
		if(parts.length>=2)   categary = parts[1];
		return new HAPDataTypeInfo(categary, type);
	}

	@Override
	public String compose(Object obj, Object data) {
		HAPDataTypeInfo dataTypeInfo = (HAPDataTypeInfo)obj;
		return HAPNamingConversionUtility.cascadePart(dataTypeInfo.getType(), dataTypeInfo.getCategary());
	}

	@HAPTestCase()
	public boolean test(HAPResultTestCase result, HAPTestEnv testEnv) {
		System.out.println("Start Data type info test.....");
		try{
			String testStr1 = "type"+HAPConstant.CONS_SEPERATOR_PART+"categary";
			HAPDataTypeInfo info1 = (HAPDataTypeInfo)this.parse(testStr1, null);
			String info1Str = this.compose(info1, null);
			
			HAPAssert.assertEquals(testStr1, info1Str, result);
			HAPAssert.assertEquals(info1.getType(), "type", result);
			HAPAssert.assertEquals(info1.getCategary(), "categary", result);
		}
		catch(Exception e){
			result.addException(e);
		}
		
		return result.isSuccess();
	}

}
