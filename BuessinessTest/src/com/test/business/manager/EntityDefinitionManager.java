package com.test.business.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.nosliw.entity.definition.HAPEntityDefinitionManager;
import com.nosliw.xmlimp.definition.HAPEntityDefinitionLoaderXML;
import com.test.business.util.PathUtility;

public class EntityDefinitionManager extends HAPEntityDefinitionManager{
	public final static String ADIAK_LOADER_ATTRIBUTE = "ADIAK_LOADER_ATTRIBUTE";
	public final static String ADIAK_LOADER_ENTITY = "ADIAK_LOADER_ENTITY";

	public EntityDefinitionManager(ApplicationContext appContext) {
		super(appContext);
	}

	@Override
	public void doLoadEntityData(){
		super.doLoadEntityData();
		
		String[] entityFiles = {
				"common.xml",
				"business.xml",
				"parm.xml",
				"datadefinition.xml",
				"ui.xml",
				"uievent.xml",
				"uiface.xml",
				"adkapplication.xml",
				"controller.xml",
				"datasource.xml",
				"datasource_test.xml",
				"datasource_transparent.xml",
				"datasource_rss.xml",
				"datasource_twitter.xml",
				}; 

		List<InputStream> entityStreams = new ArrayList<InputStream>();
		for(String entityFile : entityFiles){
			String entityDefPath = PathUtility.getEntityDefinitionPath() + "/" + entityFile;
			InputStream defStream;
			try {
				defStream = new FileInputStream(entityDefPath);
				entityStreams.add(defStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		this.addEntityDefinitionLoader(new HAPEntityDefinitionLoaderXML(ADIAK_LOADER_ENTITY, entityStreams.toArray(new InputStream[0]), this.getApplicationContext()));
	}
	
	@Override
	public String toString(){
		return super.toString();
	}
}
