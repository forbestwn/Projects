package com.adiak.datasource.module.webpage.entity;

import java.util.List;

import org.jsoup.nodes.Element;

import com.nosliw.data.HAPData;
import com.nosliw.entity.HAPEntityData;
import com.nosliw.entity.HAPDataWraper;
import com.web2app.data.ADKData;
import com.web2app.entity.datasource.DataSourceContext;
import com.web2app.entity.datasource.FilterProcessor;

public class ADKDataProcessor extends HAPEntityData{

	private static final String ATTRIBUTE_FILTERS = "filters";
	
	private final static String ATTRIBUTE_NAME = "name";
	private final static String ATTRIBUTE_TYPE = "type";
	private final static String ATTRIBUTE_DOMPATH = "dompath";
	
	public HAPData process()
	{
		return null;
	}
	
	
/*
	public String getName()
	{
		return this.getAtomAttributeValue(ATTRIBUTE_NAME);
	}
	
	public String getType()
	{
		return this.getAtomAttributeValue(ATTRIBUTE_TYPE);
	}
	
	public String getDompath()
	{
		return this.getAtomAttributeValue(ATTRIBUTE_DOMPATH);
	}

	public HAPValueWraper[] getFilters()
	{
		HAPValueWraper filtersEntity = this.getAttributeValue(ATTRIBUTE_FILTERS);
		HAPValueWraper[] out = new HAPValueWraper[0];
		if(filtersEntity != null)
		{ 
			List<HAPValueWraper> filters = (List<HAPValueWraper>)filtersEntity.getListValue();
			out = filters.toArray(new HAPValueWraper[0]);
		}
		return out;
	}

	
	
	final public ADKData process(String input, DataSourceContext context)
	{
		String outString = input;
		
		HAPValueWraper[] filters = this.getFilters();
		for(int i=0; i<filters.length; i++)
		{
			FilterProcessor filter = (FilterProcessor)filters[i].getComplexEntity();
			outString = filter.process(outString, context); 
		}
		
		String domPath = this.getDompath();
		Element ele = ADKUtility.selectElementFromRootFragment(outString, domPath);
		    
		return this.doProcess(ele, context);
	}
	
	public ADKData doProcess(Element ele, DataSourceContext context)
	{
		return new ADKData();
	}
*/
}
