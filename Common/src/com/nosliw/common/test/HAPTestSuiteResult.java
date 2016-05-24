package com.nosliw.common.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nosliw.common.utils.HAPConstant;

public class HAPTestSuiteResult extends HAPTestResult{

	private List<HAPTestResult> m_results;
	
	public HAPTestSuiteResult(String name) {
		super(name);
		this.m_results = new ArrayList<HAPTestResult>();
	}

	public void addTestResult(HAPTestResult result){
		this.m_results.add(result);
		if(!result.isSuccess()){
			this.setFail();
		}
	}
	
	public Iterator<HAPTestResult> iterator(){
		return this.m_results.iterator();
	}
	
	@Override
	public String getType() {
		return HAPConstant.CONS_TESTRESULT_TYPE_SUITE;
	}
	
}
