package com.adiak.common.uievent;

import com.nosliw.entity.HAPEntityData;

public class ADKEventDef extends HAPEntityData{

	private final static String ATTRIBUTE_NAME = "name";
	private final static String ATTRIBUTE_DATASOURCE = "datasource";
	private final static String ATTRIBUTE_DESCRIPTION = "description";

	public String getName()
	{
		return this.getAtomAttributeValue(ATTRIBUTE_NAME);
	}
	
	public String getDescription()
	{
		return this.getAtomAttributeValue(ATTRIBUTE_DESCRIPTION);
	}

	public String getDatasource()
	{
		return this.getAtomAttributeValue(ATTRIBUTE_DATASOURCE);
	}
}
