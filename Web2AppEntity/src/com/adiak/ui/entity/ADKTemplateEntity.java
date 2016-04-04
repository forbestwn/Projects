package com.adiak.ui.entity;

import com.nosliw.entity.HAPEntityData;

public class ADKTemplateEntity   extends HAPEntityData{

	private static final String ATTRIBUTE_TYPE = "type";
	private static final String ATTRIBUTE_NAME = "name";
	
	public String getType(){
		return this.getAtomAttributeValue(ATTRIBUTE_TYPE);
	}
	
	public String getName(){
		return this.getAtomAttributeValue(ATTRIBUTE_NAME);
	}
	
	
	
}
