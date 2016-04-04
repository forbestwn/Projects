package com.web2app.data.imp;

import com.web2app.data.ADKData;


public class ADKDataHtml extends ADKData{
	private String m_html;
	
	public ADKDataHtml(String html)
	{
		this.m_html = html;
	}
	
	public String getHtml()
	{
		return this.m_html;
	}
	
	public String toString()
	{
		return this.m_html;
	}
}
