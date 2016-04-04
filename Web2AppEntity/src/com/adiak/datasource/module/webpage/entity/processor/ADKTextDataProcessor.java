package com.adiak.datasource.module.webpage.entity.processor;

import org.jsoup.nodes.Element;

import com.adiak.datasource.module.webpage.entity.ADKDataProcessor;
import com.nosliw.simple.HAPSimpleXmlEntity;

public class ADKTextDataProcessor extends ADKDataProcessor{

/*	
	@Override
	public ADKData doProcess(Element ele, DataSourceContext context)
	{		
		ADKDataText data = null;
		
		//if have value attribute, then use the value attribute 
		String value = this.getAtomAttributeValue(HAPSimpleXmlEntity.ATTRIBUTE_VALUE);
		if(value != null)
		{
			data = new ADKDataText();
			data.setText(value);
			return data;
		}
		
		if(ele != null)
		{
			data = new ADKDataText();
			data.setText(ele.text());
		}
		else
		{
			data = new ADKDataText();
		}
		
		return data;
	}
*/
}
