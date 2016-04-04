package com.adiak.datasource;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.entity.imp.datadefinition.HAPDataTypeEntity;

public interface ADKDataSourcePoint {

	public String getID();
	
	public String getName();
	
	public String getDescription();
	
	//the data type structure needed when running
	public HAPDataTypeEntity getInputDataDefinition();
	
	//the output data structure 
	public HAPDataTypeEntity getOutputDataDefinition();
	
	public HAPData process(HAPData data);
	
}
