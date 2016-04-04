package com.adiak.datasource.module.webpage.entity.processor;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.nodes.Element;


import com.adiak.datasource.module.webpage.entity.ADKDataProcessor;

public class ADKImageDataProcessor  extends ADKDataProcessor{

	public static final String TAG_IMAGE = "img";
	public static final String TAG_ATTRIBUTE_SRC = "src";

	private static final String ATTRIBUTE_LOCAL = "local";

/*	
	@Override
	public ADKData doProcess(Element ele, DataSourceContext context)
	{
		String local = this.getAtomAttributeValue(ATTRIBUTE_LOCAL); 
		if(local!=null)
		{
			//image from local
			return this.getLocalImageData(local, context);
		}
		else
		{
			return this.getHtmlImageData(ele, context);
		}
	}
	
	private ADKDataImage getHtmlImageData(Element ele, DataSourceContext context)
	{
		ADKDataImage imageData = new ADKDataImage();
		Element imageEle = ele.select(TAG_IMAGE).first();
		if(imageEle != null)
		{
			String src = imageEle.attr(TAG_ATTRIBUTE_SRC);
			if(src != null)
			{
				String url = ADKUtility.getFullUrl(context, src);
				try { 
					Object bitmap = context.getResourceMan().getUrlImageObject(url);
					imageData.setBitmap(bitmap);
				} catch (Exception e) {
					e.printStackTrace();
				} 			
			}
		}
		return imageData;
	}
	
	private ADKDataImage getLocalImageData(String local, DataSourceContext context)
	{
		ADKDataImage imageData = new ADKDataImage();
		
		Object bitmap = context.getResourceMan().getLocalImageObject(local);
		imageData.setBitmap(bitmap);
		
		return imageData;
	}
*/	
} 
