package com.adiak.test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.adiak.ADKApplicationContext;
import com.adiak.ADKEntityDefinitionManager;
import com.adiak.ADKUserContext;
import com.adiak.datasource.ADKDataSource;
import com.adiak.datasource.ADKDataSourceManager;
import com.adiak.datasource.module.webpage.entity.ADKDataSourceWWWProvider;
import com.nosliw.data.HAPData;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.definition.HAPAttributeDefinitionLoader;
import com.nosliw.definition.HAPEntityDefinitionLoader;
import com.nosliw.xmlimp.definition.HAPEntityAttributeDefinitionLoaderXML;
import com.nosliw.xmlimp.definition.HAPEntityDefinitionLoaderXML;

public class TestCase {

	static ADKUserContext m_userContext = null;
	static ADKApplicationContext m_appContext = null;
	
	static String[] dataFiles = {"dataresources.xml"};
	
	public static void main(String[] args) {
		
		m_appContext = new ADKApplicationContext();
		m_userContext = new ADKUserContext(m_appContext, "default");

		HAPDataTypeManager dataTypeMan = new HAPDataTypeManager(m_appContext);
		
		ADKEntityDefinitionManager entityDefMan = new ADKEntityDefinitionManager(m_appContext);

//		HAPEntityAttributeDefinitionLoader attrLoader = initAttributeDefinitionLoader(new String[]{
//				"attributes.xml",
//		});
//		entityDefMan.addEntityAttributeDefinitionLoader(attrLoader);
		
		
//		HAPEntityDefinitionLoader defLoader = initEntityDefinitionLoader(new String[]{
//				"common/parm.xml", 
//				"common/rule.xml", 
//																					"datadefinition.xml", 
//																					"face.xml", 
//																					"layout.xml", 
//																					"template.xml", 
//																					"uicommon.xml", 
//																					"datasource_www"
//																					});
//		entityDefMan.addEntityDefinitionLoader(defLoader);
//		System.out.println(m_sysContext.getEntityDefinitionManager().toString());
		
//		ADKValueFactory valueFactory = new ADKValueFactory(m_sysContext);
//		m_sysContext.setValueFactory(valueFactory);
//
//
//		ADKXMLEntityLoader loader = initEntityLoader();
//		ADKEntityManager entityManager = new ADKEntityManager(m_sysContext, loader);
//		m_sysContext.setEntityManager(entityManager);
//		
//		
//		ADKDataSourceManager dataSourceMan = new ADKDataSourceManager(m_sysContext);
//		ADKDataSourceWWWProvider dataSourceProvider = new ADKDataSourceWWWProvider(m_sysContext);
//		dataSourceMan.registerDataSourceProvider(dataSourceProvider);
//		
//		m_sysContext.setDataSourceManager(dataSourceMan);
//
//		ADKDataSource dataSource = dataSourceMan.getDataSource("www", "aboutus");
//		HAPData data = dataSource.process();
		
	}
/*	
	protected static ADKXMLEntityLoader initEntityLoader()
	{
		ADKXMLEntityLoader loader = new ADKXMLEntityLoader(m_sysContext);
		
		for(String file : dataFiles)
		{
			InputStream stream = ClassLoader.class.getResourceAsStream("/data/"+file);
			loader.readInputStream(stream);
		}
		
		return loader;
	}

	protected static HAPEntityAttributeDefinitionLoader initAttributeDefinitionLoader(String[] defFiles)
	{
		List<InputStream> streams = new ArrayList<InputStream>();
		for(String defFile : defFiles)	{
			InputStream defStream = ClassLoader.class.getResourceAsStream("/entities/attributes/"+defFile);
			streams.add(defStream);
		}
		HAPEntityAttributeDefinitionLoaderXML out = new HAPEntityAttributeDefinitionLoaderXML(streams.toArray(new InputStream[0]), m_sysContext);
		return out;
	}
	
	protected static HAPEntityDefinitionLoader initEntityDefinitionLoader(String[] defFiles)
	{
		List<InputStream> streams = new ArrayList<InputStream>();
		for(String defFile : defFiles){
			InputStream defStream = ClassLoader.class.getResourceAsStream("/entities/"+defFile);
			streams.add(defStream);
		}
		
		HAPEntityDefinitionLoader out = new HAPEntityDefinitionLoaderXML(streams.toArray(new InputStream[0]), m_sysContext);
		return out;
	}
*/	
}
