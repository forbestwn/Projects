package com.adiak.datasource.module.webpage.entity.processor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.adiak.datasource.module.webpage.entity.ADKDataProcessor;
import com.adiak.datasource.module.webpage.entity.ADKUtility;
import com.nosliw.entity.HAPDataWraper;
import com.nosliw.simple.HAPSimpleEntity;
import com.nosliw.simple.HAPSimpleXmlEntity;

public class ADKTableDataProcessor extends ADKDataProcessor{

	private static final String ATTRIBUTE_BLOCKS = "blocks";
	private static final String ATTRIBUTE_ROWS = "rows";
	private static final String ATTRIBUTE_COLUMNS = "columns";
	private static final String ATTRIBUTE_SIBLEGROUPS = "siblegroups";

	private static final String ATTRIBUTE_COLUMN = "column";
	
	private static final String ATTRIBUTE_GROUPBY = "groupby";
	private static final String ATTRIBUTE_GROUPDIRECTION = "direction";
	
/*	
	public HAPValueWraper[] getColumns()
	{
		HAPValueWraper columnsEntity = this.getAttributeValue(ATTRIBUTE_COLUMNS);
		HAPValueWraper[] out = new HAPValueWraper[0];
		if(columnsEntity != null)
		{ 
			List<HAPValueWraper> filters = (List<HAPValueWraper>)columnsEntity.getListValue();
			out = filters.toArray(new HAPValueWraper[0]);
		}  
		return out;
	}

	
	@Override 
	public ADKData doProcess(Element dataEle, DataSourceContext context)
	{   
		ADKDataTable out = new ADKDataTable();   
		
		Map<String, List<ADKData>> tempDatas = new LinkedHashMap<String, List<ADKData>>();
		Map<String, ADKDataProcessor> dataProcessors = new LinkedHashMap<String, ADKDataProcessor>();
		
		HAPValueWraper[] columnsEntity = this.getColumns();
		
		int columnSize = columnsEntity.length;
		for(int i=0; i<columnSize; i++) 
		{
			ADKDataProcessor columnEntity = (ADKDataProcessor)columnsEntity[i].getComplexEntity();
			if(columnEntity != null)  
			{
				String title = columnEntity.getName(); 
				out.addTitle(title);
				
				tempDatas.put(title, new ArrayList<ADKData>());
				dataProcessors.put(title, columnEntity);
			}
		}
  
		HAPValueWraper blocksEntity = this.getAttributeValue(ATTRIBUTE_BLOCKS);
		int blockSize = 0; 
		if(blocksEntity != null)
		{
			blockSize = blocksEntity.getListValue().size();
			for(int r=0; r<blockSize; r++)
			{
				HAPValueWraper blockEntity = blocksEntity.getListValue().get(r);
				String blockPath = blockEntity.getAtomAttributeValue(HAPSimpleEntity.ATTRIBUTE_DOMPATH);
				
				Element blockEle = ADKUtility.selectElement(dataEle, blockPath); 
				if(blockEle!=null)
				{
					HAPValueWraper rowsEntity = blockEntity.getImpliedValue(ATTRIBUTE_ROWS);
					if(rowsEntity != null) 
					{ 
						List<HAPValueWraper> ls = rowsEntity.getImpliedValue(ATTRIBUTE_COLUMNS).getListValue();
						String rowPath = rowsEntity.getAtomAttributeValue(HAPSimpleXmlEntity.ATTRIBUTE_DOMPATH);
						
						Elements rowsEles = null;
						if(rowPath != null && !rowPath.equals(""))
						{
							rowsEles = blockEle.select(rowPath);
							for(int j=0; j<ls.size(); j++) 
							{
								HAPValueWraper columnEntity = ls.get(j);
								String columnPath = columnEntity.getAtomAttributeValue(HAPSimpleXmlEntity.ATTRIBUTE_DOMPATH);
								String columName = columnEntity.getAtomAttributeValue(ATTRIBUTE_COLUMN);
								List<ADKData> datas = tempDatas.get(columName);
								if(datas != null)
								{
									for(int p=0; p<rowsEles.size(); p++)
									{  
										Element rowEle = null;
										Element rowEle1 = rowsEles.get(p);
										if(columnPath==null || columnPath.equals("")){
											rowEle = rowEle1;
										}
										else
										{
											Element a1 = ADKUtility.selectElement(rowEle1, columnPath);
											if(a1 != null)  rowEle = a1;
										}
										
										if(rowEle != null)
										{
											ADKData data = new ADKData();
											String columnHtml = rowEle.outerHtml();
											ADKDataProcessor dataEntity = dataProcessors.get(columName); 
											if(dataEntity!=null){
												data = dataEntity.process(columnHtml, context);
											}
											datas.add(data);
										}
									}
								}
							}
						}
					}
					
					{
					HAPValueWraper blockColumsEntity = blockEntity.getImpliedValue(ATTRIBUTE_COLUMNS);
					if(blockColumsEntity != null)
					{
						List<HAPValueWraper> ls = blockColumsEntity.getListValue();
						for(int j=0; j<ls.size(); j++)
						{
							HAPValueWraper columnEntity = ls.get(j);
							String columName = columnEntity.getAtomAttributeValue(ATTRIBUTE_COLUMN);
							List<ADKData> datas = tempDatas.get(columName);
							if(datas != null)
							{
								String columnPath = columnEntity.getAtomAttributeValue(HAPSimpleXmlEntity.ATTRIBUTE_DOMPATH);
								Elements rowsEles = blockEle.select(columnPath);
								for(int p=0; p<rowsEles.size(); p++) 
								{
									Element rowEle = rowsEles.get(p);
									
									ADKData data = new ADKData();
									String columnHtml = rowEle.outerHtml();
									ADKDataProcessor dataEntity = dataProcessors.get(columName); 
									if(dataEntity!=null){
										data = dataEntity.process(columnHtml, context);
									}
									datas.add(data); 
								}
							}
						}
					}
					} 

					if(true) 
					{ 
						HAPValueWraper blockSiblegroupsEntity = blockEntity.getImpliedValue(ATTRIBUTE_SIBLEGROUPS);
						if(blockSiblegroupsEntity != null)
						{
							List<HAPValueWraper> ls = blockSiblegroupsEntity.getListValue();
							for(int j=0; j<ls.size(); j++)
							{
								HAPValueWraper sibleGroupEntity = ls.get(j);
								String columName = sibleGroupEntity.getAtomAttributeValue(ATTRIBUTE_COLUMN);
								ADKDataProcessor dataProcessor = dataProcessors.get(columName); 
								List<ADKData> datas = tempDatas.get(columName);
								if(datas != null && dataProcessor!=null)
								{
									String siblePath = sibleGroupEntity.getAtomAttributeValue(HAPSimpleXmlEntity.ATTRIBUTE_DOMPATH);
									String groupByPath = sibleGroupEntity.getAtomAttributeValue(ATTRIBUTE_GROUPBY);
									String groupDirection = sibleGroupEntity.getAtomAttributeValue(ATTRIBUTE_GROUPDIRECTION);
									boolean isDown = true;
									if(groupDirection!=null && groupDirection.equals("up"))  isDown = false;
									
									Elements sibleEles = blockEle.select(siblePath);
									StringBuffer html = new StringBuffer();  
									int index = 0;
									for(int p=0; p<sibleEles.size(); p++)
									{ 
										Element sibleEle = sibleEles.get(p);
										Elements groupEles = sibleEle.select(groupByPath);
										if(groupEles==null || groupEles.size()<=0)
										{ 
											html.append("\n" + sibleEle.outerHtml());
										}
										else
										{
											ADKData data = new ADKData();
											if(isDown)
											{ 
												if(index != 0)
												{
													data = dataProcessor.process(html.toString(), context);
													datas.add(data); 
					   							}
												html = new StringBuffer();
											}
											else
											{
												data = dataProcessor.process(html.toString(), context);
									 			datas.add(data); 
												html = new StringBuffer();
											} 
											index++;
										} 
									} 
									
									if(isDown)
									{ 
										ADKData data = new ADKData();
										data = dataProcessor.process(html.toString(), context);
										datas.add(data); 
									}
								}
							}
						}						
					} 
				}
			}
		}
		
		
		Iterator<String> it = tempDatas.keySet().iterator();
		int minLen = 1000000;
		while(it.hasNext())
		{
			List<ADKData> datas = tempDatas.get(it.next());
			if(datas!=null)
			{
				if(datas.size()!=0)
				{
					if(datas.size()<minLen)  minLen = datas.size();
				}
			}
		}
		
		if(minLen != 1000000)
		{
			for(int i=0; i<minLen; i++)
			{
				ADKDataTableRow rowData = new ADKDataTableRow(columnSize);
				Iterator<String> columns = tempDatas.keySet().iterator();
				while(columns.hasNext())
				{
					String title = columns.next();
					List<ADKData> columnDatas = tempDatas.get(title);
					if(i<columnDatas.size())
					{
						ADKData data = columnDatas.get(i);
						int index = out.getTitleIndex(title);
						rowData.setData(data, index);
					} 
				}
				out.addRow(rowData); 
			}   
		}
		
		return out;
	}  
*/	  
}
