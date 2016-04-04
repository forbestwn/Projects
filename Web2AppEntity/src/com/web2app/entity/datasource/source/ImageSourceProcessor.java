package com.web2app.entity.datasource.source;

import com.web2app.entity.datasource.DataSourceContext;
import com.web2app.entity.datasource.SourceProcessor;

public class ImageSourceProcessor  extends SourceProcessor{

	private final static String ATTRIBUTE_LOCAL = "local";

	@Override
	public String process(DataSourceContext context) throws Exception
	{
		return this.getAtomAttributeValue(ATTRIBUTE_LOCAL);
	} 
}
