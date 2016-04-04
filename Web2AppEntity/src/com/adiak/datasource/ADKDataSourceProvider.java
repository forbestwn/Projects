package com.adiak.datasource;

public interface ADKDataSourceProvider {

	public String getType();

	public String getDescription();
	
	public String[] getAllDataSourceNames();
	
	public ADKDataSource[] getAllDataSources();
	
	public ADKDataSource getDataSource(String name);
	
	
}
