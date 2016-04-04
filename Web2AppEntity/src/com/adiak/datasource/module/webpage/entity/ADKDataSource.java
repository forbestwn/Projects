package com.adiak.datasource.module.webpage.entity;

import com.nosliw.data.HAPData;
import com.nosliw.entity.HAPEntityData;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.entity.datadefinition.HAPDataDefinitionEntity;
import com.nosliw.entity.datadefinition.HAPDataTypeEntity;

public class ADKDataSource extends HAPEntityData implements com.adiak.datasource.ADKDataSource{

	ADKDataSource m_dataSource;
	
	@Override
	public String getName() {
		return "";
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public HAPDataTypeEntity getInputDataDefinition() {
		return null;
	}

	@Override
	public HAPDataTypeEntity getOutputDataDefinition() {
		return null;
	}

	@Override
	public HAPData process(HAPData indata) {
		ADKDataProcessor processor = (ADKDataProcessor)this.getAttributeEntityValue("processor");
		HAPData data = processor.process();
		return data;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

}
