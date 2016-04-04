package com.web2app.entity.datasource;

import java.util.List;

import com.adiak.datasource.module.webpage.entity.ADKDataProcessor;
import com.nosliw.entity.HAPEntityData;
import com.nosliw.entity.HAPDataWraper;

public class DataSource extends HAPEntityData{

	private static final String ATTRIBUTE_CONTEXT = "context";
	private static final String ATTRIBUTE_DATAS = "datas";
	private static final String ATTRIBUTE_SOURCE = "source";
	private static final String ATTRIBUTE_FILTERS = "filters";
	
	public SourceProcessor getSourceProcessor()
	{
//		HAPValueWraper sourceEntity = this.getAttributeValue(ATTRIBUTE_SOURCE);
//		if(sourceEntity != null){
//			return (SourceProcessor)sourceEntity.getComplexEntity();
//		}
//		else return null;
		return null;
	}

	public List<HAPDataWraper> getFilters()
	{
//		HAPValueWraper filtersEntity = this.getAttributeValue(ATTRIBUTE_FILTERS);
//		if(filtersEntity == null)  return null;
//		else return filtersEntity.getListValue();
		return null;
	}
	
	public ADKDataProcessor getDataProcessor(String name)
	{
//		HAPValueWraper entity = this.getCombinedAttributeValue(ATTRIBUTE_DATAS+"."+name);
//		if(entity==null)  return null;
//		return (ADKDataProcessor)entity.getComplexEntity();
		return null;
	}

	public String getContext()
	{
		String contextName = this.getAtomAttributeValue(ATTRIBUTE_CONTEXT);
		return contextName;
	}
}
