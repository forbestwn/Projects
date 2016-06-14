package com.nosliw.entity.query;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.configure.HAPConfiguration;

public class HAPQueryDefinitionManager {

	Map<String, HAPQueryDefinition> m_queryDefinitions;
	
	HAPConfiguration m_configure;
	
	public HAPQueryDefinitionManager(HAPConfiguration configure){
		this.m_configure = configure;
		this.m_queryDefinitions = new LinkedHashMap<String, HAPQueryDefinition>();
	}
	
	public void addQueryDefinition(HAPQueryDefinition queryDef){
		this.m_queryDefinitions.put(queryDef.getName(), queryDef);
	}
	
	public HAPQueryDefinition getQueryDefinition(String name){
		return this.m_queryDefinitions.get(name);
	}
	
}
