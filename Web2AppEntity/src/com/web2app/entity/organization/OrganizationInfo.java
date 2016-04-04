package com.web2app.entity.organization;

import com.nosliw.entity.HAPEntityData;

public class OrganizationInfo extends HAPEntityData{

	private static final String ATTRIBUTE_NAME = "name";
	private static final String ATTRIBUTE_WEBSITE = "website";
	private static final String ATTRIBUTE_OFFICIALNAME = "officialname";
	
	
	public OrganizationInfo(){	}

	public String getName()
	{
		return this.getAtomAttributeValue(ATTRIBUTE_NAME);
	}
	
	public String getWebsite()
	{
		return this.getAtomAttributeValue(ATTRIBUTE_WEBSITE);
	}

	public String getOfficialName()
	{
		return this.getAtomAttributeValue(ATTRIBUTE_OFFICIALNAME);
	}
}
