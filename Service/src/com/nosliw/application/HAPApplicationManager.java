package com.nosliw.application;

import com.nosliw.application.service.HAPServices;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.entity.definition.HAPEntityDefinitionManager;


public class HAPApplicationManager {

	private transient HAPApplicationContext m_applicationContext = null;

	public HAPApplicationManager(HAPApplicationContext applicationContext){
		this.m_applicationContext = applicationContext;
	}

	//reset the data
	public void init() throws Exception{
		this.clear();
		this.load();
	}
	
	//clear the data, or init the data 
	public void clear(){}
	
	//load the data into manager
	public void load(){}

	//helper method to get all the system manager through system context object 
	protected HAPEntityDefinitionManager getEntityDefinitionManager(){return this.getApplicationContext().getEntityDefinitionManager();}
	protected HAPDataTypeManager getDataTypeManager(){return this.getApplicationContext().getDataTypeManager();}
	
	protected HAPDataValidationManager getDataValidationManager(){return this.getApplicationContext().getDataValidationManager();}
	protected HAPApplicationContext getApplicationContext(){return this.m_applicationContext;}
	
	public HAPServices getWebServices(){return this.getApplicationContext().getWebServices();}
	public void setWebServices(HAPServices webServices){this.getApplicationContext().setWebServices(webServices);}
	
}
