package com.nosliw.entity.imp.parm;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.entity.data.HAPDataWraper;
import com.nosliw.entity.data.HAPEntityData;
import com.nosliw.entity.data.HAPEntityWraper;
import com.nosliw.entity.definition.HAPAttributeDefinition;

public class HAPEntityWithParms extends HAPEntityData{

	private static final String ATTRIBUTE_PARMS = "parms";

	public String[] getAllParmNames()
	{
//		return this.getMapAttributeKeys(ATTRIBUTE_PARMS);
		return null;
	}
	
	public HAPParmEntity getParm(String name)
	{
		HAPParmEntity out = null;
//		HAPValueWraper entity = this.getCombinedAttributeValue(ATTRIBUTE_PARMS+"."+name);
//		if(entity != null)
//		{
//			out = (ADKEntityParm)entity.getValue();
//		}
		return out; 
	}
	
	public HAPData getParmValue(String name)
	{
		HAPData out = null;
		HAPParmEntity parm = this.getParm(name);
		if(parm != null)
		{
			out = parm.getValue();
		}
		return out;
	}
	
	public void createDefaultParms(HAPEntityWithParmDefs parmDefEntity)
	{
//		HAPAttributeDefinition attrDef = this.getEntityInfo().getAttributeDefinition(ATTRIBUTE_PARMS);
//		
//		HAPListValueWraper parmsWraper = (HAPListValueWraper)this.getAttributeValue(ATTRIBUTE_PARMS);
//		parmsWraper.reset();
//		
//		for(String parmName : parmDefEntity.getAllParmNames())
//		{
//			ADKEntityParmDef parmDef = parmDefEntity.getParmDef(parmName);
//			HAPComplexValueWraper<ADKEntityParm> parmWraper = this.getValueFactory().newComplexAttributeValueWraper(ATTRIBUTE_PARMS, this, null);
//
//			ADKEntityParm parm = parmWraper.getComplexEntity();
//			parm.merge(parmDef);
//			
//			ADKRule rule = parmDef.getRule();
//			String[] options = parmDef.getOptions();
//			
//			parm.setOptions(ADKEntityParm.ATTRIBUTE_VALUE, options);
//			parm.setRuleConfigure(ADKEntityParm.ATTRIBUTE_VALUE, rule.toRuleConfigure());
//			
//			parmsWraper.addElement(parmWraper);
//		}
	}
	
}
