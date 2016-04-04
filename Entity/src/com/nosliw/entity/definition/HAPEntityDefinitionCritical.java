package com.nosliw.entity.definition;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.common.utils.HAPNamingConversionUtility;
import com.nosliw.common.utils.HAPSegmentParser;
import com.nosliw.data.HAPData;
import com.nosliw.entity.utils.HAPAttributeConstant;
import com.nosliw.entity.utils.HAPEntityDataTypeUtility;
import com.nosliw.entity.utils.HAPEntityUtility;

/*
 * this class is the definition of an complex entity 
 * every entity type has 
 * 	entity name, for instance : common.Parm
 * 	group name:
 * 		group is introduced when dealing with datasource.
 * 		because there are different type of datasoruces and system allow other type datasources are introduced into system as plugin
 * 		for some entity, for instance Face, it has attribute typed datasource. 
 * 		in that case, we use group to group different type of datasource under one group name
 * 		therefore, face entity just define its attribute by group name
 * 		one entity can belong to mutiple group
 *  attributes, there are two type of attributes : normal and critical one
 *  	critical attribute: when critical attribute is set to different value, the entity's attribute definition may changes. 
 *  	The class name may change as well
 *  	critical is in text type
 *  critical value:
 *  	"" : valid critical value
 *      null : invalid or unknown
 *  class
 *  	base class to create complex entity object
 *		for entity with critical attribute, each critical value may has its own base class,   	
 *  provide method to create another entity definition based on critical attribute value
 */

public class HAPEntityDefinitionCritical extends HAPEntityDefinitionBasic{

	//critical attribute name/definition
	protected String m_criticalAttribute;
	private HAPAttributeDefinitionAtom m_criticalAttrDefinition;
	//entity attributes parts determined by critical value
	protected Map<String, HAPEntityDefinitionBasic> m_criticalEntitys = null;
	//other critical value: when have no value match within m_criticalEntitys with critical attribute value, then use other part  
	protected HAPEntityDefinitionBasic m_criticalEntityOther = null;
	
	public HAPEntityDefinitionCritical(String name, String baseClassName, Set<String> groups, HAPEntityDefinitionManager entityDefinitionMan){
		super(name, baseClassName, groups, entityDefinitionMan);
		m_criticalEntitys = new LinkedHashMap<String, HAPEntityDefinitionBasic>();
	}	

	public HAPEntityDefinitionCritical(HAPEntityDefinitionBasic entityDefBasic){
		super(entityDefBasic);
		m_criticalEntitys = new LinkedHashMap<String, HAPEntityDefinitionBasic>();
	}	
	
	@Override
	public void afterLoad(){
		super.afterLoad();
		this.findCriticalAttribute();
	}

	//************************  methods about critical attribute
	/*
	 * critical attribute
	 * null if no critical attribute
	 */
	public String getCriticalAttribute() {return this.m_criticalAttribute;}

	/*
	 * get class type attribute definition
	 * null if no class type attribute
	 */
	public HAPAttributeDefinitionAtom getCriticalAttributeDefinition() {
		if(m_criticalAttrDefinition!=null)   return m_criticalAttrDefinition;
		if(!this.hasCriticalAttribute())  return null;
		m_criticalAttrDefinition = (HAPAttributeDefinitionAtom)this.getAttributeDefinitionByPath(this.getCriticalAttribute());
		return m_criticalAttrDefinition;
	}

	public HAPEntityDefinitionBasic getCriticalEntityOther(){return this.m_criticalEntityOther;}
	public void setCriticalEntityOther(HAPEntityDefinitionBasic other){
		this.setupCriticalEntity(other);
		this.m_criticalEntityOther=other;
	}

	public void addCriticalEntity(String value, HAPEntityDefinitionBasic criticalEntity){
		this.setupCriticalEntity(criticalEntity);
		this.m_criticalEntitys.put(value, criticalEntity);
	}
	public HAPEntityDefinitionBasic getCriticalEntity(String value){return this.m_criticalEntitys.get(value);}
	
	protected void setupCriticalEntity(HAPEntityDefinitionBasic entityDef){
		entityDef.m_groups.addAll(this.m_groups);
	}
	
	/*
	 * get class name based on the critical attribute value
	 * return base class name if value is not critical value
	 */
	public String getCriticalClassName(String value) {
		String out = this.getBaseClassName();
		if(this.hasCriticalAttribute()){
			HAPEntityDefinitionBasic entityDef = this.getCriticalEntityByCriticalValue(value);
			if(entityDef!=null){
				out = entityDef.getBaseClassName();
			}
		}
		return out;
	}

	public Set<String> getAllCriticalValues(){
		if(!this.hasCriticalAttribute())  return null;
		Set<String> out = new HashSet<String>();
		for(String opt : this.m_criticalEntitys.keySet())	out.add(opt);
		if(this.m_criticalEntityOther!=null)  out.add(HAPConstant.CONS_ENTITY_CRITICALVALUE_OTHER);
		return out;
	}
	
	public Map<String, HAPAttributeDefinition> getCriticalValueAttributeDefinitions(String criticalValue){
		return this.getEntityDefinitionByCriticalValue(criticalValue).getAttributeDefinitions();
	}
	
	/*
	 * get entity whole definition according to the value of the critical attribute
	 * if this attribute is not critical value, then return basic entity part
	 * if value is not part of option values, then return basic Entity part
	 * if value is null, then using default value
	 */
	public HAPEntityDefinition getEntityDefinitionByCriticalValue(String criticalValue) {
		//not critical attribute
		if(!this.hasCriticalAttribute())  return new HAPEntityDefinition(this, null, this.getEntityDefinitionManager());

		HAPEntityDefinitionBasic criticalEntityDef = null;
		
		if(criticalValue==HAPEntityUtility.getEmptyCriticalValue()){
			// if criticalValue is empty, critical attribute's use default instead
			HAPData defaultValue = this.getCriticalAttributeDefinition().getDefaultValue();
			if(defaultValue==null)  criticalValue=null;
			else criticalValue = defaultValue.toString();  
		}
		
		if(criticalValue!=null){
			criticalEntityDef = this.getCriticalEntityByCriticalValue(criticalValue); 
		}
		else{
			//critical value is empty, try use other
			criticalEntityDef = null;
		}

		HAPEntityDefinitionBasic entityDef = null;
		if(criticalEntityDef!=null){
			entityDef = (HAPEntityDefinitionBasic)criticalEntityDef.cloneDefinition(); 
			entityDef.m_attributeDefs.putAll(this.getAttributeDefinitions());
		}
		else{
			entityDef = this;
		}

		return new HAPEntityDefinition(entityDef, criticalValue, this.getEntityDefinitionManager());
	}
	
	/*
	 * check if this entity def has critical attribute defined
	 */
	protected boolean hasCriticalAttribute(){return !HAPBasicUtility.isStringNotEmpty(this.getCriticalAttribute());}

	/*
	 * get critical part of entity def according to critical value
	 */
	protected HAPEntityDefinitionBasic getCriticalEntityByCriticalValue(String value){
		//if value is empty, invalid, return null
		if(HAPEntityUtility.getEmptyCriticalValue()==value)  return null;
		HAPEntityDefinitionBasic entityDef = this.m_criticalEntitys.get(value);
		if(entityDef!=null){
			entityDef = this.m_criticalEntityOther;
		}
		return entityDef;
	}
	
	/*
	 * search in all attribute to find critical attribute 
	 */
	private void findCriticalAttribute(){
		for(String name : this.getAttributeDefinitions().keySet()){
			HAPAttributeDefinition attr = this.getAttributeDefinitions().get(name); 
			if(HAPEntityDataTypeUtility.isAtomType(attr.getDataTypeDefinitionInfo())){
				if(((HAPAttributeDefinitionAtom)attr).getIsCritical()){
					this.m_criticalAttribute = attr.getName();
					break;
				}
			}
		}
	}
	
	@Override
	public HAPAttributeDefinition getAttributeDefinitionByName(String attrName){
		HAPAttributeDefinition attrDef = null;
		
		HAPSegmentParser segments = HAPNamingConversionUtility.isKeywordPhrase(attrName);
		if(segments!=null){
			String keyword = segments.next();
			if(keyword.equals(HAPConstant.CONS_ATTRIBUTE_PATH_CRITICAL)){
				//critical 
				String optionValue = segments.next();
				String name = segments.next();
				
				HAPEntityDefinitionBasic optionEntity = this.m_criticalEntitys.get(optionValue);
				if(optionEntity==null)  optionEntity = this.m_criticalEntityOther;
				if(optionEntity==null)	return null;
				
				attrDef = optionEntity.getAttributeDefinitionByPath(name);
			}
		}
		else{
			attrDef = this.m_attributeDefs.get(attrName);
		}
		return attrDef;
	}
	
	@Override
	public HAPEntityDefinitionCritical cloneDefinition()
	{
		HAPEntityDefinitionCritical entityDef = new HAPEntityDefinitionCritical(this.m_entityName, this.getBaseClassName(), this.getGroups(), this.getEntityDefinitionManager());
		this.cloneTo(entityDef);
		return entityDef;
	}
	
	protected void cloneTo(HAPEntityDefinitionCritical entityDef){
		super.cloneTo(entityDef);
		if(this.m_criticalEntitys!=null){
			entityDef.m_criticalEntitys = new LinkedHashMap<String, HAPEntityDefinitionBasic>(); 
			for(String vv :this.m_criticalEntitys.keySet()){
				entityDef.m_criticalEntitys.put(vv, this.m_criticalEntitys.get(vv).cloneDefinition());
			}
		}
		if(this.m_criticalEntityOther!=null)	entityDef.m_criticalEntityOther = this.m_criticalEntityOther.cloneDefinition();
	}
	
	@Override
	protected void buildJsonMap(Map<String, String> jsonMap){
		super.buildJsonMap(jsonMap);
		//critical attribute
		Map<String, String> criAttrJsonMap = new LinkedHashMap<String, String>();
		for(String criVal : this.m_criticalEntitys.keySet()){
			HAPEntityDefinitionBasic criEntityDef = this.m_criticalEntitys.get(criVal);
			criAttrJsonMap.put(criVal, criEntityDef.toStringValue(HAPConstant.CONS_SERIALIZATION_JSON));
		}
		
		//other critical attribute
		if(this.m_criticalEntityOther!=null){
			criAttrJsonMap.put(HAPConstant.CONS_ENTITY_CRITICALVALUE_OTHER, m_criticalEntityOther.toStringValue(HAPConstant.CONS_SERIALIZATION_JSON));
		}
		String criAttrJson = HAPJsonUtility.getMapJson(criAttrJsonMap);
		jsonMap.put(HAPAttributeConstant.ATTR_ENTITYDEFINITION_CRITICALENTITYS, criAttrJson);
		
	}	
}
