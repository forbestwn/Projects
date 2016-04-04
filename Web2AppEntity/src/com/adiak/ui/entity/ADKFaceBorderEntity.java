package com.adiak.ui.entity;

import com.nosliw.entity.HAPEntityData;

public class ADKFaceBorderEntity extends HAPEntityData{
	
	private static final String ATTRIBUTE_BORDERTEMPLATE = "template";
	private static final String ATTRIBUTE_NAME = "name";
	
	public ADKTemplateBorderEntity getBorderTemplate(){
		return (ADKTemplateBorderEntity)this.getAttributeEntityValue(ATTRIBUTE_BORDERTEMPLATE);
	}

	public String getName(){
		return this.getAtomAttributeValue(ATTRIBUTE_NAME);
	}

}
