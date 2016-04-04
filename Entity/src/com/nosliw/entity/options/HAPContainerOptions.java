package com.nosliw.entity.options;

import java.util.Map;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.HAPData;
import com.nosliw.data.HAPDataTypeImp;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.data.info.HAPDataTypeInfoWithVersion;

public class HAPContainerOptions extends HAPDataTypeImp{

	public static HAPContainerOptions dataType;
	public static HAPDataTypeInfoWithVersion dataTypeInfo = new HAPDataTypeInfoWithVersion(HAPConstant.CONS_DATATYPE_CATEGARY_CONTAINER, HAPConstant.CONS_DATATYPE_TYPE_CONTAINER_OPTIONS); 
	
	public HAPContainerOptions(HAPDataTypeInfoWithVersion dataTypeInfoWithVersion, HAPDataTypeManager dataTypeMan) {
		super(dataTypeInfoWithVersion, null, null, null, "", dataTypeMan);
	}

	@Override
	public HAPData getDefaultData() {
		return new HAPContainerOptionsData();
	}

	@Override
	public HAPData toData(Object value, String format) {
		return null;
	}

	@Override
	public HAPServiceData validate(HAPData data) {
		return HAPServiceData.createSuccessData();
	}

	//factory method to create data type object 
	static public HAPContainerOptions createDataType(HAPDataTypeInfoWithVersion dataTypeInfo, Map<String, String> parms, HAPDataTypeManager dataTypeMan){
		if(HAPContainerOptions.dataType==null){
			HAPContainerOptions.dataType = new HAPContainerOptions(dataTypeInfo, dataTypeMan);
		}
		return HAPContainerOptions.dataType;
	}
}
