package com.adiak.datasource.transparent;

import com.adiak.datasource.ADKDataSourcePoint;
import com.adiak.datasource.ADKDataSourceProvider;
import com.nosliw.application.HAPApplicationManager;
import com.nosliw.application.HAPUserContext;
import com.nosliw.application.HAPUserContextManager;

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

}
