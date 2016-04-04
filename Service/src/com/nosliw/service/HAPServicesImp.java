package com.nosliw.service;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.application.HAPApplicationContext;
import com.nosliw.application.HAPApplicationManager;
import com.nosliw.application.HAPUserContextInfo;
import com.nosliw.application.entity.query.HAPQueryInfo;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.data.HAPDataType;
import com.nosliw.data.HAPWraper;
import com.nosliw.data.info.HAPDataTypeInfo;
import com.nosliw.entity.data.HAPDataWraper;
import com.nosliw.entity.data.HAPEntityID;
import com.nosliw.entity.data.HAPEntityWraper;
import com.nosliw.entity.definition.HAPAttributeDefinition;
import com.nosliw.entity.definition.HAPEntityDefinitionCritical;
import com.nosliw.entity.operation.HAPEntityOperationInfo;
import com.nosliw.entity.options.HAPOptionsDefinition;
import com.nosliw.entity.options.HAPContainerOptionsData;
import com.nosliw.entity.options.HAPContainerOptionsWraper;
import com.nosliw.entity.query.HAPQueryComponent;

public class HAPServicesImp extends HAPApplicationManager implements HAPServices{

	public HAPServicesImp(HAPApplicationContext applicationContext) {
		super(applicationContext);
		this.getApplicationContext().setWebServices(this);
	}

	@Override
	public HAPServiceData getDataType(HAPDataTypeInfo info){
		HAPDataType data = this.getDataTypeManager().getDataType(info);
		return HAPServiceData.createSuccessData(data);
	}
	
	@Override
	public HAPServiceData getAllDataTypes(){
		HAPDataType[] data =  this.getDataTypeManager().getAllDataTypes();
		Map<String, HAPDataType> map = new LinkedHashMap<String, HAPDataType>();
		for(HAPDataType d : data){
			map.put(d.getDataTypeInfo().toString(), d);
		}
		return HAPServiceData.createSuccessData(map);
	}
	
	@Override
	public HAPServiceData getAllEntityDefinitions() {
		HAPEntityDefinitionCritical[] data = this.getEntityDefinitionManager().getAllEntityDefinitions();
		Map<String, HAPEntityDefinitionCritical> map = new LinkedHashMap<String, HAPEntityDefinitionCritical>();
		for(HAPEntityDefinitionCritical d : data){
			map.put(d.getEntityName(), d);
		}
		return HAPServiceData.createSuccessData(map);
	}

	@Override
	public HAPServiceData getEntityDefinitionsByGroup(String group) {
		HAPEntityDefinitionCritical[] data = this.getEntityDefinitionManager().getEntityDefinitionsByGroup(group);
		Map<String, HAPEntityDefinitionCritical> map = new LinkedHashMap<String, HAPEntityDefinitionCritical>();
		for(HAPEntityDefinitionCritical d : data){
			map.put(d.getEntityName(), d);
		}
		return HAPServiceData.createSuccessData(map);
	}

	@Override
	public HAPServiceData getEntityDefinitionByName(String name) {
		HAPEntityDefinitionCritical data = this.getEntityDefinitionManager().getEntityDefinition(name);
		return HAPServiceData.createSuccessData(data);
	}

	@Override
	public HAPServiceData startTransaction(String transactionId, HAPUserContextInfo userContextInfo) {
		return this.getApplicationContext().getUserContext(userContextInfo).startTransactionRequest(transactionId);
	}

	@Override
	public HAPServiceData commit(String transactionId,	HAPUserContextInfo userContextInfo) {
		return this.getApplicationContext().getUserContext(userContextInfo).commitRequest(transactionId);
	}

	@Override
	public HAPServiceData rollBack(String transactionId, HAPUserContextInfo userContextInfo) {
		return this.getApplicationContext().getUserContext(userContextInfo).rollBackRequest(transactionId);
	}

	@Override
	public HAPServiceData query(HAPQueryInfo query, String transactionId, HAPUserContextInfo userContextInfo) {
		HAPQueryComponent data = this.getApplicationContext().getUserContext(userContextInfo).queryRequest(query, transactionId);
		return HAPServiceData.createSuccessData(data);
	}

	@Override
	public HAPServiceData remvoeQuery(String queryName, String transactionId, HAPUserContextInfo userContextInfo) {
		this.getApplicationContext().getUserContext(userContextInfo).remvoeQueryRequest(queryName, transactionId);
		return HAPServiceData.createSuccessData();
	}
	
	@Override
	public HAPServiceData getEntityWrapers(HAPEntityID[] IDs, String transactionId, HAPUserContextInfo userContextInfo) {
		HAPEntityWraper[] wrapers = this.getApplicationContext().getUserContext(userContextInfo).getEntityWrapersRequest(IDs, transactionId);
		Map<String, HAPEntityWraper> data = new LinkedHashMap<String, HAPEntityWraper>();
		for(HAPEntityWraper wraper : wrapers){
			data.put(wraper.getID().toString(), wraper);
		}
		return HAPServiceData.createSuccessData(data);
	}

	@Override
	public HAPServiceData operate(HAPEntityOperationInfo[] operations, String transactionId, HAPUserContextInfo userContextInfo) {
		return this.getApplicationContext().getUserContext(userContextInfo).operateRequest(operations, transactionId);
	}

	@Override
	public HAPServiceData operate(HAPEntityOperationInfo operation, String transactionId, HAPUserContextInfo userContextInfo){
		HAPServiceData out = this.getApplicationContext().getUserContext(userContextInfo).operateRequest(operation, transactionId);
		return out;
	}

	@Override
	public HAPServiceData operateEntity(HAPEntityOperationInfo[] operations, String transactionId, HAPUserContextInfo userContextInfo){
		HAPServiceData out = this.getApplicationContext().getUserContext(userContextInfo).operateEntityRequest(operations, transactionId);
		return out;
	}

	@Override
	public HAPServiceData getUIResource(String name) {
		HAPUIResource data = this.getApplicationContext().getUIResourceManager().getUIResource(name);
		return HAPServiceData.createSuccessData(data);
	}

	@Override
	public HAPServiceData getAllUIResources() {
		HAPUIResource[] data = this.getApplicationContext().getUIResourceManager().getAllUIResource();
		Map<String, HAPUIResource> map = new LinkedHashMap<String, HAPUIResource>();
		for(HAPUIResource d : data){
			map.put(d.getId(), d);
		}
		return HAPServiceData.createSuccessData(HAPJsonUtility.getMapObjectJson(map));
	}

	@Override
	public HAPServiceData getAttributeOptionsData(HAPEntityID ID, String attrPath, String transactionId, HAPUserContextInfo userContextInfo){
		HAPEntityWraper[] wrapers = this.getApplicationContext().getUserContext(userContextInfo).getEntityWrapersRequest(new HAPEntityID[]{ID}, transactionId);
		if(wrapers==null || wrapers.length<1)  return null;
		HAPEntityWraper wraper = wrapers[0];
		
		HAPDataWraper childWraper = wraper.getChildWraperByPath(attrPath);
		HAPAttributeDefinition attrDef = childWraper.getAttributeDefinition();
		if(attrDef==null)  return null;
		
		HAPOptionsDefinition optionsDef = attrDef.getOptionsDefinition();
		Map<String, HAPDataWraper> wraperParms = new LinkedHashMap<String, HAPDataWraper>();
		wraperParms.put("this", wraper);
		HAPWraper[] optionsWrapers = optionsDef.getOptions(null, wraperParms);
		
		HAPContainerOptionsData out = this.getApplicationContext().getOptionsManager().createOptionsContainerData();
		out.setEleDataTypeInfo(optionsDef.getDataTypeInfo());
		for(HAPWraper optionsWraper : optionsWrapers){
			out.addOptionsData(optionsWraper);
		}
		
		HAPContainerOptionsWraper optionsContainerWraper = new HAPContainerOptionsWraper(out, this.getApplicationContext().getDataTypeManager());
		return HAPServiceData.createSuccessData(optionsContainerWraper);
	}
}
