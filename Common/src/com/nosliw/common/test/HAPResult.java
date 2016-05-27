package com.nosliw.common.test;

public abstract class HAPResult {

	private boolean m_result = true;
	private HAPTestDescription m_testDescription;
	
	public HAPResult(HAPTestDescription testDesc){
		this.m_testDescription = testDesc;
	}
	
	public abstract String getType();
	
	public HAPTestDescription getTestDescription(){
		return this.m_testDescription;
	}
	
	public String getName(){  return this.getTestDescription().getName(); }

	public boolean isSuccess(){	return this.m_result;	}
	protected void setFail(){  this.m_result = false; }
	
}
