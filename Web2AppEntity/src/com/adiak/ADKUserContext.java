package com.adiak;

import com.adiak.datasource.ADKDataSourceManager;
import com.nosliw.HAPUserContext;

public class ADKUserContext extends HAPUserContext{
	
	public ADKUserContext(ADKApplicationContext appContext, String id){
		super(appContext, id);
	}


	private ADKDataSourceManager m_dataSourceManager;
	public ADKDataSourceManager getDataSourceManager(){	return this.m_dataSourceManager;}
	public void setDataSourceManager(ADKDataSourceManager man){	this.m_dataSourceManager = man;}

	
	private ADKResourceManager m_resourceMan;
	public ADKResourceManager getResourceManager(){	return this.m_resourceMan;}
	public void setResourceManager(ADKResourceManager man){	this.m_resourceMan = man;}
	
}
