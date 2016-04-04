package com.adiak.datasource;

import java.util.Map;

import com.nosliw.application.HAPUserContext;
import com.nosliw.application.HAPUserContextManager;

public class ADKDataSourceManager extends HAPUserContextManager{

	private Map<String, ADKDataSourceProvider> m_dataSourceProviders;
	
	public ADKDataSourceManager(HAPUserContext userContext)
	{
		super(userContext);
//		userContext.setDataSourceManager(this);
//		this.m_dataSourceProviders = new LinkedHashMap<String, ADKDataSourceProvider>();
	}
	
	public void registerDataSourceProvider(ADKDataSourceProvider provider)
	{
		this.m_dataSourceProviders.put(provider.getType(), provider);
	}
	
}
