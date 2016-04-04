package com.web2app.data.imp;

import java.util.ArrayList;
import java.util.List;

import com.web2app.data.ADKData;


public class ADKDataTable extends ADKData{

	public List<ADKDataTableRow> m_rows;
	public List<String> m_titles;
	
	public ADKDataTable()
	{
		m_rows = new ArrayList<ADKDataTableRow>();
		this.m_titles = new ArrayList<String>();
	}
	
	public List<ADKDataTableRow> getRows()
	{
		return this.m_rows;
	}
	
	public void addRow(ADKDataTableRow row)
	{
		m_rows.add(row);
	}
	
	public ADKDataTableRow getRow(int row)
	{
		return (ADKDataTableRow)m_rows.get(row);
	}
	
	public void addTitle(String title)
	{
		m_titles.add(title);
	}

	public String getTitle(int index)
	{
		return this.m_titles.get(index);
	}
	
	public int getTitleIndex(String title)
	{
		for(int i=0; i<this.m_titles.size(); i++)
		{
			if(title.equals(this.m_titles.get(i)))
			{
				return i;
			}
		}
		
		return -1;
	}
}
