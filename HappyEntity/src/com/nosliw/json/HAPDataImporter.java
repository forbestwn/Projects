package com.nosliw.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nosliw.application.HAPUserContext;
import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.application.core.data.HAPDataTypeInfo;
import com.nosliw.application.core.data.simple.text.HAPText;
import com.nosliw.application.core.data.simple.text.HAPTextData;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.entity.data.HAPEntityContainerAttributeWraper;
import com.nosliw.entity.data.HAPDataWraper;
import com.nosliw.entity.data.HAPEntity;
import com.nosliw.entity.data.HAPEntityData;
import com.nosliw.entity.data.HAPEntityID;
import com.nosliw.entity.data.HAPEntityWraper;
import com.nosliw.entity.data.HAPReferenceWraperData;
import com.nosliw.entity.definition.HAPAttributeDefinition;
import com.nosliw.entity.definition.HAPAttributeDefinitionContainer;
import com.nosliw.entity.definition.HAPEntityDefinitionCritical;
import com.nosliw.entity.utils.HAPEntityUtility;

public class HAPDataImporter {

	public static HAPEntityWraper parseEntityWraperJson(JSONObject jsonEntityWraper, HAPUserContext userContext) throws JSONException{
/*
		JSONObject jsonDataType = jsonEntityWraper.getJSONObject(HAPConstant.WRAPER_ATTRIBUTE_DATATYPE);
		HAPDataTypeInfo dataType = HAPDataTypeInfo.parse(jsonDataType);
		
		JSONObject jsonEntity = jsonEntityWraper.getJSONObject(HAPConstant.WRAPER_ATTRIBUTE_DATA);
		HAPEntityDefinition entityDef = userContext.getEntityDefinitionManager().getEntityDefinition(dataType.getType());
		
		//get critical value from node
		String criticalValue = getCriticalValue(jsonEntity, entityDef, userContext);
		
		//create wraper with entity with empty attributes
		HAPEntity entityDataType = userContext.getDataTypeManager().getEntityDataType(dataType.getType());
		HAPEntityData entity = entityDataType.newEmptyEntity(criticalValue);
		HAPEntityWraper out = new HAPEntityWraper(entity);
		
		//set entity id
		String id = jsonEntityWraper.optString(HAPConstant.WRAPER_ATTRIBUTE_id);
		if(HAPUtility.isStringNotEmpty(id))		out.setId(id);

		//set attribute value
		importEntityAttributes(jsonEntity, out.getEntityData(), userContext);
		
		return out;
*/
		return null;
	}
	
	
	/*
	 * read all attributes into entity from element
	 */
	private static void importEntityAttributes(JSONObject jsonEntity, HAPEntityData entity, HAPUserContext appContext) throws JSONException
	{
		for(HAPAttributeDefinition attrDef : entity.getAttributeDefinitions()){
			//for each attribute defined in entity
			importEntityAttribute(jsonEntity, attrDef, entity, appContext);
		}
	}
	
	/*
	 * import attribute value into entity according to entityElement and attribute
	 * return wraper
	 * if cannot find 
	 */
	private static void importEntityAttribute(JSONObject jsonEntity, HAPAttributeDefinition attrDef, HAPEntityData parent, HAPUserContext appContext) throws JSONException
	{
		/*
		HAPDataTypeInfo attrType = attrDef.getDataTypeDefinitionInfo();
		HAPDataWraper attrWraper = parent.getAttributeValueWraper(attrDef.getName());
		
		Object data = jsonEntity.opt(attrDef.getName());
		if(data==null)  return;
		
		if(data instanceof String){
			attrWraper.setData(new HAPTextData((String)data));
			return;
		}
		else if(data instanceof JSONObject){
			JSONObject jsonAttrWraper = (JSONObject)data;
			
			if(HAPUtility.isAtomType(attrType)){
				attrWraper.setData(appContext.getDataTypeManager().parseJson(jsonAttrWraper));
			}
			else if(HAPUtility.isContainerType(attrType)){
				HAPContainerWraper containerWraper = (HAPContainerWraper)attrWraper;
				String nextId = jsonAttrWraper.getString(HAPConstant.WRAPER_ATTRIBUTE_NEXTID);
				containerWraper.getContainerData().setNextId(nextId);
				
				JSONArray jsonContainer = jsonAttrWraper.getJSONArray(HAPConstant.WRAPER_ATTRIBUTE_DATA);
				for(int i=0; i<jsonContainer.length(); i++){
					importContainerElement((JSONObject)jsonContainer.get(i), (HAPContainerWraper)attrWraper, (HAPContainerAttributeDefinition)attrDef, appContext);
				}
			}
			else if(HAPUtility.isEntityType(attrType)){
				//our defined entity type
				JSONObject jsonAttrWraperData = jsonAttrWraper.getJSONObject(HAPConstant.WRAPER_ATTRIBUTE_DATA);
				HAPEntityDefinition entityInfo = appContext.getEntityDefinitionManager().getEntityDefinition(attrType.getType());
				//get critical value from node
				String criticalValue = getCriticalValue(jsonAttrWraperData, entityInfo, appContext);
				HAPEntity entityDataType = appContext.getDataTypeManager().getEntityDataType(attrType.getType());
				HAPEntityData entity =  entityDataType.newEmptyEntity(criticalValue);
				attrWraper.setData(entity);
				importEntityAttributes(jsonAttrWraperData, entity, appContext);
			}
			else if(HAPUtility.isReferenceType(attrType)){
				HAPEntityID id = HAPEntityID.parseJson(jsonAttrWraper.getJSONObject(HAPConstant.WRAPER_ATTRIBUTE_DATA)); 
				attrWraper.setData(new HAPReferenceWraperData(id));
			}
		}
		*/
	}	
	
	private static HAPDataWraper importContainerElement(JSONObject jsonEle, 
														HAPEntityContainerAttributeWraper containerWraper, 
														HAPAttributeDefinitionContainer attrDef, 
														HAPUserContext appContext) throws JSONException
	{
		/*
		HAPDataWraper out = null;
		String eleId = jsonEle.getString(HAPConstant.WRAPER_ATTRIBUTE_id); 
		HAPDataTypeInfo cType = attrDef.getChildDataTypeDefinitionInfo();
		if(HAPUtility.isAtomType(cType)){
			HAPData data = appContext.getDataTypeManager().parseJson(jsonEle.getJSONObject(HAPConstant.WRAPER_ATTRIBUTE_DATA));
			out = containerWraper.getContainerData().addElementData(data, eleId);
		}
		else if(HAPUtility.isContainerType(cType)){
			//not appliable for container, because container cannot be element of another container
		}
		else if(HAPUtility.isEntityType(cType)){
			HAPEntityDefinition entityInfo = appContext.getEntityDefinitionManager().getEntityDefinition(cType.getType());
			//get critical value from node
			String criticalValue = getCriticalValue(jsonEle, entityInfo, appContext);
			HAPEntity entityDataType = appContext.getDataTypeManager().getEntityDataType(cType.getType());
			HAPEntityData entity = entityDataType.newEmptyEntity(criticalValue);
			
			importEntityAttributes(jsonEle, entity, appContext);
			out = containerWraper.getContainerData().addElementData(entity, eleId);
		}
		else if(HAPUtility.isReferenceType(cType)){
			HAPEntityID id = HAPEntityID.parseJson(jsonEle.getJSONObject(HAPConstant.WRAPER_ATTRIBUTE_DATA)); 
			out = containerWraper.getContainerData().addElementData(new HAPReferenceWraperData(id), eleId);
		}

		return out;
		*/
		return null;
	}
	
	private static String getCriticalValue(JSONObject jsonEntity, HAPEntityDefinitionCritical entityInfo, HAPUserContext appContext) throws JSONException 
	{
		/*
		String criticalValue = null;
		HAPAttributeDefinition criticalAttributeDef = entityInfo.getCriticalAttributeDefinition();
		if(criticalAttributeDef != null){
			Object data = jsonEntity.opt(criticalAttributeDef.getName());
			if(data instanceof String)  criticalValue = (String)data;
			else{
				JSONObject criticalJson = jsonEntity.getJSONObject(criticalAttributeDef.getName());
				criticalValue = appContext.getDataTypeManager().parseJson(criticalJson).toString();
			}
		}
		return criticalValue;
		*/
		return null;
	}
}
