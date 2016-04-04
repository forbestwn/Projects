package com.nosliw.data.simple.bool;

import com.nosliw.application.HAPApplicationContext;
import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.application.core.data.HAPDataTypeDomain;
import com.nosliw.application.core.data.HAPDataTypeInfo;
import com.nosliw.application.core.data.HAPDataTypeManager;
import com.nosliw.application.core.data.HAPDataTypeOperationsAnnotation;
import com.nosliw.application.core.data.HAPWraper;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.data.simple.integer.HAPIntegerData;

public class HAPBool extends HAPDataTypeDomain{

	public static HAPDataType dataType;

	public final static boolean DATA_TRUE = true;
	public final static boolean DATA_FALSE = false;
	
	public HAPBool(String name, Class wraperClass, HAPDataTypeManager dataTypeMan) {
		super(new HAPDataTypeInfo(HAPConstant.CATEGARY_SIMPLE, name), wraperClass, null, dataTypeMan);
		this.addDataTypeOperations(new HAPDataTypeOperationsAnnotation(new HAPBoolOperation(), this));
	}
	
	public HAPBool(String name, HAPDataTypeManager dataTypeMan) {
		this(name, null, dataTypeMan);
	}

	@Override
	protected String getDefaultKey() {
		return String.valueOf(DATA_FALSE);
	}

	@Override
	public HAPServiceData validate(HAPData data) {
		return HAPServiceData.createSuccessData();
	}

	@Override
	protected void initDomainValue() {
		this.addDomainValue(new HAPBoolData(true, this));
		this.addDomainValue(new HAPBoolData(false, this));
	}

	@Override
	protected String getKey(HAPData data) {
		HAPBoolData bData = (HAPBoolData)data;
		return String.valueOf(bData.getValue());
	}

	@Override
	protected String getKey(Object value, String format) {
		if(HAPConstant.FORMAT_TEXT.equals(format)){
			if(value==null)  return String.valueOf(DATA_FALSE);
			if(value.equals("true") || value.equals("yes"))  return String.valueOf(DATA_TRUE);
			return String.valueOf(DATA_FALSE);
		}
		return String.valueOf(DATA_FALSE);
	}

	public HAPBoolData createDataByValue(boolean value){
		return new HAPBoolData(value, this);
	}

}
