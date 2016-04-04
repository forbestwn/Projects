package com.nosliw.application;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.application.service.HAPServices;
import com.nosliw.application.util.template.HAPStringTemplateManager;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.entity.data.HAPEntityID;
import com.nosliw.entity.data.HAPEntityWraper;
import com.nosliw.entity.definition.HAPEntityDefinitionManager;
import com.nosliw.entity.options.HAPOptionsDefinitionManager;

public class HAPApplicationContext {

	private HAPEntityDefinitionManager m_entityDefManager;
	private HAPDataTypeManager m_dataTypeManager;
	private HAPDataValidationManager m_dataValidationManager;
	private HAPOptionsDefinitionManager m_optionsManager;
	private HAPApplicationInfo m_applicationInfo;
	private HAPStringTemplateManager m_stringTemplateMan;
	private HAPServices m_webServices;
	private HAPApplicationConfigure m_appConfigure;
	private HAPUIResourceManager m_uiResourceMan;
	
	private Map<String, HAPUserContext> m_userContexts;
	
	public void query(){
		
	}
	
	public HAPServiceData requestGetEntity(HAPUserContext reqUserContext, HAPEntityID entityID){
		HAPUserContext userContext = this.getUserContext(entityID.getUserContext());
		HAPEntityWraper wraper = userContext.getEntityWraperRequest(entityID, null);
		return HAPServiceData.createSuccessData(wraper);
	}
	
	
	public HAPApplicationContext(){
		this.m_userContexts = new LinkedHashMap<String, HAPUserContext>();
	}

	public void addUserContext(HAPUserContext userContext){
		this.m_userContexts.put(userContext.getId(), userContext);
	}
	public HAPUserContext getUserContext(HAPUserContextInfo userContext){
		return this.m_userContexts.get(userContext.getId());
	}
	public HAPUserContext getUserContext(String userContextId){
		return this.m_userContexts.get(userContextId);
	}
	
	public void removeUserContext(HAPUserContextInfo userContext){
		this.m_userContexts.remove(userContext.getId());
	}

	public HAPUIResourceManager getUIResourceManager(){return this.m_uiResourceMan;}
	public void setUIResourceManager(HAPUIResourceManager man){this.m_uiResourceMan=man;}
	
	public HAPApplicationInfo getApplicationInfo(){	return this.m_applicationInfo;	}
	public void setApplicationInfo(HAPApplicationInfo info){this.m_applicationInfo = info;}

	public HAPApplicationConfigure getApplicationConfigure(){return this.m_appConfigure;}
	public void setApplicationConfigure(HAPApplicationConfigure appConfigure){this.m_appConfigure=appConfigure;}
	
	public HAPEntityDefinitionManager getEntityDefinitionManager(){	return this.m_entityDefManager;	}
	public void setEntityDefinitionManager(HAPEntityDefinitionManager man){	this.m_entityDefManager=man;}
	
	public HAPDataTypeManager getDataTypeManager(){return this.m_dataTypeManager;}
	public void setDataTypeManager(HAPDataTypeManager man){this.m_dataTypeManager=man;}
	
	public HAPDataValidationManager getDataValidationManager(){return this.m_dataValidationManager;}
	public void setDataValidationManager(HAPDataValidationManager man){this.m_dataValidationManager=man;}

	public HAPOptionsDefinitionManager getOptionsManager(){return this.m_optionsManager;}
	public void setOptionsManager(HAPOptionsDefinitionManager man){this.m_optionsManager=man;}
	
	public HAPStringTemplateManager getStringTemplateManager(){return this.m_stringTemplateMan;}
	public void setStringTemplateManager(HAPStringTemplateManager man){this.m_stringTemplateMan=man;}

	public HAPServices getWebServices(){return this.m_webServices;}
	public void setWebServices(HAPServices webServices){this.m_webServices=webServices;}
	
}
