package com.web2app.entity.datasource.source;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.web2app.data.imp.ADKDataURL;
import com.web2app.entity.datasource.DataSourceContext;

public class ExternalLinkSourceProcessor  extends HtmlSourceProcessor{

	private Map<String, String> m_sources = null;
	
	public ExternalLinkSourceProcessor()
	{
		this.m_sources = new LinkedHashMap<String, String>();
	}
	
	@Override
	public String process(DataSourceContext context)  throws Exception
	{
		String out = null;
		Object data = context.getSourceInput();
		if(data instanceof ADKDataURL)
		{
			ADKDataURL d = (ADKDataURL)data;
			String link = d.getLink(); 
			
			out = this.m_sources.get(link);
			if(out == null)
			{
				out = this.getSource(context, link);
				if(out != null)
				{
					this.m_sources.put(link, out);
				}
			}
		}
		return out; 
	} 

	private String getSource(DataSourceContext context, String urlpath) throws Exception
	{
//		String dompath = this.getAtomAttributeValue(ATTRIBUTE_DOMPATH);
//		String basedompath = context.getBaseDompath();
//		
//		Document doc = Jsoup.parse(new URL(ADKUtility.getFullUrl(context, urlpath)), 10000);
//
//		return doc.select(basedompath + " > " + dompath).html();
		return null;
	}
}
 