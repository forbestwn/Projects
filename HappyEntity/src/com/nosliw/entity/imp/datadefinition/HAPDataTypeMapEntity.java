package com.nosliw.entity.imp.datadefinition;

import java.util.Map;

import com.nosliw.entity.data.HAPEntityContainerAttributeWraper;

public class HAPDataTypeMapEntity extends HAPDataTypeEntity{

	private static final String ATTRIBUTE_CHILDTYPES = "childtypes";
	
	private Map<String, HAPDataDefinitionEntity> getChildTypes(){
		HAPEntityContainerAttributeWraper typeWraper = (HAPEntityContainerAttributeWraper)this.getAttributeValueWraper(ATTRIBUTE_CHILDTYPES);
		return (Map<String, HAPDataDefinitionEntity>)typeWraper.getData();
	}
	
	@Override
	public void createPath(String path, String rootId){
//		path = path + "?" + this.getType();
//		String[] keys = this.getMapAttributeKeys(ATTRIBUTE_CHILDTYPES);
//		for(String key : keys){
//			HAPDataTypeEntity dataTypeEntity = (HAPDataTypeEntity)this.getChildPathWraper(ATTRIBUTE_CHILDTYPES+":" + key + ":" +"datatype").getValue();
//			dataTypeEntity.createPath(path+":"+ key, rootId);
//		}
	}
	
}
