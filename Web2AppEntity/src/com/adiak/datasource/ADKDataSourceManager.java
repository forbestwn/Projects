package com.adiak.datasource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.adiak.ADKUserContext;
import com.nosliw.HAPApplicationManager;
import com.nosliw.HAPUserContextManager;

public class ADKDataSourceManager extends HAPUserContextManager{

	private Map<String, ADKDataSourceProvider> m_dataSourceProviders;
	
	public ADKDataSourceManager(ADKUserContext sysContext)
	{
		super(sysContext);
		sysContext.setDataSourceManager(this);
		this.m_dataSourceProviders = new LinkedHashMap<String, ADKDataSourceProvider>();
	}
	
	public ADKDataSource[] getAllDataSources()
	{
		List<ADKDataSource> out = new ArrayList();
		for(String type : this.m_dataSourceProviders.keySet())
		{
			Collections.addAll(out, this.m_dataSourceProviders.get(type).getAllDataSources());
		}
		
		return out.toArray(new ADKDataSource[0]);
	}
	
	public ADKDataSource[] getDataSource(String type)
	{
		ADKDataSource out = null;
		ADKDataSourceProvider provider = this.m_dataSourceProviders.get(type);
		if(provider==null)  return null;
		return provider.getAllDataSources();
	}
	
	public ADKDataSource getDataSource(String type, String name)
	{
		ADKDataSource out = null;
		ADKDataSourceProvider provider = this.m_dataSourceProviders.get(type);
		if(provider==null)  return null;
		return provider.getDataSource(name);
	}
	
	public void registerDataSourceProvider(ADKDataSourceProvider provider)
	{
		this.m_dataSourceProviders.put(provider.getType(), provider);
	}
	
}
