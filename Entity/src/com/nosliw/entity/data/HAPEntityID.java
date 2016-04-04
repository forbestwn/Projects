package com.nosliw.entity.data;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPStringable;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPNamingConversionUtility;
import com.nosliw.common.utils.HAPSegmentParser;
import com.nosliw.entity.utils.HAPAttributeConstant;

/*
 * store all the component of entity ID:
 *    userContext  : which user context this entity belong to
 *    type: 	entity type of root entity
 *    id: 		entity id of root entity which is unique within same entity type
 *    attribute path: if this entity is not root entity, then attribute path is the path from root entity 
 */
public class HAPEntityID implements HAPStringable{
	//user context this entity belong to
	private String m_userContext;
	//entity type name
	private String m_entityType;
	//root entity id
	private String m_id;
	//for entity that is not root entity, rather is attribute/subattribute of root entity, then attrPath is the path from root entity 
	private String m_attrPath;

	public HAPEntityID(String userContext, String type, String id, String attrPath){
		this.m_userContext = userContext;
		this.m_entityType = type;
		this.m_id = id;
		this.m_attrPath = attrPath;
	}
	
	public HAPEntityID(String userContext, String type, String id){
		this.m_userContext = userContext;
		this.m_entityType = type;
		this.m_id = id;
	}

	public HAPEntityID(HAPEntityID parentID, String attrPath){
		this.m_userContext = parentID.getUserContext();
		this.m_entityType = parentID.getEntityType();
		this.m_id = parentID.getId();
		this.m_attrPath = HAPNamingConversionUtility.cascadePath(parentID.getAttributePath(), attrPath);
		
		uniformalize();
	}
	
	public HAPEntityID(String userContext, String ID){
		HAPSegmentParser idSegs = new HAPSegmentParser(ID, HAPConstant.CONS_SEPERATOR_PART);
		String firstPart = idSegs.next();
		String uc = HAPNamingConversionUtility.getKeyword(firstPart, HAPConstant.CONS_SYMBOL_USERCONTEXT);
		this.m_userContext = userContext;
		if(uc!=null){
			this.m_userContext = uc;
			this.m_entityType = idSegs.next();
		}
		else{
			this.m_entityType = firstPart;
		}
		
		this.m_id = idSegs.next();
		
		if(idSegs.hasNext())  this.m_attrPath = idSegs.next();
	}
	
	public HAPEntityID(String ID){
		this("", ID);
	}
	
	public HAPEntityID getRootEntityID(){
		return new HAPEntityID(this.getUserContext(), this.getEntityType(), this.getId(), null);
	}
	
	public HAPEntityID getUniformEntityID(){
		String path = this.getAttributePath();
		String id = this.getId();
		int index = id.indexOf(HAPConstant.CONS_SEPERATOR_PART);
		if(index!=-1){
			path = HAPNamingConversionUtility.cascadePart(id.substring(index+1), path);
			id = id.substring(0, index);
		}
		HAPEntityID ID = new HAPEntityID(this.getUserContext(), this.getEntityType(), id, path);
		return ID;
	}
	
	private void uniformalize(){
		int index = this.m_id.indexOf(HAPConstant.CONS_SEPERATOR_PART);
		if(index!=-1){
			this.m_attrPath = HAPNamingConversionUtility.cascadePart(this.m_id.substring(index+1), this.m_attrPath); 
			this.m_id = this.m_id.substring(0, index);
		}
	}

	public String getUserContext(){return this.m_userContext;}
	public void setUserContext(String userContext){this.m_userContext=userContext;}
	public String getEntityType(){return this.m_entityType;}
	public String getId(){return this.m_id;}
	public String getAttributePath(){return this.m_attrPath;}
	
	@Override
	public String toStringValue(String format){
		return this.toString();
		
//		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
//		jsonMap.put(HAPConstant.ENTITYID_ATTRIBUTE_FULLNAME, this.toString());
//
//		Map<String, String> jsonDataMap = new LinkedHashMap<String, String>();
//		jsonDataMap.put(HAPConstant.ENTITYID_ATTRIBUTE_USERCONTEXT, this.m_userContext);
//		jsonDataMap.put(HAPConstant.ENTITYID_ATTRIBUTE_ID, this.m_id);
//		jsonDataMap.put(HAPConstant.ENTITYID_ATTRIBUTE_TYPE, this.m_type);
//		jsonDataMap.put(HAPConstant.ENTITYID_ATTRIBUTE_ATTRPATH, this.m_attrPath);
//		jsonMap.put(HAPConstant.ENTITYID_ATTRIBUTE_DATA, HAPJsonUtility.getMapJson(jsonDataMap));
//
//		return HAPJsonUtility.getMapJson(jsonMap);
	}
	
	public static HAPEntityID parseJson(JSONObject jsonEntityID){
		String userContext = jsonEntityID.optString(HAPAttributeConstant.ATTR_ENTITYID_USERCONTEXT);
		String type = jsonEntityID.optString(HAPAttributeConstant.ATTR_ENTITYID_ENTITYTYPE);
		String id = jsonEntityID.optString(HAPAttributeConstant.ATTR_ENTITYID_ID);
		String attrPath = jsonEntityID.optString(HAPAttributeConstant.ATTR_ENTITYID_ATTRIBUTEPATH);
		return new HAPEntityID(userContext, type, id, attrPath);
	}
	
	@Override
	public HAPEntityID clone(){
		return new HAPEntityID(this.m_userContext, this.m_entityType, this.m_id, this.m_attrPath);
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof HAPEntityID){
			HAPEntityID ID = (HAPEntityID)o;
			if(HAPBasicUtility.isEquals(ID.getUserContext(), this.getUserContext()) && 
					HAPBasicUtility.isEquals(ID.getEntityType(), this.getEntityType()) &&
					HAPBasicUtility.isEquals(ID.getId(), this.getId()) &&
					HAPBasicUtility.isEquals(ID.getAttributePath(), this.getAttributePath())){
				return true;
			}
			else  return false;
		}
		else  return false;
	}

	@Override
	public String toString(){
		if(HAPBasicUtility.isStringEmpty(this.getEntityType()))  return null;
		if(HAPBasicUtility.isStringEmpty(this.getId()))  return null;
		
		StringBuffer out = new StringBuffer();
		out.append(HAPConstant.CONS_SYMBOL_USERCONTEXT);
		out.append(this.getUserContext());
		out.append(HAPConstant.CONS_SEPERATOR_PART);
		out.append(this.getEntityType());
		out.append(HAPConstant.CONS_SEPERATOR_PART);
		out.append(this.getId());
		if(HAPBasicUtility.isStringNotEmpty(this.getAttributePath())){
			out.append(HAPConstant.CONS_SEPERATOR_PART);
			out.append(this.getAttributePath());
		}
		return out.toString();
	}
}
