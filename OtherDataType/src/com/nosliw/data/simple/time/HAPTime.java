package com.nosliw.data.simple.time;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.application.core.data.HAPDataTypeExtend;
import com.nosliw.application.core.data.HAPDataTypeInfo;
import com.nosliw.application.core.data.HAPDataTypeManager;
import com.nosliw.application.core.data.HAPWraper;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.common.exception.HAPServiceData;

public class HAPTime extends HAPDataTypeExtend{

	public static HAPDataType dataType;

	public HAPTime(String name, Class wraperClass,HAPDataTypeManager dataTypeMan) {
		super(new HAPDataTypeInfo(HAPConstant.CATEGARY_SIMPLE, name), wraperClass, null, dataTypeMan);
	}

	@Override
	public HAPData getDefaultData() {
		return new HAPTimeData(0, 0, 0, this);
	}

	@Override
	public HAPData toData(Object value, String format) {
		return HAPTimeData.toTimeData(value, format, this);
	}

	@Override
	public HAPServiceData validate(HAPData data) {
		return HAPServiceData.createSuccessData();
	}

}
