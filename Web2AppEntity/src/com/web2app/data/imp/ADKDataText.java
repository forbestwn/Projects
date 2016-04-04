package com.web2app.data.imp;

import com.web2app.data.ADKData;


public class ADKDataText extends ADKData{

	private String m_text;

	public ADKDataText(){
		this.m_text = "";
	}
	
	public String getText()
	{
		return this.m_text;
	}
	
	public void setText(String text)
	{
		this.m_text = text;
	}
	
	public String toString()
	{
		return this.m_text;
	}
	
}
