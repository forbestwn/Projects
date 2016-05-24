package com.nosliw.common.test;

import com.nosliw.common.utils.HAPConstant;

public class HAPTestCaseResult extends HAPTestResult{

	public HAPTestCaseResult(String name) {
		super(name);
	}

	public void addException(Exception e){
		this.setFail();
	}
	
	public void addIsScuss(boolean b){
		if(!b)  this.setFail();  
	}
	
	public void addResult(HAPTestCaseResult result){
		if(!result.isSuccess())  this.setFail();  
	}

	@Override
	public String getType() {
		return HAPConstant.CONS_TESTRESULT_TYPE_CASE;
	}
	
}
