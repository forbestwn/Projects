package com.nosliw.data.basic.number;

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

public class HAPInteger extends HAPDataTypeImp{
	
	private static HAPInteger dataType;
	
	private HAPInteger(HAPDataTypeInfoWithVersion dataTypeInfo,
					HAPDataType olderDataType, 
					HAPDataTypeInfo parentDataTypeInfo, 
					HAPConfigurable configure,
					String description,
					HAPDataTypeManager dataTypeMan) {
		super(dataTypeInfo, olderDataType, parentDataTypeInfo, configure, description, dataTypeMan);
		this.setDataTypeOperations(new HAPDataTypeOperationsAnnotation(new HAPIntegerOperation(dataTypeMan), dataTypeInfo, dataTypeMan));
	}

	@Override
	public HAPData getDefaultData() {	return createDataByValue(0);	}

	@Override
	public HAPData toData(Object value, String format) {
		HAPData out = null;
		out = createDataByValue(Integer.parseInt(value.toString()));
		return out;
	}

	@Override
	public HAPServiceData validate(HAPData data) {
		HAPDataTypeInfo dataTypeInfo1 = HAPDataUtility.getDataTypeInfo(data);
		if(!this.getDataTypeInfo().equalsWithoutVersion(dataTypeInfo1))  return HAPDataErrorUtility.createDataTypeError(dataTypeInfo1, this.getDataTypeInfo(), null);
		return HAPServiceData.createSuccessData();
	}
	
	public HAPIntegerData createDataByValue(int value){
		return new HAPIntegerData(value);
	}

	//factory method to create data type object 
	static public HAPInteger createDataType(HAPDataTypeInfoWithVersion dataTypeInfo, 
										HAPDataType olderDataType, 		
										HAPDataTypeInfo parentDataTypeInfo, 
										HAPConfigurable configures,
										String description,
										HAPDataTypeManager dataTypeMan){
		if(HAPInteger.dataType==null){
			HAPInteger.dataType = new HAPInteger(dataTypeInfo, olderDataType, parentDataTypeInfo, configures, description, dataTypeMan);
		}
		return HAPInteger.dataType;
	}
}
