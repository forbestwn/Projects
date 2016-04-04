package com.web2app.entity.datasource.source;

import java.io.InputStream;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.web2app.entity.datasource.DataSourceContext;
import com.web2app.entity.datasource.SourceProcessor;

public class HtmlSourceProcessor extends SourceProcessor{

	protected final static String ATTRIBUTE_URLPATH = "urlpath";
	protected final static String ATTRIBUTE_DOMPATH = "dompath";
	protected final static String ATTRIBUTE_LOCAL = "local";
	
	private String m_data = null;
	
	@Override
	public String process(DataSourceContext context) throws Exception
	{
		if(m_data == null)
		{
			this.m_data = this.getSource(context);
		}
		return this.m_data;
	}	

	private String getSource(DataSourceContext context) throws Exception
	{
		String urlpath = this.getAtomAttributeValue(ATTRIBUTE_URLPATH);
		String dompath = this.getAtomAttributeValue(ATTRIBUTE_DOMPATH);
		String local = this.getAtomAttributeValue(ATTRIBUTE_LOCAL);
		String basedompath = context.getBaseDompath();
		
		Document doc = null;
		if(local != null)
		{
			//get html from local file in assets
			InputStream stream = context.getResourceMan().getLocalHtmlStream(local);
			if(stream != null)
			{
				doc = Jsoup.parse(stream, null, context.getBaseUrl());
				stream.close();
			}
		}  
		else
		{
			//get html from internet 
//			String url = ADKUtility.getFullUrl(context, urlpath);
////				doc = Jsoup.connect(url).get();
//			doc = Jsoup.parse(new URL(url), 10000);
		}

		String path = basedompath;
		if(dompath!=null && !dompath.equals("")) 
		{
			path = basedompath + " > " + dompath;
		}
		Elements eles = doc.select(path);
		return eles.html();
	}
}
