package com.nosliw.entity.definition;

import java.util.Map;

import com.nosliw.entity.utils.HAPAttributeConstant;

/*
 * this entity definition is the entity definition information that stored in each entity
 * it has 
 * 	straight attribute definitions
 *  entity name
 *  critical value for this entity definition
 *  base class
 * 
 */
public class HAPEntityDefinition extends HAPEntityDefinitionBasic{

	//null: no critical attribute
	//"" : critical attribute value is ""
	private String m_criticalAttrValue = null;
	
	public HAPEntityDefinition(HAPEntityDefinitionBasic def, String criticalValue, HAPEntityDefinitionManager entityDefinitionMan)
	{
		def.cloneTo(this);
		this.m_criticalAttrValue = criticalValue;
	}
	
	public String getCriticalAttrValue() {
		return this.m_criticalAttrValue;
	}

	protected void buildJsonMap(Map<String, String> jsonMap){
		super.buildJsonMap(jsonMap);
		jsonMap.put(HAPAttributeConstant.ATTR_ENTITYDEFINITION_CRITICALATTRVALUE, this.m_criticalAttrValue);
	}
	
}
