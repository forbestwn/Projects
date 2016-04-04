package com.nosliw.entity.definition;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.serialization.HAPStringable;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.common.utils.HAPSegmentParser;
import com.nosliw.entity.utils.HAPAttributeConstant;

/*
 * this class is parent class or a component of critical entity def
 *    
 */
public class HAPEntityDefinitionBasic implements HAPStringable{
	//entity name
	protected String m_entityName;  	
	//groups this entity belong to
	protected Set<String> m_groups;
	//java class name for entity object
	protected String m_baseClassName;

	//entity attribute information
	protected Map<String, HAPAttributeDefinition> m_attributeDefs; 
	
	private HAPEntityDefinitionManager m_entityDefinitionMan;
	
	public HAPEntityDefinitionBasic(String name, String baseClassName, Set<String> groups, HAPEntityDefinitionManager entityDefinitionMan){
		this();
		this.m_entityDefinitionMan = entityDefinitionMan;
		if(groups!=null)		this.m_groups.addAll(groups);
		this.m_entityName = name;
		if(HAPBasicUtility.isStringEmpty(baseClassName))	this.m_baseClassName=this.getEntityDefinitionManager().getDefaultClassName();
		else this.m_baseClassName = baseClassName;
	}	
	
	public HAPEntityDefinitionBasic(HAPEntityDefinitionCritical def){
		this();
		def.cloneTo(this);
	}

	public HAPEntityDefinitionBasic(HAPEntityDefinitionBasic def){
		this();
		def.cloneTo(this);
	}
	
	protected HAPEntityDefinitionBasic(){
		this.m_groups = new HashSet<String>();
		m_attributeDefs = new LinkedHashMap<String, HAPAttributeDefinition>();
	}
	
	/******************************************   Serialization  *********************************************/
	
	protected void buildJsonMap(Map<String, String> jsonMap){
		Map<String, String> attrJsonMap = new LinkedHashMap<String, String>();
		for(String attrName : this.getAttributeNames()){
			HAPAttributeDefinition attrDef = this.getAttributeDefinitionByPath(attrName);
			attrJsonMap.put(attrName, attrDef.toStringValue(HAPConstant.CONS_SERIALIZATION_JSON));
		}
		jsonMap.put(HAPAttributeConstant.ATTR_ENTITYDEFINITION_ATTRIBUTES, HAPJsonUtility.getMapJson(attrJsonMap));
		
		jsonMap.put(HAPAttributeConstant.ATTR_ENTITYDEFINITION_ENTITYNAME, this.getEntityName());
		jsonMap.put(HAPAttributeConstant.ATTR_ENTITYDEFINITION_GROUPS, HAPJsonUtility.getSetObjectJson(m_groups));
		jsonMap.put(HAPAttributeConstant.ATTR_ENTITYDEFINITION_BASECLASS, this.getBaseClassName());
	}
	
	
	@Override
	public final String toStringValue(String format) {
		if(format.equals(HAPConstant.CONS_SERIALIZATION_JSON)){
			Map<String, String> outJsonMap = new LinkedHashMap<String, String>();
			this.buildJsonMap(outJsonMap);
			return HAPJsonUtility.getMapJson(outJsonMap);
		}
		return null;
	}
	
	@Override
	public String toString(){	return this.toStringValue(HAPConstant.CONS_SERIALIZATION_JSON);	}

	/******************************************   Attribute  *********************************************/
	/*
	 * get entity's base attributes names, not including those come with the subclass attribute
	 */
	public Set<String> getAttributeNames() {return this.m_attributeDefs.keySet();}

	/*
	 * get entity's base attribute definitions info, not including those come with the subclass attribute
	 */
	public Map<String, HAPAttributeDefinition> getAttributeDefinitions() {	return this.m_attributeDefs;}
	
	public void copyAttributeDefinition(HAPAttributeDefinition defin){
		HAPAttributeDefinition def1 = defin.cloneDefinition(this);
		this.m_attributeDefs.put(def1.getName(), def1);
	}

	protected void copyAttributeDefinitions(Set<HAPAttributeDefinition> defins) {
		for(HAPAttributeDefinition def: defins)		this.copyAttributeDefinition(def);
	}

	public void addAttributeDefinition(HAPAttributeDefinition defin){
		this.m_attributeDefs.put(defin.getName(), defin);
	}
	
	public void removeAttributeDefinition(String attrName) {this.m_attributeDefs.remove(attrName);	}
	
	public HAPAttributeDefinition getAttributeDefinitionByName(String attrName){return this.m_attributeDefs.get(attrName);}
	
	/*
	 * get attribute definitions by attribute path
	 * return null if no attribute exists
	 * 
	 * the attribute path is a list of jointed attribute  for example : 
	 * 			children.name:..
	 * 			#critical|value|attribute
	 * 			#element.address
	 */
	public HAPAttributeDefinition getAttributeDefinitionByPath(String attrPath) {
		HAPSegmentParser pathParser = new HAPSegmentParser(attrPath);
		String attrName = pathParser.next();
		String attrRest = pathParser.getRestPath();
		
		HAPAttributeDefinition attrDef = this.getAttributeDefinitionByName(attrName);
		if(HAPBasicUtility.isStringEmpty(attrRest))  return attrDef;
		else return attrDef.getChildAttrByPath(attrRest);
	}

	/******************************************   Basic Information  *********************************************/
	public String getEntityName(){return this.m_entityName;}
//	public void setEntityName(String entityName) {this.m_entityName = entityName;}

	public Set<String> getGroups() {	return this.m_groups;	}
//	public void addGroup(String group){this.m_groups.add(group);}

	/* get defined class name for this entity
	 * if not defined, then use method getDefaultClassName in EntityDefinitionManager instead 
	 */
	public String getBaseClassName() {	return this.m_baseClassName;	}
//	public void setBaseClass(String className) {this.m_baseClassName=className;}

	protected HAPEntityDefinitionManager getEntityDefinitionManager(){return this.m_entityDefinitionMan;}
	
	/******************************************   Clone  *********************************************/
	public HAPEntityDefinitionBasic cloneDefinition(){
		HAPEntityDefinitionBasic out = new HAPEntityDefinitionBasic();
		this.cloneTo(out);
		return out;
	}

	protected void cloneTo(HAPEntityDefinitionBasic entityDef){
		entityDef.m_entityDefinitionMan = this.m_entityDefinitionMan;
		entityDef.m_entityName = this.m_entityName;
		for(String group : this.getGroups())   entityDef.m_groups.add(group);
		entityDef.m_baseClassName = this.getBaseClassName();
		entityDef.m_attributeDefs.putAll(this.m_attributeDefs);
	}
	
	/******************************************   Loader  *********************************************/
	/*
	 * method to be called after entity definition is loaded
	 */
	public void afterLoad(){}

}

