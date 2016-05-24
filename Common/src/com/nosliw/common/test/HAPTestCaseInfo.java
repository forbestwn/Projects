package com.nosliw.common.test;

import com.nosliw.common.utils.HAPBasicUtility;

public class HAPTestCaseInfo {
	//information for executing the test case: class, before method, test case method, after method
	private HAPTestCaseRuntime m_testCaseRuntime;
	
	private String m_name;
	private String m_description;
	private int m_sequence;
	
	public HAPTestCaseInfo(HAPTestCase testCase, HAPTestCaseRuntime testCaseRuntime){
		this.m_testCaseRuntime = testCaseRuntime;
		this.m_name = testCase.name();
		this.m_description = testCase.description();
		this.m_sequence = testCase.sequence();
		
		if(HAPBasicUtility.isStringEmpty(this.m_name)){
			//for empty name, use method name instead
			this.m_name = this.m_testCaseRuntime.getMethodName();
		}
	}

	public String getName(){ return this.m_name; }
	public String getDescription(){  return this.m_description;  }
	public int getSequence(){  return this.m_sequence;  }
	
	public HAPTestCaseRuntime getTestCaseRuntime(){
		return this.m_testCaseRuntime;
	}
	
	public HAPTestCaseResult run(HAPTestCaseResult result){
		return this.m_testCaseRuntime.run(result);
	}
	
}
