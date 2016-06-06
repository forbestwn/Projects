package com.nosliw.common.test;

import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;

public class HAPTestCaseInfo extends HAPTestInfo{
	//information for executing the test case: class, before method, test case method, after method
	private HAPTestCaseRuntime m_testCaseRuntime;
	
	public HAPTestCaseInfo(HAPTestCase testCase, HAPTestCaseRuntime testCaseRuntime){
		super(new HAPTestDescription(testCase), testCase.sequence());
		this.m_testCaseRuntime = testCaseRuntime;
		
		if(HAPBasicUtility.isStringEmpty(this.getName())){
			//for empty name, use method name instead
			this.setName(this.m_testCaseRuntime.getMethodName());
		}
	}

	@Override
	public void postInit(){
		if(this.getTestEnv()!=null){
			//update description info: update variable place holder
			this.getDescription().updateDocument(this.getTestEnv().getGlobalVariables());
		}
		this.setInited();
	}

	@Override
	public String getType(){ return HAPConstant.CONS_TEST_TYPE_CASE; }

	public HAPTestCaseRuntime getTestCaseRuntime(){
		return this.m_testCaseRuntime;
	}
	
	@Override
	public HAPResult run(HAPResultTestSuite parentResult){
		if(!this.inited())  this.postInit();
		HAPResultTestCase result = new HAPResultTestCase(this.getDescription());
		result = this.m_testCaseRuntime.run(result, this.getTestEnv());
		return this.addToParentResult(parentResult, result);
	}
}
