package com.nosliw.entity.definition;

import java.util.Map;

import com.nosliw.entity.utils.HAPAttributeConstant;

/*
 * this entity definition is the entity definition used to create entity instance 
 * it is also stored in each entity
 * it has
 * 		all the information of entity definition segment plus
 * 	 	critical value when create entity instance
 */
public class HAPEntityDefinition extends HAPEntityDefinitionSegment{

	//null: no critical attribute
	//"" : critical attribute value is ""
	private String m_criticalAttrValue = null;
	
	public HAPEntityDefinition(HAPEntityDefinitionSegment def, String criticalValue, HAPEntityDefinitionManager entityDefinitionMan)
	{
		super(entityDefinitionMan);
		this.cloneFrom(def);
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
