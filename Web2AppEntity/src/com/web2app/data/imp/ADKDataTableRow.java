package com.web2app.data.imp;

import java.util.ArrayList;
import java.util.List;

import com.web2app.data.ADKData;


public class ADKDataTableRow extends ADKData{

	private List<ADKData> m_rowData;
	
	public ADKDataTableRow()
	{
		this.m_rowData = new ArrayList<ADKData>();
	}
	
	public ADKDataTableRow(int size)
	{
		this.m_rowData = new ArrayList<ADKData>(size);
		for(int i=0; i<size; i++)
		{
			this.m_rowData.add(null);
		}
	}

	public void setData(ADKData data, int index)
	{
		this.m_rowData.set(index, data);
	}
	
	public void addData(ADKData data)
	{
		this.m_rowData.add(data);
	}
	
	public ADKData getData(int index) 
	{
		return this.m_rowData.get(index);
	}
	
	public List<ADKData> getDatas()
	{
		return this.m_rowData;
	}
} 
