package com.nosliw.common.test;

import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.utils.HAPConstant;

public class HAPTestSuiteInfo extends HAPTestInfo{

	//all the test cases information
	private List<HAPTestInfo> m_testCasesInfos;

	//all child test suites
	private List<HAPTestInfo> m_testSuitesInfos;
	
	public HAPTestSuiteInfo(HAPTestDescription description, HAPTestEnv testEnv){
		this(description);
		this.setTestEnv(testEnv);
	}

	public HAPTestSuiteInfo(HAPTestDescription description){
		super(description, -1);
		this.m_testCasesInfos = new ArrayList<HAPTestInfo>();
		this.m_testSuitesInfos = new ArrayList<HAPTestInfo>();
	}

	@Override
	public String getType(){ return HAPConstant.CONS_TEST_TYPE_SUITE; }

	@Override
	public HAPResult run(HAPResultTestSuite parentResult){
		HAPResultTestSuite result = new HAPResultTestSuite(this.getDescription());
		
		HAPTestUtility.sortTestInfo(this.m_testSuitesInfos);
		for(HAPTestInfo testSuiteInfo : this.m_testSuitesInfos){
			testSuiteInfo.run(result);
		}
		
		HAPTestUtility.sortTestInfo(this.m_testCasesInfos);
		for(HAPTestInfo testCaseInfo : this.m_testCasesInfos){
			testCaseInfo.run(result);
		}

		return this.addToParentResult(parentResult, result);
	}

	
	public void mergeSoft(HAPTestSuiteInfo testSuite){
		for(HAPTestInfo testInfo : testSuite.m_testCasesInfos){
			this.addTest(testInfo);
		}
		
		for(HAPTestInfo testInfo : testSuite.m_testSuitesInfos){
			this.addTest(testInfo);
		}
	}
	
	/*
	 * add test to test suite
	 * if test is test suite, then do merge if same test suite exits
	 */
	public void addTest(HAPTestInfo test){
		this.updateChildTestCase(test);
		String testType = test.getType();
		switch(testType){
		case HAPConstant.CONS_TEST_TYPE_CASE:
			this.m_testCasesInfos.add(test);
			break;
		case HAPConstant.CONS_TEST_TYPE_SUITE:
			int index = this.m_testSuitesInfos.indexOf(test);
			if(index==-1)		this.m_testSuitesInfos.add(test);
			else{
				HAPTestSuiteInfo s1 = (HAPTestSuiteInfo)this.m_testSuitesInfos.get(index);
				s1.mergeSoft((HAPTestSuiteInfo)test);
			}
			break;
		}
	}	
	
	private void updateChildTestCase(HAPTestInfo childTestInfo){
		childTestInfo.setParentTestInfo(this);
	}
}
