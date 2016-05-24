package com.nosliw.common.test;

public abstract class HAPTestResult {

	private boolean m_result = true;
	private String m_name;
	
	public HAPTestResult(String name){
		this.m_name = name;
	}
	
	public abstract String getType();
	
	public String getName(){  return this.m_name; }

	public boolean isSuccess(){	return this.m_result;	}
	protected void setFail(){  this.m_result = false; }
	
}
