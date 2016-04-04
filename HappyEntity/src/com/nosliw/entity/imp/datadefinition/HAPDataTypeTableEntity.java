package com.nosliw.entity.imp.datadefinition;

public class HAPDataTypeTableEntity  extends HAPDataTypeEntity{

	private static final String ATTRIBUTE_COLUMNTYPES = "columntypes";
	
	@Override
	public void createPath(String path, String rootId){
//		path = path + "?" + this.getType();
//		String[] keys = this.getMapAttributeKeys(ATTRIBUTE_COLUMNTYPES);
//		for(String key : keys){
//			HAPDataTypeEntity entity = (HAPDataTypeEntity)this.getChildPathWraper(ATTRIBUTE_COLUMNTYPES+":" + key + ":" +"datatype").getValue();
//			entity.createPath(path+":"+key, rootId);
//		}
	}
}
