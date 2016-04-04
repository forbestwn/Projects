package com.adiak.datasource.test;

import java.util.ArrayList;
import java.util.List;

import com.adiak.datasource.ADKDataSource;
import com.adiak.datasource.ADKDataSourceProvider;
import com.nosliw.HAPApplicationManager;
import com.nosliw.HAPUserContext;
import com.nosliw.HAPUserContextManager;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.exception.HAPServiceData;

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

	@Override
	public String[] getAllDataSourceNames() {
		List<String> out = new ArrayList<String>();
		
//		HAPServiceData dataService = this.getEntityManager().getEntitiesByGroup(TYPE_ENTITY);
//		HAPEntityWraper[] wrapers = (HAPEntityWraper[])dataService.getSuccessData();
//		for(HAPEntityWraper wraper : wrapers){
//			out.add(((ADKDataSource)wraper.getEntityData()).getName());
//		}
		return out.toArray(new String[0]);
	}

	@Override
	public ADKDataSource[] getAllDataSources() {
		
		List<ADKDataSource> out = new ArrayList<ADKDataSource>();
		
//		HAPServiceData dataService = this.getEntityManager().getEntitiesByGroup(TYPE_ENTITY);
//		HAPEntityWraper[] wrapers = (HAPEntityWraper[])dataService.getSuccessData();
//		for(HAPEntityWraper wraper : wrapers){
//			out.add((ADKDataSource)wraper.getEntityData());
//		}
		
		return out.toArray(new ADKDataSource[0]);
	}

	@Override
	public ADKDataSource getDataSource(String name) {
		
//		HAPServiceData dataService = this.getEntityManager().getEntitiesByGroup(TYPE_ENTITY);
//		HAPEntityWraper[] wrapers = (HAPEntityWraper[])dataService.getSuccessData();
//		for(HAPEntityWraper wraper : wrapers){
//			if(((ADKDataSource)wraper.getEntityData()).getName().equals(name))  return (ADKDataSource)wraper.getEntityData(); 
//		}
		return null;
	}

}
