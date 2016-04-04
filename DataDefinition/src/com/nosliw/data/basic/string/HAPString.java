package com.nosliw.data.basic.string;

import com.nosliw.common.configure.HAPConfigurable;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.HAPData;
import com.nosliw.data.HAPDataType;
import com.nosliw.data.HAPDataTypeImp;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.data.HAPDataTypeOperationsAnnotation;
import com.nosliw.data.info.HAPDataTypeInfo;
import com.nosliw.data.info.HAPDataTypeInfoWithVersion;

public class HAPString extends HAPDataTypeImp{

	//static attribute to store single data type instance for particular DataType, so that they can get it instantly without do search throug data type manager
	private static HAPString dataType;
	
	private HAPString(HAPDataTypeInfoWithVersion dataTypeInfo, 
					HAPDataType olderDataType, 
					HAPDataTypeInfo parentDataTypeInfo, 
					HAPConfigurable configure,
					String description,
					HAPDataTypeManager dataTypeMan) {
		super(dataTypeInfo, olderDataType, parentDataTypeInfo, configure, description, dataTypeMan);
		this.setDataTypeOperations(new HAPDataTypeOperationsAnnotation(new HAPStringOperation(dataTypeMan), dataTypeInfo, dataTypeMan));
	}
	
	@Override
	public HAPData getDefaultData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HAPData toData(Object value, String format) {
		HAPData out = this.createDataByValue(value.toString());
		return out;
	}

	@Override
	public HAPServiceData validate(HAPData data) {
		return null;
	}

	public HAPStringData createDataByValue(String value){
		return new HAPStringData(value);
	}
	
	//factory method to create data type object 
	static public HAPString createDataType(HAPDataTypeInfoWithVersion dataTypeInfo, 
										HAPDataType olderDataType, 		
										HAPDataTypeInfo parentDataTypeInfo, 
										HAPConfigurable configures,
										String description,
										HAPDataTypeManager dataTypeMan){
		if(HAPString.dataType==null){
			HAPString.dataType = new HAPString(dataTypeInfo, olderDataType, parentDataTypeInfo, configures, description, dataTypeMan);
		}
		return HAPString.dataType;
	}
}