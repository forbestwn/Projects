package com.nosliw.entity.definition;

import java.util.Map;

import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.entity.options.HAPOptionsDefinitionManager;

public class HAPAttributeDefinitionEntity extends HAPAttributeDefinition{

	//calculated attribute
	//entity definition
	private HAPEntityDefinitionCritical m_attrEntityDefinition;
	
	public HAPAttributeDefinitionEntity(String name,HAPEntityDefinitionBasic entityDef, HAPDataTypeManager dataTypeMan, HAPEntityDefinitionManager entityDefMan, HAPOptionsDefinitionManager optionsMan) {
		super(name, entityDef, dataTypeMan, entityDefMan, optionsMan);
	}

	@Override
	public HAPAttributeDefinition getChildAttrByName(String name){
		if(HAPBasicUtility.isStringEmpty(name))  return this;
		HAPEntityDefinitionCritical entityDef = this.getAttrEntityDefinition();
		return entityDef.getAttributeDefinitionByPath(name);
	}
	
	@Override
	public HAPAttributeDefinition cloneDefinition(HAPEntityDefinitionBasic entityDef){
		HAPAttributeDefinitionEntity out = new HAPAttributeDefinitionEntity(this.getName(), entityDef, this.getDataTypeManager(), this.getEntityDefinitionManager(), this.getOptionsManager());
		cloneTo(out);
		return out;
	}

	@Override
	protected void buildOjbectJsonMap(Map<String, String> map, Map<String, Class> dataTypeMap){
		super.buildOjbectJsonMap(map, dataTypeMap);
	}
	
	protected HAPEntityDefinitionCritical getAttrEntityDefinition(){
		if(this.m_attrEntityDefinition==null){
			String entityType = this.getDataTypeDefinitionInfo().getType();
			this.m_attrEntityDefinition = this.getEntityDefinitionManager().getEntityDefinition(entityType);
		}
		return this.m_attrEntityDefinition;
	}
}
