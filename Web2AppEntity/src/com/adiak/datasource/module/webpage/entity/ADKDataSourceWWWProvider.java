package com.adiak.datasource.module.webpage.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import com.adiak.datasource.ADKDataSource;
import com.adiak.datasource.ADKDataSourceProvider;
import com.nosliw.HAPApplicationManager;
import com.nosliw.HAPUserContext;
import com.nosliw.HAPUserContextManager;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.exception.HAPServiceData;
import com.nosliw.transaction.HAPEntityManager;

public class ADKDataSourceWWWProvider extends HAPUserContextManager implements ADKDataSourceProvider{

	Map<String, ADKDataSource> m_dataSources = null;
	
	public ADKDataSourceWWWProvider(HAPUserContext userContext) {
		super(userContext);
		m_dataSources = new LinkedHashMap<String, ADKDataSource>();
//		HAPServiceData data = this.get.getEntitiesByGroup("datasource.webpage.datasource");
//		if(data.isSuccess()){
//			HAPEntityWraper[] entities = (HAPEntityWraper[])data.getSuccessData();
//			for(HAPEntityWraper entity : entities){
//				ADKDataSource datasource = (ADKDataSource)entity.getEntityData();
//				this.m_dataSources.put(datasource.getName(), (ADKDataSource)entity);
//			}
//		}
	}

	@Override
	public String getType() {
		return "Web";
	}

	@Override
	public String[] getAllDataSourceNames()
	{
		return this.m_dataSources.keySet().toArray(new String[0]);
	}
	
	@Override
	public ADKDataSource[] getAllDataSources() {
		return this.m_dataSources.values().toArray(new ADKDataSource[0]);
	}

	@Override
	public ADKDataSource getDataSource(String name) {
		return this.m_dataSources.get(name);
	}

	@Override
	public String getDescription() {
		return "";
	}

}
