package com.adiak.datasource.transparent;

import com.adiak.datasource.ADKDataSource;
import com.adiak.datasource.ADKDataSourceProvider;
import com.nosliw.HAPApplicationManager;
import com.nosliw.HAPUserContext;
import com.nosliw.HAPUserContextManager;

public class ADKTransparentDataSourceProvider  extends HAPUserContextManager implements ADKDataSourceProvider{

	public static final String PROVIDER_NAME = "TRANSPARENT";
	
	public ADKTransparentDataSourceProvider(HAPUserContext userContext) {
		super(userContext);
	}

	@Override
	public String getType() {
		return PROVIDER_NAME;
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public String[] getAllDataSourceNames() {
		return null;
	}

	@Override
	public ADKDataSource[] getAllDataSources() {
		return null;
	}

	@Override
	public ADKDataSource getDataSource(String name) {
		return null;
	}

}
