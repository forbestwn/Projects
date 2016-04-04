package com.nosliw.entity.query;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.HAPData;
import com.nosliw.data.HAPDataType;
import com.nosliw.data.HAPDataTypeImp;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.data.info.HAPDataTypeInfoWithVersion;
/*
 * data type definition for query entity --- the entity represent query result
 */
public class HAPQueryEntity  extends HAPDataTypeImp{

	public static HAPDataType dataType;
	
	public HAPQueryEntity(HAPDataTypeManager dataTypeMan) {
		super(new HAPDataTypeInfoWithVersion(HAPConstant.CONS_DATATYPE_CATEGARY_QUERYENTITY, HAPConstant.CONS_DATATYPE_TYPE_QUERYENTITY_NORMAL), null, null, null, "", dataTypeMan);
	}


	@Override
	public HAPData getDefaultData() {
		return null;
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
