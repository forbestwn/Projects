package com.nosliw.entity.imp.parm;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.entity.data.HAPDataWraper;
import com.nosliw.entity.data.HAPEntityData;

public class HAPEntityWithParmDefs extends HAPEntityData{

	public static final String ATTRIBUTE_PARMDEFS = "parmdefs";

	public String[] getAllParmNames()
	{
		return null;
//		return this.getMapAttributeKeys(ATTRIBUTE_PARMDEFS);
	}
	
	public HAPParmDefinitionEntity getParmDef(String name)
	{
		HAPParmDefinitionEntity out = null;
//		HAPValueWraper entity = this.getCombinedAttributeValue(ATTRIBUTE_PARMDEFS+"."+name);
//		if(entity != null)
//		{
//			out = (ADKEntityParmDef)entity.getValue();
//		}
		return out; 
	}
	
	public HAPData getParmDefaultValue(String name)
	{
		HAPParmDefinitionEntity parm = this.getParmDef(name);
		if(parm != null)  return parm.getDefaultValue();
		return null;
	}
	
}
