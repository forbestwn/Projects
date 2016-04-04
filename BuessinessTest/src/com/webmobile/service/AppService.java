package com.webmobile.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.HAPApplicationContext;
import com.nosliw.HAPApplicationManager;
import com.nosliw.HAPUserContext;
import com.nosliw.data.HAPData;
import com.nosliw.data.HAPWraper;
import com.nosliw.entity.data.HAPDataWraper;
import com.nosliw.entity.data.HAPEntityContainerAttributeWraper;
import com.nosliw.entity.data.HAPEntityID;
import com.nosliw.entity.data.HAPEntityWraper;
import com.nosliw.entity.data.HAPReferenceWraper;
import com.nosliw.entity.imp.datadefinition.HAPChainProcessorEntity;
import com.nosliw.entity.imp.datadefinition.HAPDataTunnelEndPointEntity;
import com.nosliw.exception.HAPServiceData;
import com.nosliw.ui.HAPUIResource;
import com.nosliw.ui.HAPUIResourceImporter;
import com.nosliw.util.HAPConstant;
import com.nosliw.util.HAPJsonUtility;
import com.nosliw.util.HAPUtility;
import com.test.business.manager.UserContext;
import com.test.business.manager.UserContextManager;

public class AppService extends UserContextManager{

	public AppService(UserContext sysContext) {
		super(sysContext);
	}
	
	public static String getApplicationDataWithFacePaths(HAPEntityID applicationId, String facePath, HAPApplicationContext appContext) throws Exception{
		Map<String, String> jsonOutMap = new LinkedHashMap<String, String>();
		
		HAPUserContext userContext = appContext.getUserContext("default");
		HAPEntityWraper applicationWraper = userContext.getEntityWraperRequest(applicationId, null);

		String name = applicationWraper.getChildWraperByPath("name").getData().toString();
		jsonOutMap.put("name", name);

		HAPEntityWraper uiWraper = ((HAPReferenceWraper)applicationWraper.getChildWraperByPath("appui")).getReferenceWraper();
		Set<HAPEntityID> templates = new HashSet<HAPEntityID>();
		String[] filters = {facePath};
		jsonOutMap.put("appui", getUiUnitData(uiWraper, "application", "application", filters, templates, appContext));
		
		Map<String, String> templatesJsonMap = new LinkedHashMap<String, String>();
		for(HAPEntityID template : templates){
			templatesJsonMap.put(template.toString(), getUiTemplateData(template, appContext));
		}
		jsonOutMap.put("templates", HAPJsonUtility.getMapJson(templatesJsonMap));
		
		return HAPJsonUtility.getMapJson(jsonOutMap);
	}
	
	
	public static String getApplicationData(HAPEntityID applicationId, HAPApplicationContext appContext) throws Exception{
		Map<String, String> jsonOutMap = new LinkedHashMap<String, String>();
		
		HAPUserContext userContext = appContext.getUserContext("default");
		HAPEntityWraper applicationWraper = userContext.getEntityWraperRequest(applicationId, null);

		String name = applicationWraper.getChildWraperByPath("name").getData().toString();
		jsonOutMap.put("name", name);

		HAPEntityID startPointID = ((HAPReferenceWraper)applicationWraper.getChildWraperByPath("startpoint")).getIDData();
		jsonOutMap.put("startpoint", startPointID.toString());
		
		HAPEntityWraper uiWraper = ((HAPReferenceWraper)applicationWraper.getChildWraperByPath("appui")).getReferenceWraper();
		Set<HAPEntityID> templates = new HashSet<HAPEntityID>();
		jsonOutMap.put("appui", getUiUnitData(uiWraper, "application", "application", null, templates, appContext));
		
		Map<String, String> templatesJsonMap = new LinkedHashMap<String, String>();
		for(HAPEntityID template : templates){
			templatesJsonMap.put(template.toString(), getUiTemplateData(template, appContext));
		}
		jsonOutMap.put("templates", HAPJsonUtility.getMapJson(templatesJsonMap));
		
		Map<String, String> startPointsJsonMap = new LinkedHashMap<String, String>();
		HAPEntityContainerAttributeWraper startPointsWraper = (HAPEntityContainerAttributeWraper)applicationWraper.getChildWraperByPath("startpoints");
		Iterator<HAPDataWraper> sit = startPointsWraper.iterate();
		while(sit.hasNext()){
			Map<String, String> startPointJsonMap = new LinkedHashMap<String, String>();
			HAPReferenceWraper startPointRefWraper = (HAPReferenceWraper)sit.next();
			HAPEntityWraper startPointWraper = startPointRefWraper.getReferenceWraper();

			Map<String, String> faceJsonMap = new LinkedHashMap<String, String>();
			HAPEntityID  faceID= ((HAPReferenceWraper)startPointWraper.getChildWraperByPath("face")).getIDData();
			HAPEntityWraper faceWraper= ((HAPReferenceWraper)startPointWraper.getChildWraperByPath("face")).getReferenceWraper();
			HAPEntityWraper faceUnitWraper = (HAPEntityWraper)faceWraper.getChildWraperByPath("uiunit");
			
			faceJsonMap.put("faceID", faceID.toString());
			faceJsonMap.put("faceUnitID", faceUnitWraper.getID().toString());
			
			startPointJsonMap.put("face", HAPJsonUtility.getMapJson(faceJsonMap));
			
			startPointsJsonMap.put(startPointRefWraper.getIDData().toString(), HAPJsonUtility.getMapJson(startPointJsonMap));
		}
		jsonOutMap.put("startpoints", HAPJsonUtility.getMapJson(startPointsJsonMap));

		return HAPJsonUtility.getMapJson(jsonOutMap);
	}
	
	public static boolean ifPassPathFilter(String path, String[] filters){
		boolean passed = false;
		if(filters==null || filters.length==0){
			passed = true;
		}
		else{
			for(String filter : filters){
				if(filter.startsWith(path)){
					passed = true;
					break;
				}
				if(path.startsWith(filter)){
					passed = true;
					break;
				}
			}
		}
		return passed;
	}
	
	public static String getUiUnitData(HAPEntityWraper uiUnitWraper, String uiPath, String subPath, String[] filters, Set<HAPEntityID> templates, HAPApplicationContext appContext){
		
		Map<String, String> jsonOutMap = new LinkedHashMap<String, String>();
		
		String categary = uiUnitWraper.getChildWraperByPath("categary").getData().toString();
		String type = uiUnitWraper.getChildWraperByPath("type").getData().toString();
		String source = uiUnitWraper.getChildWraperByPath("source").getData().toString();
		jsonOutMap.put("categary", categary);
		jsonOutMap.put("type", type);
		jsonOutMap.put("source", source);
		jsonOutMap.put("uipath", uiPath);
		jsonOutMap.put("subpath", subPath);
		
		if(source.equals("template")){
			jsonOutMap.put("ID", uiUnitWraper.getID().toString());

			HAPEntityID templateID = ((HAPReferenceWraper)(uiUnitWraper.getChildWraperByPath("template"))).getIDData();
			templates.add(templateID);
			jsonOutMap.put("template", templateID.toString());

			HAPEntityWraper configureWraper = (HAPEntityWraper)uiUnitWraper.getChildWraperByPath("configure");
			Map<String, String> configureJsonMap = new LinkedHashMap<String, String>();

			List<String> datainputsJsonList = new ArrayList<String>();
			HAPEntityContainerAttributeWraper datainputsWraper = (HAPEntityContainerAttributeWraper)configureWraper.getChildWraperByPath("datainputs");
			Iterator<HAPDataWraper> dit = datainputsWraper.iterate();
			while(dit.hasNext()){
				HAPEntityWraper dataTunnelWraper = (HAPEntityWraper)dit.next();
				datainputsJsonList.add(getDataTunnelData(dataTunnelWraper, uiPath, appContext));
			}
			configureJsonMap.put("datainputs", HAPJsonUtility.getArrayJson(datainputsJsonList.toArray(new String[0])));
			
			HAPEntityContainerAttributeWraper eventTunnelsWraper = (HAPEntityContainerAttributeWraper)configureWraper.getChildWraperByPath("eventtunnels");
			configureJsonMap.put("eventtunnels", getEventTunnelsData(eventTunnelsWraper, uiPath, appContext));
			
			Map<String, String> componentsJsonMap = new LinkedHashMap<String, String>();
			HAPEntityContainerAttributeWraper componentsWraper = (HAPEntityContainerAttributeWraper)configureWraper.getChildWraperByPath("components");
			Iterator<HAPDataWraper> cit = componentsWraper.iterate();
			while(cit.hasNext()){
				Map<String, String> componentJsonMap = new LinkedHashMap<String, String>();
				HAPEntityWraper componentWraper = (HAPEntityWraper)cit.next();
				String name = componentWraper.getChildWraperByPath("name").getData().toString();
				componentJsonMap.put("name", name);
				
				String comPath = "component|"+name;
				String comFullPath = uiPath+"."+ comPath;
				
				if(ifPassPathFilter(comFullPath, filters)){
					HAPEntityContainerAttributeWraper comConfiguresWraper = (HAPEntityContainerAttributeWraper)componentWraper.getChildWraperByPath("configures");
					Iterator<HAPDataWraper> ccit = comConfiguresWraper.iterate();
					List<String> comConfigureJsonList = new ArrayList<String>();
					while(ccit.hasNext()){
						HAPEntityWraper comConfigureWraper = (HAPEntityWraper)ccit.next();
						comConfigureJsonList.add(getDataTunnelData(comConfigureWraper, uiPath, appContext));
					}				
					componentJsonMap.put("configures", HAPJsonUtility.getArrayJson(comConfigureJsonList.toArray(new String[0])));
					
					HAPEntityWraper unitWraper = (HAPEntityWraper)componentWraper.getChildWraperByPath("uiunit");
					if(!unitWraper.isEmpty()){
						componentJsonMap.put("uiunit", getUiUnitData(unitWraper, comFullPath, comPath, filters, templates, appContext));
					}
					
					componentsJsonMap.put(name, HAPJsonUtility.getMapJson(componentJsonMap));
				}
			}
			configureJsonMap.put("components", HAPJsonUtility.getMapJson(componentsJsonMap));

			Map<String, String> componentContainersJsonMap = new LinkedHashMap<String, String>();
			HAPEntityContainerAttributeWraper componentContainersWraper = (HAPEntityContainerAttributeWraper)configureWraper.getChildWraperByPath("componentcontainers");
			Iterator<HAPDataWraper> ccsit = componentContainersWraper.iterate();
			while(ccsit.hasNext()){
				Map<String, String> componentContainerJsonMap = new LinkedHashMap<String, String>();
				HAPEntityWraper componentContainerWraper = (HAPEntityWraper)ccsit.next();
				String name = componentContainerWraper.getChildWraperByPath("name").getData().toString();
				componentContainerJsonMap.put("name", name);
				
				HAPEntityContainerAttributeWraper comConfiguresWraper = (HAPEntityContainerAttributeWraper)componentContainerWraper.getChildWraperByPath("configures");
				Iterator<HAPDataWraper> ccit = comConfiguresWraper.iterate();
				List<String> comConfigureJsonList = new ArrayList<String>();
				while(ccit.hasNext()){
					HAPEntityWraper comConfigureWraper = (HAPEntityWraper)ccit.next();
					comConfigureJsonList.add(getDataTunnelData(comConfigureWraper, uiPath, appContext));
				}				
				componentContainerJsonMap.put("configures", HAPJsonUtility.getArrayJson(comConfigureJsonList.toArray(new String[0])));
				

				HAPEntityContainerAttributeWraper uiunitsWraper = (HAPEntityContainerAttributeWraper)componentContainerWraper.getChildWraperByPath("uiunits");
				Iterator<HAPDataWraper> uit = uiunitsWraper.iterate();
				Map<String, String> uiunitsJsonMap = new LinkedHashMap<String, String>();
				while(uit.hasNext()){
					HAPEntityWraper uiunitWraper = (HAPEntityWraper)uit.next();
					String path = uiunitWraper.getId();
					
					String comPath = "components|"+name+"|"+path;
					String comFullPath = uiPath+"."+ comPath;
					
					if(ifPassPathFilter(comFullPath, filters)){
						uiunitsJsonMap.put(path, getUiUnitData(uiunitWraper, comFullPath, comPath, filters, templates, appContext));
					}					
				}				
				componentContainerJsonMap.put("uiunits", HAPJsonUtility.getMapJson(uiunitsJsonMap));
				componentContainersJsonMap.put(name, HAPJsonUtility.getMapJson(componentContainerJsonMap));
			}
			configureJsonMap.put("componentcontainers", HAPJsonUtility.getMapJson(componentContainersJsonMap));
			
			jsonOutMap.put("configure", HAPJsonUtility.getMapJson(configureJsonMap));
		}
		if(source.equals("reference")){
			HAPEntityWraper refUiUnitWraper = ((HAPReferenceWraper)uiUnitWraper.getChildWraperByPath("configure")).getReferenceWraper();
			return getUiUnitData(refUiUnitWraper, uiPath, subPath, filters, templates, appContext);
		}		
		
		return HAPJsonUtility.getMapJson(jsonOutMap);
	}
	
	
	
	public static String getUiTemplateData(HAPEntityID entityID, HAPApplicationContext appContext) throws Exception{
		
		TemplateUserContext templateUserContext = (TemplateUserContext)appContext.getUserContext("uitemplate");
		UITemplateManager templateManager = templateUserContext.getUITemplateManager();
		HAPEntityWraper templateWraper = templateUserContext.getEntityWraperRequest(entityID, null);

		Map<String, String> jsonOutMap = new LinkedHashMap<String, String>();
		String name = templateWraper.getChildWraperByPath("name").getData().toString();
		String categary = templateWraper.getChildWraperByPath("categary").getData().toString();
		String type = templateWraper.getChildWraperByPath("type").getData().toString();
		jsonOutMap.put("name", name);
		jsonOutMap.put("categary", categary);
		jsonOutMap.put("type", type);

		String templateResourceFile = templateManager.getResourceFileByEntity(templateWraper);
		HAPUIResource templateResource = HAPUIResourceImporter.readUIResource(templateResourceFile);
		jsonOutMap.put("uiresource", templateResource.toStringValue(HAPConstant.FORMAT_JSON));
		
		
		HAPEntityWraper infoWraper = (HAPEntityWraper)templateWraper.getChildWraperByPath("info");
		
		Map<String, String> datasJsonMap = new LinkedHashMap<String, String>();
		HAPEntityContainerAttributeWraper datasWraper = (HAPEntityContainerAttributeWraper)infoWraper.getChildWraperByPath("datas");
		Iterator<HAPDataWraper> dit = datasWraper.iterate();
		while(dit.hasNext()){
			Map<String, String> dataJsonMap = new LinkedHashMap<String, String>();
			HAPEntityWraper dataWraper = (HAPEntityWraper)dit.next();
			String defaultValue = getDataDefDefaultValue(dataWraper);
			if(HAPUtility.isStringNotEmpty(defaultValue)){
				HAPData defaultData = appContext.getDataTypeManager().parseString(defaultValue, null, null);
				dataJsonMap.put("data", defaultData.toStringValue(HAPConstant.FORMAT_JSON_DATATYPE));
				datasJsonMap.put(dataWraper.getChildWraperByPath("name").getData().toString(), HAPJsonUtility.getMapJson(dataJsonMap));
			}
		}
		jsonOutMap.put("datas", HAPJsonUtility.getMapJson(datasJsonMap));
		
		
		return HAPJsonUtility.getMapJson(jsonOutMap);
	}
	
	private static String getDataDefDefaultValue(HAPEntityWraper defWraper){
		String out = null;
		HAPDataWraper wraper = defWraper.getChildWraperByPath("datatypeinfo.datatype.atomtype.defaultvalue");
		if(wraper!=null){
			HAPData data = wraper.getData();
			if(data!=null){
				return data.toString();
			}
		}
		return out;
	}
	
	
	private static String getEventTunnelsData(HAPEntityContainerAttributeWraper eventTunnelsWraper, String uiPath, HAPApplicationContext appContext){

		Map<String, String> jsonOutMap = new LinkedHashMap<String, String>();
		
		Iterator<HAPDataWraper> kit = eventTunnelsWraper.iterate();
		while(kit.hasNext()){
			HAPEntityWraper eventTunnelWraper = (HAPEntityWraper)kit.next();

			Map<String, String> eventTunnelJsonMap = new LinkedHashMap<String, String>();
			
			String eventPath = eventTunnelWraper.getChildWraperByPath("eventpath").getData().toString();
			String commandPath = eventTunnelWraper.getChildWraperByPath("commandpath").getData().toString();
			eventTunnelJsonMap.put("eventpath", getAbsolutePath(uiPath, eventPath));
			eventTunnelJsonMap.put("commandpath", getAbsolutePath(uiPath, commandPath));
			
			List<String> dataTunnelsJsonList = new ArrayList<String>();
			HAPEntityContainerAttributeWraper dataTunnelsWraper = (HAPEntityContainerAttributeWraper)eventTunnelWraper.getChildWraperByPath("datatunnels");
			Iterator<HAPDataWraper> sit = dataTunnelsWraper.iterate();
			while(sit.hasNext()){
				HAPEntityWraper dataTunnelWraper = (HAPEntityWraper)sit.next();
				dataTunnelsJsonList.add(getDataTunnelData(dataTunnelWraper, uiPath, appContext));
			}
			eventTunnelJsonMap.put("datatunnels", HAPJsonUtility.getArrayJson(dataTunnelsJsonList.toArray(new String[0])));
			
			List<String> extraDataJsonList = new ArrayList<String>();
			HAPEntityContainerAttributeWraper extrasWraper = (HAPEntityContainerAttributeWraper)eventTunnelWraper.getChildWraperByPath("extra");
			Iterator<HAPDataWraper> eit = extrasWraper.iterate();
			while(eit.hasNext()){
				HAPEntityWraper extraWraper = (HAPEntityWraper)eit.next();
				extraDataJsonList.add(getEndPointData(extraWraper, uiPath, appContext));
			}
			eventTunnelJsonMap.put("extra", HAPJsonUtility.getArrayJson(extraDataJsonList.toArray(new String[0])));

			jsonOutMap.put(getAbsolutePath(uiPath, eventPath), HAPJsonUtility.getMapJson(eventTunnelJsonMap));
		}
		return HAPJsonUtility.getMapJson(jsonOutMap);
	}
	

	private static String getDataTunnelData(HAPEntityWraper dataTunnelWraper, String uiPath, HAPApplicationContext appContext){
		Map<String, String> jsonOutMap = new LinkedHashMap<String, String>();
		HAPEntityWraper dataInWraper = (HAPEntityWraper)dataTunnelWraper.getChildWraperByPath("in");
		HAPEntityWraper dataOutWraper = (HAPEntityWraper)dataTunnelWraper.getChildWraperByPath("out");
		jsonOutMap.put("in", getEndPointData(dataInWraper, uiPath, appContext));
		jsonOutMap.put("out", getEndPointData(dataOutWraper, uiPath, appContext));
		
		List<String> spotTunnelsJsonList = new ArrayList<String>();
		HAPEntityContainerAttributeWraper spotTunnelsWraper = (HAPEntityContainerAttributeWraper)dataTunnelWraper.getChildWraperByPath("spottunnels");
		Iterator<HAPDataWraper> sit = spotTunnelsWraper.iterate();
		while(sit.hasNext()){
			Map<String, String> spotTunnelJsonMap = new LinkedHashMap<String, String>();
			HAPEntityWraper spotTunnelWraper = (HAPEntityWraper)sit.next();
			String inpath = spotTunnelWraper.getChildWraperByPath("inpath").getData().toString();
			String outpath = spotTunnelWraper.getChildWraperByPath("outpath").getData().toString();
			String fun = spotTunnelWraper.getChildWraperByPath("function").getData().toString();
			spotTunnelJsonMap.put("inpath", inpath);
			spotTunnelJsonMap.put("outpath", outpath);
			spotTunnelJsonMap.put("function", fun);
			spotTunnelsJsonList.add(HAPJsonUtility.getMapJson(spotTunnelJsonMap));
		}		
		jsonOutMap.put("spottunnels", HAPJsonUtility.getArrayJson(spotTunnelsJsonList.toArray(new String[0])));
		
		return HAPJsonUtility.getMapJson(jsonOutMap);
	}
	
	private static String getEndPointData(HAPEntityWraper endPointWraper, String uipath, HAPApplicationContext appContext){
		Map<String, String> jsonOutMap = new LinkedHashMap<String, String>();
		
		HAPDataTunnelEndPointEntity endPointEntity = (HAPDataTunnelEndPointEntity)endPointWraper.getData();
		
		String type = endPointWraper.getChildWraperByPath("type").getData().toString();
		jsonOutMap.put("type", type);
		if(type.equals("uipath")){
			String path = endPointWraper.getChildWraperByPath("point").getData().toString();
			jsonOutMap.put("ID", getAbsolutePath(uipath, path));
		}
		else if(type.equals("static")){
			String value = endPointWraper.getChildWraperByPath("point").getData().toString();
			HAPData data = appContext.getDataTypeManager().parseString(value, null, null);
			jsonOutMap.put("data", data.toStringValue(HAPConstant.FORMAT_JSON_DATATYPE));
		}
		else{
			jsonOutMap.put("ID", endPointEntity.getEndPointID());
		}
		
		return HAPJsonUtility.getMapJson(jsonOutMap);
	}

	private static String getAbsolutePath(String base, String path){
		if(path.startsWith("\\..")){
			int n = base.lastIndexOf(".");
			return getAbsolutePath(path.substring(3), base.substring(0, n));
		}
		else if(path.startsWith("\\.")){
			return base + "." + path.substring(2);
		}
		else{
			return path;
		}
	}	
}
