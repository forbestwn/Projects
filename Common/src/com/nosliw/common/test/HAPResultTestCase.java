package com.nosliw.common.test;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.utils.HAPConstant;

public class HAPResultTestCase extends HAPResult{

	private List<Exception> m_e;
	private List<HAPTestItem> m_testItems;
	
	public HAPResultTestCase(HAPTestDescription testDesc) {
		super(testDesc);
		this.m_testItems = new ArrayList<HAPTestItem>();
		this.m_e = new ArrayList<Exception>();
	}

	public void addException(Exception e){
		this.m_e.add(e);
		this.setFail();	
	}
	
	public List<Exception> getExceptions(){  return m_e; }
	
	public void addIsScuss(boolean b){
		if(!b)  this.setFail();  
	}
	
	public void importResult(HAPResultTestCase result){
		if(!result.isSuccess())  this.setFail();  
		this.m_e.addAll(result.m_e);
		this.m_testItems.addAll(result.m_testItems);
	}

	public void addTestItem(HAPTestItem testItem){
		this.m_testItems.add(testItem);
		Boolean isSuccess = testItem.isSuccess();
		if(isSuccess!=null){
			this.addIsScuss(isSuccess);
		}
	}
	
	public List<HAPTestItem> getTestItems(){ return this.m_testItems; }
	
	@Override
	public String getType() {
		return HAPConstant.CONS_TESTRESULT_TYPE_CASE;
	}
	
}
