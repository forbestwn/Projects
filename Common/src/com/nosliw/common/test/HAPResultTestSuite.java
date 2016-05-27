package com.nosliw.common.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nosliw.common.utils.HAPConstant;

public class HAPResultTestSuite extends HAPResult{
	//results for suites
	private List<HAPResult> m_testSuiteResults;
	//results for test cases 
	private List<HAPResult> m_testCaseResults;
	
	public HAPResultTestSuite(HAPTestDescription testDesc) {
		super(testDesc);
		this.m_testSuiteResults = new ArrayList<HAPResult>();
		this.m_testCaseResults = new ArrayList<HAPResult>();
	}

	public void addTestResult(HAPResult result){
		switch(result.getType()){
		case HAPConstant.CONS_TESTRESULT_TYPE_CASE:
			this.m_testCaseResults.add(result);
			break;
		case HAPConstant.CONS_TESTRESULT_TYPE_SUITE:
			this.m_testCaseResults.add(result);
			break;
		}
		if(!result.isSuccess()){
			this.setFail();
		}
	}
	
	public Iterator<HAPResult> iterator(){
		List<HAPResult> all = new ArrayList<HAPResult>();
		all.addAll(m_testSuiteResults);
		all.addAll(m_testCaseResults);
		return all.iterator();
	}
	
	@Override
	public String getType() {
		return HAPConstant.CONS_TESTRESULT_TYPE_SUITE;
	}
}
