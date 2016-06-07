package com.nosliw.common.test;

/*
 * test item is child of test case, 
 */
public class HAPTestItem {

	private Boolean m_isSuccess = true;
	private HAPTestItemDescription m_description;
	
	public HAPTestItem(HAPTestItemDescription description, boolean isSuccess){
		this.m_description = description;
		this.m_isSuccess = isSuccess;
	}

	public HAPTestItem(HAPTestItemDescription description){
		this.m_description = description;
		this.m_isSuccess = null;
	}
		
	public HAPTestItemDescription getDescription(){ return this.m_description; }
	public Boolean isSuccess(){ return this.m_isSuccess; }
	
}
