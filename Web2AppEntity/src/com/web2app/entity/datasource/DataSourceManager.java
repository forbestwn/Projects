package com.web2app.entity.datasource;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.adiak.ADKUserContext;
import com.adiak.datasource.module.webpage.entity.ADKDataProcessor;
import com.nosliw.entity.HAPDataWraper;
import com.nosliw.simple.HAPSimpleEntity;
import com.web2app.data.ADKData;

public class DataSourceManager {
	
/*	
	protected static final String TAG_CONTEXT = "context";
	protected static final String TAG_DATASOURCE = "datasource";
	
	protected Map<String, DataSource> m_dataResources;
	protected Map<String, DataSourceContext> m_contexts;
	
	protected DataSourceManager()	{
		this.m_dataResources = new LinkedHashMap<String, DataSource>();
		this.m_contexts = new LinkedHashMap<String, DataSourceContext>();
	}

	private ADKSystemContext m_systemMan; 
	public void setEntitySystemManager(ADKSystemContext man)
	{
		this.m_systemMan = man;
	}
	
	public void init()
	{
		try {
			DocumentBuilderFactory DOMfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder DOMbuilder = DOMfactory.newDocumentBuilder();

			InputStream[] contextStreams = this.m_systemMan.getResourceManager().getDataSourceContextInputStreams();
			for(int i=0; i<contextStreams.length; i++)
			{
				Document contextDoc = DOMbuilder.parse(contextStreams[i]);
				this.readContext(contextDoc);
			}
			
			InputStream[] sourceStreams = this.m_systemMan.getResourceManager().getDataSourceInputStreams();
			for(int i=0; i<sourceStreams.length; i++)
			{
				Document dataResourceDoc = DOMbuilder.parse(sourceStreams[i]); 
				this.readDataResources(dataResourceDoc);
			}
		} catch (Exception e) {   
			e.printStackTrace();   
		}
	}
	
	public ADKData process(String sourceName, String dataName, ADKData data) throws Exception
	{
		DataSource dataSource = this.getDataSource(sourceName);

		//create new context
		String contextName = dataSource.getContext();
		DataSourceContext con = null; 
		if(contextName==null)  con = this.getContext();
		else{
			con = this.getContext(contextName);
			if(con == null)  con = this.getContext();  
		}
		DataSourceContext context = (DataSourceContext)con.clone();
		context.setBusinessInfo(this.m_systemMan.getOrganizationManager().getOrganization());
		context.setSourceInput(data);
		context.setResourceMan(this.m_systemMan.getResourceManager());

		//handle source
		String outString = "";
		SourceProcessor sourceProcessor = dataSource.getSourceProcessor();
		if(sourceProcessor != null)  outString = sourceProcessor.process(context);
		  
		//handle filter
		List<HAPValueWraper> filters = dataSource.getFilters();
		if(filters != null){    
			for(int i=0; i<filters.size(); i++){
				FilterProcessor filter = (FilterProcessor)filters.get(i).getComplexEntity();
				outString = filter.process(outString, context); 
			}
		}
		
		//handle data
		ADKData out = null;
		ADKDataProcessor dataProcessor = dataSource.getDataProcessor(dataName);
		if(dataProcessor != null){
			out = dataProcessor.process(outString, context);
		}
		
		if(out != null)  out.setContext(context);
		
		return out; 
	}
	
	public DataSourceContext getContext()
	{
		return this.m_contexts.get(DataSourceContext.CONTEXT_DEFAULT);
	}
	public DataSourceContext getLocalContext()
	{
		return this.m_contexts.get(DataSourceContext.CONTEXT_LOCAL);
	}
	
	public DataSourceContext getContext(String name)
	{
		return this.m_contexts.get(name);
	}
	
	public DataSource getDataSource(String sourceName)
	{
		return this.m_dataResources.get(sourceName);
	} 
	
	protected void readContext(Document doc)
	{
		HAPValueWraper localContext = HAPValueWraper.createConfigurableEntity("context", this.m_systemMan.getEntityDefinitionManager()); 
		localContext.setAtomAttributeValue(HAPSimpleEntity.ATTRIBUTE_NAME, DataSourceContext.CONTEXT_LOCAL);
		this.m_contexts.put(DataSourceContext.CONTEXT_LOCAL, (DataSourceContext)localContext.getComplexEntity());

		NodeList contexts = doc.getElementsByTagName(TAG_CONTEXT);
		for(int i=0; i<contexts.getLength(); i++)
		{
			Element node = (Element)contexts.item(i);
			HAPValueWraper context = HAPValueWraper.createConfigurableComplexEntity(node, this.m_systemMan.getEntityDefinitionManager()); 
			String name = context.getAtomAttributeValue(HAPSimpleEntity.ATTRIBUTE_NAME);
			if(name==null)  name = DataSourceContext.CONTEXT_DEFAULT;
			this.m_contexts.put(name, (DataSourceContext)context.getComplexEntity());
		}		
	} 
	
	protected void readDataResources(Document doc)
	{
		NodeList sources = doc.getElementsByTagName(TAG_DATASOURCE);
		for(int i=0; i<sources.getLength(); i++)
		{
			Element node = (Element)sources.item(i);
			HAPValueWraper entity = HAPValueWraper.createConfigurableComplexEntity(node, this.m_systemMan.getEntityDefinitionManager()); 
			String name = entity.getAtomAttributeValue(HAPSimpleEntity.ATTRIBUTE_NAME);
			DataSource source = (DataSource)entity.getComplexEntity();
			this.m_dataResources.put(name, source);
		}
	}	
*/
}
