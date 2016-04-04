package com.adiak.datasource.transparent;

import com.adiak.datasource.ADKDataSourcePoint;
import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.entity.data.HAPEntityData;
import com.nosliw.entity.definition.HAPEntityDefinitionBasic;
import com.nosliw.entity.imp.datadefinition.HAPDataTypeEntity;

public class ADKDataSourceTransparent  extends HAPEntityData implements ADKDataSourcePoint{

	private static final String ATTRIBUTE_NAME = "name";
	private static final String ATTRIBUTE_INPUTTYPE = "inputtype";
	
	public ADKDataSourceTransparent(HAPDataType dataType, HAPEntityDefinitionBasic entityInfo){
		super(dataType, entityInfo);
	}
	
	@Override
	public String getName() {
		return this.getAttributeValue(ATTRIBUTE_NAME).toString();
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
