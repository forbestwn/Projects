package com.nosliw.entity.imp.parm;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.entity.data.HAPEntityData;

/*
 * parm entity, containing name and value pair
 * value is store in simple/text
 * their is no validation in parm entity, validation is done by parms entity which is the container of parm
 * parms entity does the validation according to the parm definition
 */

public class HAPParmEntity extends HAPEntityData{

	public final static String ENTITY_NAME = "parm";
	
	public final static String ATTRIBUTE_NAME = "name";
	public final static String ATTRIBUTE_VALUE = "value";

	public String getName(){
//		return this.getAtomAttributeValue(ATTRIBUTE_NAME);
		return null;
	}

	public HAPData getValue(){
//		String value = this.getAtomAttributeValue(ATTRIBUTE_VALUE);
//		return this.getUserContext().getDataTypeManager().parseString(value);
		return null;
	}

	public void setParm(String parm, String value){
//		this.getAttributeValueWraper(ATTRIBUTE_NAME).setDataValue(parm);
//		this.getAttributeValueWraper(ATTRIBUTE_VALUE).setDataValue(value);
	}

}
