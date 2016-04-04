package com.adiak.datasource.test;

import java.util.ArrayList;
import java.util.List;

import com.adiak.datasource.ADKDataSourcePoint;
import com.adiak.datasource.ADKDataSourceProvider;
import com.nosliw.application.HAPApplicationManager;
import com.nosliw.application.HAPUserContext;
import com.nosliw.application.HAPUserContextManager;
import com.nosliw.common.exception.HAPServiceData;

public class ADKTestDataSourceProvider extends HAPUserContextManager implements ADKDataSourceProvider{

	public static final String PROVIDER_NAME = "TEST";
	public static final String TYPE_ENTITY = "datasource.test.datasource";
	
	public ADKTestDataSourceProvider(HAPUserContext userContext) {
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
