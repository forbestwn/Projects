package com.adiak.datasource.transparent;

import com.adiak.datasource.ADKDataSource;
import com.nosliw.data.HAPData;
import com.nosliw.entity.HAPEntityData;
import com.nosliw.entity.datadefinition.HAPDataTypeEntity;

public class ADKDataSourceTransparent  extends HAPEntityData implements ADKDataSource{

	private static final String ATTRIBUTE_NAME = "name";
	private static final String ATTRIBUTE_INPUTTYPE = "inputtype";
	
	@Override
	public String getName() {
		return this.getAtomAttributeValue(ATTRIBUTE_NAME);
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public HAPDataTypeEntity getInputDataDefinition() {
		return (HAPDataTypeEntity)this.getAttributeEntityValue(ATTRIBUTE_INPUTTYPE);
	}

	@Override
	public HAPDataTypeEntity getOutputDataDefinition() {
		return this.getInputDataDefinition();
	}

	@Override
	public HAPData process(HAPData data) {
		return data;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

}
