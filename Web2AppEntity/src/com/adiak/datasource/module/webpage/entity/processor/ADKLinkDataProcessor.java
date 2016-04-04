package com.adiak.datasource.module.webpage.entity.processor;

import org.jsoup.nodes.Element;

import com.adiak.datasource.module.webpage.entity.ADKDataProcessor;

public class ADKLinkDataProcessor extends ADKDataProcessor{

	public static final String TAG_LINK = "a";
	public static final String TAG_ATTRIBUTE_HREF = "href";
/*
	@Override
	public ADKData doProcess(Element ele, DataSourceContext context)
	{
		ADKDataURL linkData = new ADKDataURL();
		
		Element linkEle = ele.select(TAG_LINK).first();
		if(linkEle!=null)
		{
			String href = linkEle.attr(TAG_ATTRIBUTE_HREF);
			String link = ADKUtility.getFullUrl(context, href);
			linkData.setLink(link);
			
			String text = linkEle.text();
			linkData.setText(text);
		}
		
		return linkData;
	}
*/	
}
