package com.nosliw.entity.options;

import java.util.Map;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.HAPData;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.entity.definition.HAPAttributeDefinition;
import com.nosliw.entity.query.HAPQueryDefinition;
import com.nosliw.entity.query.HAPQueryDefinitionManager;

public abstract class HAPOptionsDefinitionQuery extends HAPOptionsDefinition{

	//query definition that this options depends on
	private String m_queryDefName;
	private HAPQueryDefinition m_queryDef;
	
	//parms used for query
	private Map<String, HAPData> m_queryParms; 
	
	private HAPQueryDefinitionManager m_queryDefMan;
	
	public HAPOptionsDefinitionQuery(String name, String queryDefName, Map<String, HAPData> queryParms, HAPAttributeDefinition attrDef, HAPQueryDefinitionManager queryDefMan, HAPDataTypeManager dataTypeMan) {
		super(name, attrDef.getDataTypeDefinitionInfo(), null, dataTypeMan);
		this.m_queryDefMan = queryDefMan;
		this.m_queryDefName = queryDefName;
	}

	public HAPQueryDefinition getQueryDefinition(){
		if(this.m_queryDef==null){
			this.m_queryDef = this.getQueryDefinitionManager().getQueryDefinition(m_queryDefName);
		}
		return this.m_queryDef;
	}
	
	public Map<String, HAPData> getQueryParms(){	return this.m_queryParms;	}
	
	public void addQueryParms(String name, HAPData parm){ this.m_queryParms.put(name, parm);}
	
	public String getQueryDefinitionName(){return this.m_queryDefName;}
	
	protected HAPQueryDefinitionManager getQueryDefinitionManager(){	return this.m_queryDefMan;	}
	
	@Override
	public String getType() {	return HAPConstant.CONS_OPTIONS_TYPE_QUERY;	}
}
