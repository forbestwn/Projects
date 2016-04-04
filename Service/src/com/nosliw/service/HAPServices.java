package com.nosliw.service;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.data.info.HAPDataTypeInfo;

/*
 * this interface define all the service/method for UI
 * return type for all the method are service data
 * therefore, all the method may fail for some reason
 */
public interface HAPServices {
	
	//*********************************  method about entity definition  ***************************
	public HAPServiceData getAllEntityDefinitions();
	public HAPServiceData getEntityDefinitionsByGroup(String group);
	public HAPServiceData getEntityDefinitionByName(String name);
	
	//*********************************  data type definition  ***************************
	public HAPServiceData getDataType(HAPDataTypeInfo info);
	public HAPServiceData getAllDataTypes();
	
	//*********************************  ui resource  *******************************
	public HAPServiceData getUIResource(String name);
	public HAPServiceData getAllUIResources();
	
}
