package com.nosliw.data.exchange;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.entity.imp.datadefinition.HAPDataTypeEntity;

public interface HAPDataProcessor {

	//the data type structure needed when running
	public HAPDataTypeEntity getInputDataDefinition();
	
	//the output data structure 
	public HAPDataTypeEntity getOutputDataDefinition();
	
	public HAPData process(HAPData data);

}
