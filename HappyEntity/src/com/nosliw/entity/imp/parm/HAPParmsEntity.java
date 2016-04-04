package com.nosliw.entity.imp.parm;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.entity.data.HAPEntityData;

/*
 * parms container, 
 * some parms follow Parm Definition
 * other parms have no definition, so we can add or set any value we like
 * 
 */
public class HAPParmsEntity extends HAPEntityData{

	public final static String ATTRIBUTE_PARMS = "parms";
	
	private Map<String, HAPParmDefinitionEntity> m_parmDefs;

	public HAPParmsEntity(){
		super();
		this.m_parmDefs = new LinkedHashMap<String, HAPParmDefinitionEntity>();
	}
	
	public String[] getParmNames(){
//		return this.getMapAttributeKeys(ATTRIBUTE_PARMS);
		return null;
	}
	
//	public HAPMapValueWraper getParmsWraper(){
//		return (HAPMapValueWraper)this.getAttributeValueWraper(ATTRIBUTE_PARMS);
//	}
	
	public HAPData getValue(String parm){
//		HAPDataWraper wraper = getParmsWraper().getElement(parm);
//		if(wraper != null){
//			return ((HAPParmEntity)wraper.getValue()).getAttributeValue(parm);
//		}
//		else{
//			HAPParmDefinitionEntity parmDef = this.getParmDefinition(parm);
//			if(parmDef == null)  return null;
//			else{
//				return parmDef.getDefaultValue();
//			}
//		}
		return null;
	}
	
	public void addParmDefinition(HAPParmDefinitionEntity parmDef){
		this.m_parmDefs.put(parmDef.getName(), parmDef);
	}
	
	public HAPParmDefinitionEntity getParmDefinition(String parm){
		return this.m_parmDefs.get(parm);
	}
	
//	public HAPServiceData setParm(String parm, HAPData value){
//		return HAPServiceData.createSuccessData();
//	}
//	
//	public HAPServiceData setParm(HAPParmEntity parm){
//		HAPServiceData out = HAPServiceData.createSuccessData();
//		HAPParmDefinitionEntity parmDef = this.getParmDefinition(parm.getName());
//		if(parmDef == null)   this.getParmsWraper().addElement(parm);
//		else{
//			out = parmDef.validateParmValue(parm);
//			if(out.isSuccess())  this.getParmsWraper().addElement(parm);
//		}
//		return out;
//	}
	
}
