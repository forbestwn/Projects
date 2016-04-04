package com.nosliw.service;

import com.nosliw.application.HAPUserContextInfo;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.entity.data.HAPEntityID;
import com.nosliw.entity.operation.HAPEntityOperationInfo;
import com.nosliw.entity.query.HAPQueryDefinition;

public interface HAPEntityService {

	//********************************* transaction  ************************************ 
	/*
	 * return if success, return transaction object
	 *        if fail, return error
	 */
	public HAPServiceData startTransaction(String transactionId, HAPUserContextInfo userContextInfo);
	/*
	 * return value depend on return value of commit of different type of transaction 
	 */
	public HAPServiceData commit(String transactionId, HAPUserContextInfo userContextInfo);
	/*
	 * return value: the updated entity
	 */
	public HAPServiceData rollBack(String transactionId, HAPUserContextInfo userContextInfo);
	
	//********************************* Query  ************************************ 
	public HAPServiceData query(HAPQueryDefinition query, String transactionId, HAPUserContextInfo userContextInfo);
	public HAPServiceData remvoeQuery(String queryName, String transactionId, HAPUserContextInfo userContextInfo);
	public HAPServiceData getEntityWrapers(HAPEntityID[] IDs, String transactionId, HAPUserContextInfo userContextInfo);

	public HAPServiceData getAttributeOptionsData(HAPEntityID ID, String attrPath, String transactionId, HAPUserContextInfo userContextInfo);
	
	
	//********************************* Entity Operation  ************************************ 
	/*
	 * return : if the operation success
	 * 			if one of them fail 
	 * 				failure message
	 * 				stop do following operation
	 * 			a list of entity needed to update 
	 */
	
	
	/*
	 * single operation
	 */
	public HAPServiceData operate(HAPEntityOperationInfo operation, String transactionId, HAPUserContextInfo userContextInfo);
	
	/*
	 * 
	 */
	public HAPServiceData operateEntity(HAPEntityOperationInfo[] operation, String transactionId, HAPUserContextInfo userContextInfo);

	/*
	 * batch operations at the same time
	 */
	public HAPServiceData operate(HAPEntityOperationInfo[] operations, String transactionId, HAPUserContextInfo userContextInfo);
	
}
