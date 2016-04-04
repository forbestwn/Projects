package com.adiak.datasource.module.webpage.entity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.web2app.entity.datasource.DataSourceContext;

public class ADKUtility {

	public static Element selectElementFromRootFragment(String fragment, String criteria)
	{
		String head = "";
		String tail = "";
		String tagadd = "body";
		if(criteria!=null && criteria.equals("tr"))
		{
			head = "<table><tbody>";
			tail = "</tbody></table>";
			tagadd = "body>table>tbody";
		}
		
		String rowTag = null; 
		if(criteria==null || criteria.equals(""))
		{
			rowTag = tagadd;
		}
		else
		{
			rowTag = tagadd + ">" + criteria;
		}
		
		Document doc = Jsoup.parseBodyFragment(head + fragment + tail);
		Element out = doc.select(rowTag).first();
//		if(criteria==null || criteria.equals(""))
//		{
//			if(out != null)  out = out.children().first();
//		}
			
		return out;
	}

	public static Element selectElement(Element fragment, String criteria)
	{
		if(criteria==null || criteria.equals(""))  return fragment;
		else{
			return fragment.select(criteria).first();
		}
	}	
	
	
	static public String getFullUrl(DataSourceContext context, String url)
	{
		String baseurl = context.getBaseUrl();
		
		if(url==null || url.equals(""))  return baseurl;
		
		if(url.contains(baseurl))  return url;
		
		String a = url.substring(0, 1);
		if(a.equals("/"))  a = "";
		else a = "/";
		
		return baseurl + a + url; 
	}	
}
