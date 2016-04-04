package com.web2app.data.imp;

import com.web2app.data.ADKData;

public class ADKDataImage extends ADKData{
	private Object m_bitmap;

	public Object getBitmap()
	{
		return this.m_bitmap;
	}
	
	public void setBitmap(Object bitmap)
	{
		this.m_bitmap = bitmap;
	}
}
