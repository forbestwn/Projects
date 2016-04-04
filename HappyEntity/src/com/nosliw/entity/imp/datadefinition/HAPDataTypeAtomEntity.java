package com.nosliw.entity.imp.datadefinition;

public class HAPDataTypeAtomEntity extends HAPDataTypeEntity{

	private static final String ATTRIBUTE_CATEGARY = "categary";
	private static final String ATTRIBUTE_TYPE = "type";

	private static final String ATTRIBUTE_DATAPATH = "datapath";
	private static final String ATTRIBUTE_DATAROOTID = "datarootid";
	
	public String getCategary(){
//		return this.getAtomAttributeValue(ATTRIBUTE_CATEGARY);
		return null;
	}

	public String getType(){
//		return this.getAtomAttributeValue(ATTRIBUTE_TYPE);
		return null;
	}
	
	public void setDataPath(String path){this.setInternalAttribute(ATTRIBUTE_DATAPATH, path+"?"+this.getCategary()+"-"+this.getType());}
	public String getDataPath(){return this.getInternalAttribute(ATTRIBUTE_DATAPATH);}
	
	public void setDataRootId(String id){this.setInternalAttribute(ATTRIBUTE_DATAROOTID, id);}
	public String getDataRootId(){return this.getInternalAttribute(ATTRIBUTE_DATAROOTID);}
}
