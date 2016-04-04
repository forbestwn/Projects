package com.nosliw.data.simple.integer;

import com.nosliw.application.HAPApplicationContext;
import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.application.core.data.HAPDataTypeExtend;
import com.nosliw.application.core.data.HAPDataTypeInfo;
import com.nosliw.application.core.data.HAPDataTypeManager;
import com.nosliw.application.core.data.HAPDataTypeOperationsAnnotation;
import com.nosliw.application.core.data.HAPWraper;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.common.exception.HAPServiceData;

public class HAPInteger extends HAPDataTypeExtend{

	public static HAPDataType dataType;

	public HAPInteger(String name, Class wraperClass, HAPDataTypeManager dataTypeMan) {
		super(new HAPDataTypeInfo(HAPConstant.CATEGARY_SIMPLE, name), wraperClass, null, dataTypeMan);
		this.addDataTypeOperations(new HAPDataTypeOperationsAnnotation(new HAPIntegerOperation(this, dataTypeMan), this));
	}

	@Override
	public HAPData getDefaultData() {
		return createDataByValue(0);
	}

	@Override
	public HAPData toData(Object value, String format) {
		if(HAPConstant.FORMAT_TEXT.equals(format)){
			return createDataByValue(Integer.parseInt((String)value));
		}
		return null;
	}

	@Override
	public HAPServiceData validate(HAPData data) {
		return HAPServiceData.createSuccessData();
	}
	
	public HAPIntegerData createDataByValue(int value){
		return new HAPIntegerData(value, this);
	}
}
