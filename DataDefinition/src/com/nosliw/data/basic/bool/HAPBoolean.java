package com.nosliw.data.basic.bool;

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
import com.nosliw.data.utils.HAPDataErrorUtility;
import com.nosliw.data.utils.HAPDataUtility;

public class HAPBoolean extends HAPDataTypeImp{

	private static HAPBoolean dataType;
	
	private HAPBoolean(HAPDataTypeInfoWithVersion dataTypeInfo, 
						HAPDataType olderDataType, 
						HAPDataTypeInfo parentDataTypeInfo, 
						HAPConfigurable configure,
						String description,
						HAPDataTypeManager dataTypeMan) {
		super(dataTypeInfo, olderDataType, parentDataTypeInfo, configure, description, dataTypeMan);
		this.setDataTypeOperations(new HAPDataTypeOperationsAnnotation(new HAPBooleanOperation(dataTypeMan), dataTypeInfo, dataTypeMan));
	}
	
	@Override
	public HAPData getDefaultData() {
		return createDataByValue(false);
	}

	@Override
	public HAPData toData(Object value, String format) {
		HAPData out = null;
		out = createDataByValue(Boolean.parseBoolean(value.toString()));
		return out;
	}

	@Override
	public HAPServiceData validate(HAPData data) {
		HAPDataTypeInfo dataTypeInfo1 = HAPDataUtility.getDataTypeInfo(data);
		if(!this.getDataTypeInfo().equalsWithoutVersion(dataTypeInfo1))  return HAPDataErrorUtility.createDataTypeError(dataTypeInfo1, this.getDataTypeInfo(), null);
		return HAPServiceData.createSuccessData();
	}

	public HAPBooleanData createDataByValue(boolean value){
		return new HAPBooleanData(value);
	}
	
	//factory method to create data type object 
	static public HAPBoolean createDataType(HAPDataTypeInfoWithVersion dataTypeInfo, 
											HAPDataType olderDataType, 		
											HAPDataTypeInfo parentDataTypeInfo, 
											HAPConfigurable configures,
											String description,
											HAPDataTypeManager dataTypeMan){
		if(HAPBoolean.dataType==null){
			HAPBoolean.dataType = new HAPBoolean(dataTypeInfo, olderDataType, parentDataTypeInfo, configures, description, dataTypeMan);
		}
		return HAPBoolean.dataType;
	}
}