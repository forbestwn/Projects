package com.webmobile.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.nosliw.HAPApplicationContext;
import com.nosliw.HAPUserContext;
import com.nosliw.data.HAPData;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.entity.data.HAPDataWraper;
import com.nosliw.entity.data.HAPEntityContainerAttributeWraper;
import com.nosliw.entity.data.HAPEntityID;
import com.nosliw.entity.data.HAPEntityWraper;
import com.nosliw.entity.data.HAPReferenceWraper;
import com.nosliw.entity.imp.datadefinition.HAPChainProcessorEntity;
import com.nosliw.entity.imp.datadefinition.HAPUIPath;
import com.nosliw.exception.HAPServiceData;
import com.nosliw.ui.HAPUIResource;
import com.nosliw.ui.HAPUIResourceImporter;
import com.nosliw.util.HAPConstant;
import com.nosliw.util.HAPJsonUtility;
import com.nosliw.util.HAPUtility;
import com.test.business.manager.UserContext;
import com.test.business.manager.UserContextManager;

public class FaceService extends UserContextManager{

	public FaceService(UserContext sysContext) {
		super(sysContext);
//		sysContext.setFaceService(this);
	}

	public static String readUITemplate(String uiTemplateId, HAPApplicationContext appContext) throws Exception{
		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
		
		HAPUserContext userContext = appContext.getUserContext("default");
		TemplateUserContext templateUserContext = (TemplateUserContext)appContext.getUserContext("uitemplate");
		HAPEntityWraper uiTemplateWraper = userContext.getEntityWraperRequest(new HAPEntityID(uiTemplateId), null);
		UITemplateManager templateManager = templateUserContext.getUITemplateManager();

		String templateName = uiTemplateWraper.getChildWraperByPath("name").getData().toString();
		jsonMap.put("name", templateName);
		jsonMap.put("ID", uiTemplateWraper.getID().toString());
		
		
		String layoutResourceFile = templateManager.getResourceFileByEntity(uiTemplateWraper);
		HAPUIResource templateResource = HAPUIResourceImporter.readUIResource(layoutResourceFile);
		jsonMap.put("uiresource", templateResource.toStringValue(HAPConstant.FORMAT_JSON));

		Map<String, String> datasJsonMap = new LinkedHashMap<String, String>();
		HAPEntityContainerAttributeWraper templateDataDefs = (HAPEntityContainerAttributeWraper)uiTemplateWraper.getChildWraperByPath("uiunit.datas");
		Iterator<HAPDataWraper> tit = templateDataDefs.iterate();
		while(tit.hasNext()){
			HAPEntityWraper templateDataDef = (HAPEntityWraper)tit.next();
			String name = templateDataDef.getChildWraperByPath("name").getData().toString();
			datasJsonMap.put(name, createDataDefJson(templateDataDef));
		}		
		jsonMap.put("data", HAPJsonUtility.getMapJson(datasJsonMap));
		
		Map<String, String> eventsJsonMap = new LinkedHashMap<String, String>();
		HAPEntityContainerAttributeWraper templateEventDefs = (HAPEntityContainerAttributeWraper)uiTemplateWraper.getChildWraperByPath("uiunit.events");
		Iterator<HAPDataWraper> eit = templateEventDefs.iterate();
		while(eit.hasNext()){
			HAPEntityWraper templateEventDef = (HAPEntityWraper)eit.next();
			String name = templateEventDef.getChildWraperByPath("name").getData().toString();
			eventsJsonMap.put(name, createDataDefJson(templateEventDef));
		}		
		jsonMap.put("events", HAPJsonUtility.getMapJson(eventsJsonMap));

		Map<String, String> commandsJsonMap = new LinkedHashMap<String, String>();
		HAPEntityContainerAttributeWraper templateCommandDefs = (HAPEntityContainerAttributeWraper)uiTemplateWraper.getChildWraperByPath("uiunit.commands");
		Iterator<HAPDataWraper> cit = templateCommandDefs.iterate();
		while(cit.hasNext()){
			Map<String, String> commandJsonMap = new LinkedHashMap<String, String>();
			HAPEntityWraper templateCommandDef = (HAPEntityWraper)cit.next();
			String name = templateCommandDef.getChildWraperByPath("name").getData().toString();
			String descp = templateCommandDef.getChildWraperByPath("description").getData().toString();
			commandJsonMap.put("name", name);
			commandJsonMap.put("description", descp);

			Map<String, String> parmsJsonMap = new LinkedHashMap<String, String>();
			HAPEntityContainerAttributeWraper templateCommandParms = (HAPEntityContainerAttributeWraper)templateCommandDef.getChildWraperByPath("datatypes");
			Iterator<HAPDataWraper> ait = templateCommandParms.iterate();
			while(ait.hasNext()){
				HAPEntityWraper templateCommandParm = (HAPEntityWraper)ait.next();
				String parmName = templateCommandParm.getChildWraperByPath("name").getData().toString();
				parmsJsonMap.put(parmName, createDataDefJson(templateCommandParm));
			}
			commandJsonMap.put("parms", HAPJsonUtility.getMapJson(parmsJsonMap));
			
			commandsJsonMap.put(name, HAPJsonUtility.getMapJson(commandJsonMap));
		}		
		jsonMap.put("commands", HAPJsonUtility.getMapJson(commandsJsonMap));
		
		
		Map<String, String> componentsJsonMap = new LinkedHashMap<String, String>();
		HAPEntityContainerAttributeWraper components = (HAPEntityContainerAttributeWraper)uiTemplateWraper.getChildWraperByPath("uiunit.components");
		Iterator<HAPDataWraper> ait = components.iterate();
		while(ait.hasNext()){
			Map<String, String> componentJsonMap = new LinkedHashMap<String, String>();
			HAPEntityWraper component = (HAPEntityWraper)ait.next();
			String name = component.getChildWraperByPath("name").getData().toString();
			String descp = component.getChildWraperByPath("description").getData().toString();
			componentJsonMap.put("name", name);
			componentJsonMap.put("description", descp);

			Map<String, String> dataJsonMap = new LinkedHashMap<String, String>();
			HAPEntityContainerAttributeWraper componentDatas = (HAPEntityContainerAttributeWraper)component.getChildWraperByPath("datas");
			Iterator<HAPDataWraper> bit = componentDatas.iterate();
			while(bit.hasNext()){
				HAPEntityWraper componentData = (HAPEntityWraper)bit.next();
				String componentName = componentData.getChildWraperByPath("name").getData().toString();
				dataJsonMap.put(componentName, createDataDefJson(componentData));
			}
			componentJsonMap.put("data", HAPJsonUtility.getMapJson(dataJsonMap));

			componentJsonMap.put("uiunit", HAPJsonUtility.getMapJson(new LinkedHashMap<String, String>()));
			
			componentsJsonMap.put(name, HAPJsonUtility.getMapJson(componentJsonMap));
		}
		jsonMap.put("components", HAPJsonUtility.getMapJson(componentsJsonMap));
		

		
		String content = HAPJsonUtility.getMapJson(jsonMap);
		return content;
	}
	
	public static String readFace(String faceStartId, Map<String, HAPData> faceDatas, HAPApplicationContext appContext) throws Exception{
		HAPUserContext userContext = appContext.getUserContext("default");
		TemplateUserContext templateUserContext = (TemplateUserContext)appContext.getUserContext("uitemplate");
		UITemplateManager templateManager = templateUserContext.getUITemplateManager();

		if(faceStartId==null)   faceStartId = "default#ui.face_start:start1000";
		HAPEntityID faceStartID = new HAPEntityID(faceStartId);
		HAPEntityWraper faceStartWraper = userContext.getEntityWraperRequest(faceStartID, null);

		//face entity
		HAPEntityID faceID = ((HAPReferenceWraper)faceStartWraper.getChildWraperByPath("face")).getIDData();
		HAPEntityWraper faceWraper = userContext.getEntityWraperRequest(faceID, null);
		HAPEntityWraper faceDefinitionWraper = ((HAPReferenceWraper)(faceWraper.getChildWraperByPath("definition"))).getReferenceWraper();
		HAPEntityWraper layoutWraper = (HAPEntityWraper)faceDefinitionWraper.getChildWraperByPath("layout");
		
		Map<String, String> outMapJson = new LinkedHashMap<String, String>();

		outMapJson.put("face", faceWraper.toStringValue(HAPConstant.FORMAT_JSON));
		
		//face data
		HAPChainProcessorEntity chainProcessorEntity = (HAPChainProcessorEntity)faceStartWraper.getChildWraperByPath("chainprocessor").getData();
		Map<String, HAPData> faceInputDataMap = chainProcessorEntity.chainProcessor(faceDatas);		
		Map<String, String> dataSourceJson = new LinkedHashMap<String, String>();
		for(String key : faceInputDataMap.keySet()){
			HAPData d = faceInputDataMap.get(key);
			dataSourceJson.put(key, d.toStringValue(HAPConstant.FORMAT_JSON_DATATYPE));
		}
		outMapJson.put("data", HAPJsonUtility.getMapJson(dataSourceJson));

		Map<String, String> layoutJsonMap = new LinkedHashMap<String, String>();
		HAPReferenceWraper layoutRefWraper = (HAPReferenceWraper)faceDefinitionWraper.getChildWraperByPath("layout.uiunit.template");
		HAPEntityWraper layoutTemplateWraper = layoutRefWraper.getReferenceWraper();
		String layoutResourceFile = templateManager.getResourceFileByEntity(layoutTemplateWraper);
		HAPUIResource layoutResource = HAPUIResourceImporter.readUIResource(layoutResourceFile);
		layoutJsonMap.put("uiresource", layoutResource.toStringValue(HAPConstant.FORMAT_JSON));
		layoutJsonMap.put("uiid", layoutWraper.getID().toString());
		outMapJson.put("layout", HAPJsonUtility.getMapJson(layoutJsonMap));
		
		Map<String, String> inputDataMap = new LinkedHashMap<String, String>();
		Map<String, String> commandMap = new LinkedHashMap<String, String>();
		Map<String, String> eventMap = new LinkedHashMap<String, String>();
		Map<String, Set<HAPEntityWraper>> eventChannelMap = new LinkedHashMap<String, Set<HAPEntityWraper>>();

		//components
		Map<String, String> componentsJsonMap = new LinkedHashMap<String, String>();
		HAPEntityContainerAttributeWraper componentsWraper = (HAPEntityContainerAttributeWraper)layoutWraper.getChildWraperByPath("components");
		Iterator<HAPDataWraper> bit = componentsWraper.iterate();
		while(bit.hasNext()){
			Map<String, String> componentInfoJsonMap = new LinkedHashMap<String, String>();
			HAPEntityWraper faceComponentWraper = (HAPEntityWraper)bit.next();
			String name = faceComponentWraper.getChildWraperByPath("name").getData().toString();
			String uiId = faceComponentWraper.getID().toString();
			
			componentInfoJsonMap.put("uiid", uiId);
			componentInfoJsonMap.put("name", name);

			//component UI Resource
			HAPReferenceWraper componentRefWraper = (HAPReferenceWraper)faceComponentWraper.getChildWraperByPath("uiunit.template");
			HAPEntityWraper componentTemplateWraper = componentRefWraper.getReferenceWraper();
			String componentResourceFile = templateManager.getResourceFileByEntity(componentTemplateWraper);
			HAPUIResource componentResource = HAPUIResourceImporter.readUIResource(componentResourceFile);
			componentInfoJsonMap.put("uiresource", componentResource.toStringValue(HAPConstant.FORMAT_JSON));

			//component data mapping
			HAPEntityContainerAttributeWraper componentDatasWraper = (HAPEntityContainerAttributeWraper)faceComponentWraper.getChildWraperByPath("uiunit.uiunit.datas");
			Iterator<HAPDataWraper> dit = componentDatasWraper.iterate();
			while(dit.hasNext()){
				HAPEntityWraper componentDataWraper = (HAPEntityWraper)dit.next();
				HAPUIPath uiDataPath = new HAPUIPath();
				uiDataPath.parentId = faceComponentWraper.getID();
				uiDataPath.name = componentDataWraper.getChildWraperByPath("name").getData().toString();
				uiDataPath.path = "uiunit.uiunit.datas."+componentDataWraper.getId()+".datatype";
				String componentDataID = ((HAPEntityWraper)componentDataWraper.getChildWraperByPath("datatype")).getID().toString();
				inputDataMap.put(componentDataID, uiDataPath.toStringValue(HAPConstant.FORMAT_JSON));
			}
			
			//component command mapping
			HAPEntityContainerAttributeWraper commandsWraper = (HAPEntityContainerAttributeWraper)faceComponentWraper.getChildWraperByPath("uiunit.uiunit.commands");
			Iterator<HAPDataWraper> cit = commandsWraper.iterate();
			while(cit.hasNext()){
				HAPEntityWraper commandWraper = (HAPEntityWraper)cit.next();
				HAPUIPath uiCommandPath = new HAPUIPath();
				uiCommandPath.parentId = faceComponentWraper.getID();
				uiCommandPath.name = commandWraper.getChildWraperByPath("name").getData().toString();
				uiCommandPath.path = "uiunit.uiunit.commands."+commandWraper.getId();
				
				HAPEntityContainerAttributeWraper commandDataTypesWraper =(HAPEntityContainerAttributeWraper)commandWraper.getChildWraperByPath("datatypes");
				Iterator<HAPDataWraper> cdit = commandDataTypesWraper.iterate();
				while(cdit.hasNext()){
					HAPEntityWraper commandDataTypeWraper = (HAPEntityWraper)cdit.next();
					String commandName = commandDataTypeWraper.getChildWraperByPath("name").getData().toString();
					String commandDataID = ((HAPEntityWraper)commandDataTypeWraper.getChildWraperByPath("datatype")).getID().toString();
					uiCommandPath.parms.put(commandName, commandDataID);
				}				
				
				commandMap.put(commandWraper.getID().toString(), uiCommandPath.toStringValue(HAPConstant.FORMAT_JSON));
			}
			componentsJsonMap.put(uiId, HAPJsonUtility.getMapJson(componentInfoJsonMap));

			Map<String, String> eventNameIDJsonMap = new LinkedHashMap<String, String>();
			HAPEntityContainerAttributeWraper eventsWraper = (HAPEntityContainerAttributeWraper)faceComponentWraper.getChildWraperByPath("uiunit.uiunit.events");
			Iterator<HAPDataWraper> eit = eventsWraper.iterate();
			while(eit.hasNext()){
				HAPEntityWraper eventWraper = (HAPEntityWraper)eit.next();
				String eventName = eventWraper.getChildWraperByPath("name").getData().toString();
				eventNameIDJsonMap.put(eventName, eventWraper.getID().toString());
			}
			eventMap.put(uiId, HAPJsonUtility.getMapJson(eventNameIDJsonMap));
		}
		outMapJson.put("components", HAPJsonUtility.getMapJson(componentsJsonMap));
		
		
		HAPEntityContainerAttributeWraper defEventTunnelsWraper = (HAPEntityContainerAttributeWraper)faceDefinitionWraper.getChildWraperByPath("eventtunnels");
		Iterator<HAPDataWraper> tit = defEventTunnelsWraper.iterate();
		while(tit.hasNext()){
			HAPEntityWraper eventTunnelWraper = (HAPEntityWraper)tit.next();
			String eventID = ((HAPReferenceWraper)eventTunnelWraper.getChildWraperByPath("event")).getIDData().toString();
			Set<HAPEntityWraper> d = eventChannelMap.get(eventID);
			if(d==null)  d = new HashSet<HAPEntityWraper>();
			d.add(eventTunnelWraper);
			eventChannelMap.put(eventID, d);
		}		
		
		Map<String, String> eventChannelMapJson = new LinkedHashMap<String, String>();
		for(String ID : eventChannelMap.keySet()){
			 Set<HAPEntityWraper> eTunnels = eventChannelMap.get(ID);
			 List<String> eTunnelsJson = new ArrayList<String>();
			 for(HAPEntityWraper eTunnel : eTunnels){
				 eTunnelsJson.add(eTunnel.toStringValue(HAPConstant.FORMAT_JSON));
			 }
			 eventChannelMapJson.put(ID, HAPJsonUtility.getArrayJson(eTunnelsJson.toArray(new String[0])));
		}
		
		outMapJson.put("eventChannelMap", HAPJsonUtility.getMapJson(eventChannelMapJson));
		
		outMapJson.put("inputDataIDMap", HAPJsonUtility.getMapJson(inputDataMap));
		outMapJson.put("commandIDMap", HAPJsonUtility.getMapJson(commandMap));
		outMapJson.put("eventIDMap", HAPJsonUtility.getMapJson(eventMap));
		
		String content = HAPJsonUtility.getMapJson(outMapJson);
		return content;
	}
	
	public static String createDataTypeJson(HAPEntityWraper dataTypeWraper){
		Map<String, String> outMapJson = new LinkedHashMap<String, String>();
		
		String type = dataTypeWraper.getChildWraperByPath("type").getData().toString();
		if("atom".equals(type)){
			String categary = dataTypeWraper.getChildWraperByPath("atomtype.categary").getData().toString();
			String t = dataTypeWraper.getChildWraperByPath("atomtype.type").getData().toString();
			String rule = dataTypeWraper.getChildWraperByPath("atomtype.rule").getData().toString();
			String defaultVal = null;
			HAPDataWraper w1 = dataTypeWraper.getChildWraperByPath("atomtype.defaultValue");
			if(w1!=null){
				HAPData d1 = w1.getData();
				if(d1!=null)		defaultVal = d1.toString();
			}
			
			Map<String, String> dataTypeJson = new LinkedHashMap<String, String>();
			dataTypeJson.put("categary", categary);
			dataTypeJson.put("type", t);
			dataTypeJson.put("rule", rule);
			dataTypeJson.put("default", defaultVal);
			
			outMapJson.put("datatype", HAPJsonUtility.getMapJson(dataTypeJson));
			outMapJson.put("data", defaultVal);
		}
		else if("map".equals(type) || "tablerow".equals(type)){
			HAPEntityContainerAttributeWraper templateDataDefs = (HAPEntityContainerAttributeWraper)dataTypeWraper.getChildWraperByPath("childtypes");
			
			Map<String, String> dataJsonMap = new LinkedHashMap<String, String>();
			Iterator<HAPDataWraper> tit = templateDataDefs.iterate();
			while(tit.hasNext()){
				HAPEntityWraper childDataDef = (HAPEntityWraper)tit.next();
				String childName = childDataDef.getChildWraperByPath("name").getData().toString();
				dataJsonMap.put(childName, createDataDefJson(childDataDef));
			}
			outMapJson.put("data", HAPJsonUtility.getMapJson(dataJsonMap));

			Map<String, String> dataTypeJson = new LinkedHashMap<String, String>();
			dataTypeJson.put("categary", "complex");
			dataTypeJson.put("type", type);
			dataTypeJson.put("rule", dataTypeWraper.getChildWraperByPath("rule").getData().toString());
			
			outMapJson.put("datatype", HAPJsonUtility.getMapJson(dataTypeJson));
		}
		else if("table".equals(type)){
			HAPEntityContainerAttributeWraper templateDataDefs = (HAPEntityContainerAttributeWraper)dataTypeWraper.getChildWraperByPath("columntypes");
			Map<String, String> dataJsonMap = new LinkedHashMap<String, String>();
			Iterator<HAPDataWraper> tit = templateDataDefs.iterate();
			while(tit.hasNext()){
				HAPEntityWraper childDataDef = (HAPEntityWraper)tit.next();
				String childName = childDataDef.getChildWraperByPath("name").getData().toString();
				dataJsonMap.put(childName, createDataDefJson(childDataDef));
			}

			HAPEntityContainerAttributeWraper tableMapRefs = (HAPEntityContainerAttributeWraper)dataTypeWraper.getChildWraperByPath("columnmapref");
			List<String> mapRefJsonMap = new ArrayList<String>();
			Iterator<HAPDataWraper> kit = tableMapRefs.iterate();
			while(kit.hasNext()){
				HAPReferenceWraper childDataDef = (HAPReferenceWraper)kit.next();
				mapRefJsonMap.add(childDataDef.getIDData().toString());
			}
			
			Map<String, String> dataTypeJson = new LinkedHashMap<String, String>();
			dataTypeJson.put("categary", "complex");
			dataTypeJson.put("type", "table");
			dataTypeJson.put("rule", dataTypeWraper.getChildWraperByPath("rule").getData().toString());
			dataTypeJson.put("columns", HAPJsonUtility.getMapJson(dataJsonMap));
			dataTypeJson.put("columnsMapRef", HAPJsonUtility.getArrayJson(mapRefJsonMap.toArray(new String[0])));
			outMapJson.put("datatype", HAPJsonUtility.getMapJson(dataTypeJson));

			outMapJson.put("data", HAPJsonUtility.getArrayJson(new String[0]));
		}
		else if("list".equals(type)){
			
		}
		
		outMapJson.put("dataTypeID", dataTypeWraper.getID().toString());
		
		return HAPJsonUtility.getMapJson(outMapJson);
	}
	
	public static String createDataDefJson(HAPEntityWraper dataDefWraper){
		Map<String, String> outMapJson = new LinkedHashMap<String, String>();

		String name = dataDefWraper.getChildWraperByPath("name").getData().toString();
		String descp = dataDefWraper.getChildWraperByPath("description").getData().toString();
		HAPEntityWraper dataType = (HAPEntityWraper)dataDefWraper.getChildWraperByPath("datatype");
		
		outMapJson.put("name", name);
		outMapJson.put("description", descp);
		outMapJson.put("datatype", createDataTypeJson(dataType));
		
		return HAPJsonUtility.getMapJson(outMapJson);
	}
	
}
