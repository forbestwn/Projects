package com.nosliw.application;

import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.entity.dataaccess.HAPDataContext;
import com.nosliw.entity.definition.HAPEntityDefinitionManager;

public class HAPUserContextManager {

	private transient HAPUserContext m_userContext = null;

	public HAPUserContextManager(HAPUserContext userContext){
		this.m_userContext = userContext;
	}

	//reset the data
	public void init(){
		this.clear();
		this.load();
	}
	
	//clear the data, or init the data 
	public void clear(){}
	
	//load the data into manager
	public void load(){}

	//helper method to get all the system manager through system context object 
	protected HAPDataContext getEntityManager(){return this.m_userContext.getEntityManager();}

	
	protected HAPEntityDefinitionManager getEntityDefinitionManager(){return this.getApplicationContext().getEntityDefinitionManager();}
	protected HAPDataTypeManager getDataTypeManager(){return this.getApplicationContext().getDataTypeManager();}
	protected HAPDataValidationManager getDataValidationManager(){return this.getApplicationContext().getDataValidationManager();}

	protected HAPUserContext getUserContext(){return this.m_userContext;}
	protected HAPApplicationContext getApplicationContext(){return this.getUserContext().getApplicationContext();}
	
}

