package com.adiak;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.nosliw.definition.HAPEntityDefinitionManager;
import com.nosliw.xmlimp.definition.HAPEntityDefinitionLoaderXML;

public class ADKEntityDefinitionManager extends HAPEntityDefinitionManager{
	public final static String ADIAK_LOADER_ATTRIBUTE = "ADIAK_LOADER_ATTRIBUTE";
	public final static String ADIAK_LOADER_ENTITY = "ADIAK_LOADER_ENTITY";

	public ADKEntityDefinitionManager(ADKApplicationContext appContext) {
		super(appContext);
	}

	@Override
	public void doLoadEntityData(){
		super.doLoadEntityData();
		
		String[] entityFiles = {
				"test.xml",
				"ui.xml",
				"controller.xml",
				"datasource_test.xml",
				"datasource_transparent.xml"
				}; 

		List<InputStream> entityStreams = new ArrayList<InputStream>();
		for(String entityFile : entityFiles){
			InputStream defStream = ADKEntityDefinitionManager.class.getResourceAsStream("entities/"+entityFile);
			entityStreams.add(defStream);
		}
		this.addEntityDefinitionLoader(new HAPEntityDefinitionLoaderXML(ADIAK_LOADER_ENTITY, entityStreams.toArray(new InputStream[0]), this.getApplicationContext()));
	}
	
	@Override
	public String toString(){
		return super.toString();
	}
}
