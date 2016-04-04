package com.nosliw.data.simple.image;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.application.core.data.HAPDataTypeExtend;
import com.nosliw.application.core.data.HAPDataTypeInfo;
import com.nosliw.application.core.data.HAPDataTypeManager;
import com.nosliw.application.core.data.HAPDataTypeOperationsAnnotation;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.common.exception.HAPServiceData;

public class HAPImage extends HAPDataTypeExtend{

	public static HAPDataType dataType;
	
	public HAPImage(String name, Class wraperClass, HAPDataTypeManager dataTypeMan) {
		super(new HAPDataTypeInfo(HAPConstant.CATEGARY_SIMPLE, name), wraperClass, null, dataTypeMan);
		this.addDataTypeOperations(new HAPDataTypeOperationsAnnotation(new HAPImageOperation(this, dataTypeMan), this));
	}

	@Override
	public HAPData getDefaultData() {
		return new HAPImageData("", this);
	}

	@Override
	public HAPData toData(Object value, String format) {
		return HAPImageData.toImageData(value, format, this);
	}

	@Override
	public HAPServiceData validate(HAPData data) {
		return HAPServiceData.createSuccessData();
	}

}
