package com.nosliw.data.simple.text;

import org.json.JSONObject;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.application.core.data.HAPDataTypeExtend;
import com.nosliw.application.core.data.HAPDataTypeInfo;
import com.nosliw.application.core.data.HAPDataTypeManager;
import com.nosliw.application.core.data.HAPDataTypeOperationsAnnotation;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.common.exception.HAPServiceData;

public class HAPText extends HAPDataTypeExtend{

	public static HAPText dataType;
	
	public HAPText(String name, Class wraperClass, HAPDataTypeManager dataTypeMan) {
		super(new HAPDataTypeInfo(HAPConstant.CATEGARY_SIMPLE, name), wraperClass, null, dataTypeMan);
		this.addDataTypeOperations(new HAPDataTypeOperationsAnnotation(new HAPTextOperation(this, dataTypeMan), this));
	}

	@Override
	public HAPData getDefaultData() {
		return createTextData("");
	}

	@Override
	public HAPData toData(Object value, String format) {
		String text = null;
		if(HAPConstant.FORMAT_JSON.equals(format)){
			JSONObject jsonValue = (JSONObject)value;
			text = jsonValue.optString("data");
		}
		else{
			text = (String)value;
		}
		
		return createTextData(text);
	}

	@Override
	public HAPServiceData validate(HAPData data) {
		return HAPServiceData.createSuccessData();
	}

	public HAPTextData createTextData(String value){
		return new HAPTextData(value, this);
	}
	
}
