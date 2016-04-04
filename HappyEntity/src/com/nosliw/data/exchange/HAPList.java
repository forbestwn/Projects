package com.nosliw.data.exchange;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.application.core.data.HAPDataTypeExtend;
import com.nosliw.application.core.data.HAPDataTypeInfo;
import com.nosliw.application.core.data.HAPDataTypeManager;
import com.nosliw.application.core.data.HAPWraper;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.common.exception.HAPServiceData;

public class HAPList extends HAPDataTypeExtend{

	public static HAPDataType dataType;

	public HAPList(HAPDataTypeManager dataTypeMan) {
		super(new HAPDataTypeInfo(HAPConstant.CATEGARY_CONTAINER, HAPConstant.DATATYPE_CONTAINER_LIST), HAPWraper.class, null, dataTypeMan);
	}

	@Override
	public HAPData getDefaultData() {
		return new HAPListData();
	}

	@Override
	public HAPData toData(Object value, String format) {
		return null;
	}

	@Override
	public HAPServiceData validate(HAPData data) {
		return HAPServiceData.createSuccessData();
	}

}
