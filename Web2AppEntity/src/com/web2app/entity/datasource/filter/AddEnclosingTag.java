package com.web2app.entity.datasource.filter;

import com.web2app.entity.datasource.DataSourceContext;
import com.web2app.entity.datasource.FilterProcessor;

public class AddEnclosingTag extends FilterProcessor{

	private final static String ATTRIBUTE_TAG = "tag";

	public String getTag()
	{
		return this.getAtomAttributeValue(ATTRIBUTE_TAG);
	}
	
	@Override
	public String process(String input, DataSourceContext context)
	{
		String tag = this.getTag();
		
		return "<"+tag+"> " + input + " </" + tag+">"; 
	}
	
}
