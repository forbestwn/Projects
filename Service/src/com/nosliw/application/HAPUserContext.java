package com.nosliw.application;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPStringable;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.entity.data.HAPEntityID;
import com.nosliw.entity.data.HAPEntityWraper;
import com.nosliw.entity.dataaccess.HAPDataContext;
import com.nosliw.entity.definition.HAPEntityDefinitionManager;
import com.nosliw.entity.operation.HAPEntityOperationInfo;
import com.nosliw.entity.options.HAPOptionsDefinitionManager;
import com.nosliw.entity.query.HAPQueryComponent;
import com.nosliw.entity.transaction.HAPTransaction;
import com.nosliw.entity.utils.HAPEntityDataUtility;

public class HAPUserContext implements HAPStringable{

	private HAPApplicationContext m_appContext;
	private HAPUserContextInfo m_userContextInfo;

	private HAPDataContext m_entityManager;
	
	public HAPUserContext(HAPApplicationContext appContext, String id){
		this.m_appContext=appContext;
		this.m_userContextInfo = new HAPUserContextInfo(id);
	}

	//*********************  External (App Level)

	public HAPServiceData getExternalEntity(HAPEntityID ID){
		return this.getApplicationContext().requestGetEntity(this, ID);
	}
	
	//*********************  Transaction
	//method about transaction 
	/*
	 * return if success, return transaction object
	 *        if fail, return error
	 */
	public HAPServiceData startTransactionRequest(String transactionId){
		return this.getEntityManager().startTransactionRequest(transactionId);
	}
	
	/*
	 * return value depend on return value of commit of differen type of transaction 
	 */
	public HAPServiceData commitRequest(String transactionId){
		return this.getEntityManager().commitRequest(transactionId);
	}
	/*
	 * return value: the updated entity
	 */
	public HAPServiceData rollBackRequest(String transactionId){
		return this.getEntityManager().rollBackRequest(transactionId);
	}
	
	//*********************  Retrive Entity
	public HAPQueryComponent queryRequest(HAPQueryInfo query, String transactionId){
		if(HAPBasicUtility.isStringEmpty(query.getQueryId())){
			query.setQueryId(this.getNextQueryId());
		}
		return this.getEntityManager().queryRequest(query, transactionId);
	}
	
	public void remvoeQueryRequest(String queryName, String transactionId){
		this.getEntityManager().removeQueryRequest(queryName, transactionId);;
	}
	
	public HAPEntityWraper[] getEntityWrapersRequest(HAPEntityID[] IDs, String transactionId){
		return this.getEntityManager().getEntityWrapersRequest(IDs, transactionId);
	}

	public HAPEntityWraper getEntityWraperRequest(HAPEntityID ID, String transactionId){
		HAPEntityWraper out = this.getEntityManager().getEntityWraperRequest(ID, transactionId);
		if(out==null)   throw new NullPointerException();
		return out;
	}

	//*********************  Operation
	
	/*
	 * return : if the operation success
	 * 			if one of them fail 
	 * 				failure message
	 * 				stop do following operation
	 * 			a list of entity needed to update 
	 */
	public HAPServiceData operateRequest(HAPEntityOperationInfo operation, String transactionId){
		return this.getEntityManager().operateRequest(operation, transactionId);
	}

	public HAPServiceData operateEntityRequest(HAPEntityOperationInfo[] operations, String transactionId){
		return this.getEntityManager().operateEntityRequest(operations, transactionId);
	}

	public HAPServiceData operateRequest(HAPEntityOperationInfo[] operations, String transactionId){
		HAPDataContext entityManager = this.getEntityManager();

		List<HAPOperationAllResult> results = new ArrayList<HAPOperationAllResult>();
		boolean hasError = false;
		for(HAPEntityOperationInfo op : operations){
			HAPServiceData serviceData = entityManager.operateRequest(op, transactionId);
			if(serviceData.isSuccess()){
				HAPOperationAllResult opResult = (HAPOperationAllResult)serviceData.getData();
				results.add(opResult);
			}
			else{
				results.add((HAPOperationAllResult)serviceData.getData());
				hasError = true;
				break;
			}
		}
		
		if(hasError)  return HAPServiceData.createFailureData(results, "");
		else return HAPServiceData.createSuccessData(results);
	}
	
	public HAPServiceData operateRequest(HAPEntityOperationInfo[] operations, String[] IDs, String transactionId){
		return null;
	}

	
	
	public String getNextEntityId(){
		return HAPEntityDataUtility.getNextID();
	}

	public String getNextQueryId(){
		return HAPEntityDataUtility.getNextID();
	}


	//*********************************   Basic Method
	public String getId(){return this.getUserContextInfo().getId();	}
	public HAPApplicationContext getApplicationContext(){return this.m_appContext;}
	public HAPUserContextInfo getUserContextInfo(){return this.m_userContextInfo;}
	
	public HAPDataContext getEntityManager(){	return this.m_entityManager;}
	public void setEntityManager(HAPDataContext man){	this.m_entityManager = man;}

	
	public HAPApplicationInfo getConfigure(){	return this.getApplicationContext().getApplicationInfo();	}
	public HAPEntityDefinitionManager getEntityDefinitionManager(){	return this.getApplicationContext().getEntityDefinitionManager();	}
	public HAPDataTypeManager getDataTypeManager(){return this.getApplicationContext().getDataTypeManager();}
	public HAPDataValidationManager getDataValidationManager(){return this.getApplicationContext().getDataValidationManager();}
	public HAPOptionsDefinitionManager getOptionsManager(){return this.getApplicationContext().getOptionsManager();}
	public HAPStringTemplateManager getStringTemplateManager(){return this.getApplicationContext().getStringTemplateManager();}
	
	HAPLogger m_logger = null;
	public HAPLogger getLogger(){return this.m_logger;}
	public void setLogger(HAPLogger logger){this.m_logger=logger;}
	
	@Override
	public String toStringValue(String format) {
		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
		jsonMap.put("data", this.getEntityManager().toStringValue(format));
		return HAPJsonUtility.getMapJson(jsonMap);
	};
}
