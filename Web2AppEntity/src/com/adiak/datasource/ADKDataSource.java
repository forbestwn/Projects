package com.adiak.datasource;

import com.nosliw.data.HAPData;
import com.nosliw.entity.datadefinition.HAPDataDefinitionEntity;
import com.nosliw.entity.datadefinition.HAPDataTypeEntity;

public interface ADKDataSource {

	public String getID();
	
	public String getName();
	
	public String getDescription();
	
	//the data type structure needed when running
	public HAPDataTypeEntity getInputDataDefinition();
	
	//the output data structure 
	public HAPDataTypeEntity getOutputDataDefinition();
	
	public HAPData process(HAPData data);
	
}
