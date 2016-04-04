package com.nosliw.data.library.url.v100;

import com.nosliw.common.configure.HAPConfigurable;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.data.HAPData;
import com.nosliw.data.HAPDataType;
import com.nosliw.data.HAPDataTypeImp;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.data.HAPDataTypeOperationsAnnotation;
import com.nosliw.data.info.HAPDataTypeInfo;
import com.nosliw.data.info.HAPDataTypeInfoWithVersion;

public class HAPUrl extends HAPDataTypeImp{

	private HAPUrl(HAPDataTypeInfoWithVersion dataTypeInfo, 
					HAPDataType olderDataType, 
					HAPDataTypeInfo parentDataTypeInfo, 
					HAPConfigurable configure,
					String description,
					HAPDataTypeManager dataTypeMan) {
		super(dataTypeInfo, olderDataType, parentDataTypeInfo, configure, description, dataTypeMan);
		this.setDataTypeOperations(new HAPDataTypeOperationsAnnotation(new HAPUrlOperation(dataTypeMan), dataTypeInfo, dataTypeMan));
	}

	@Override
	public HAPData getDefaultData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HAPData toData(Object value, String format) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public HAPServiceData validate(HAPData data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//factory method to create data type object 
	static public HAPUrl createDataType(HAPDataTypeInfoWithVersion dataTypeInfo, 
			HAPDataType olderDataType, 		
			HAPDataTypeInfo parentDataTypeInfo, 
			HAPConfigurable configures,
			String description,
			HAPDataTypeManager dataTypeMan){
		return new HAPUrl(dataTypeInfo, olderDataType, parentDataTypeInfo, configures, description, dataTypeMan);
	}

	
}
