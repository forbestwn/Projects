package com.nosliw.data.basic.string;

import com.nosliw.data.HAPData;
import com.nosliw.data.HAPDataOperation;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.data.HAPOperationInfoAnnotation;
import com.nosliw.data.basic.bool.HAPBooleanData;

public class HAPStringOperation extends HAPDataOperation{

	public HAPStringOperation(HAPDataTypeManager man) {
		super(man);
	}

	@HAPOperationInfoAnnotation(in = { "string:simple", "string:simple" }, out = "string:simple", description = "This is operation on string data type to compare two string")
	public HAPStringData cascade(HAPData[] parms){
		HAPStringData stringData1 = (HAPStringData)parms[0];
		HAPStringData stringData2 = (HAPStringData)parms[1];
		return HAPDataTypeManager.STRING.createDataByValue(stringData1.getValue()+stringData2.getValue());
	}

	@HAPOperationInfoAnnotation(in = { "boolean:simple" }, out = "string:simple", description = "This is operation on string data type to compare two string")
	public HAPStringData toString(HAPData[] parms){
		HAPBooleanData booleanData = (HAPBooleanData)parms[0];
		return HAPDataTypeManager.STRING.createDataByValue(booleanData.toString());
	}
	
}