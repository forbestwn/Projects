package com.nosliw.data.basic.number;

import com.nosliw.data.HAPData;
import com.nosliw.data.HAPDataOperation;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.data.HAPOperationInfoAnnotation;
import com.nosliw.data.HAPScriptOperationInfoAnnotation;
import com.nosliw.data.basic.bool.HAPBooleanData;

public class HAPIntegerOperation extends HAPDataOperation{

	HAPIntegerOperation(HAPDataTypeManager dataTypeMan){
		super(dataTypeMan);
	}

	@HAPOperationInfoAnnotation(in = { "integer:simple", "integer:simple" }, out = "boolean:simple", description = "This is operation on Integer data type to compare two Integer")
	public HAPBooleanData largerThan(HAPData[] parms){
		HAPIntegerData integerData1 = (HAPIntegerData)parms[0];
		HAPIntegerData integerData2 = (HAPIntegerData)parms[1];
		boolean out = integerData1.getValue() > integerData2.getValue();
		return HAPDataTypeManager.BOOLEAN.createDataByValue(out);
	}
	
	@HAPScriptOperationInfoAnnotation(dependent="boolean:simple")
	public String largerThan_javascript(){
		String script = "return nosliwCreateData(parms[0].value>parms[1].value, new NosliwDataTypeInfo(\"simple\", \"boolean\"));";
		return script;
	}	
}
