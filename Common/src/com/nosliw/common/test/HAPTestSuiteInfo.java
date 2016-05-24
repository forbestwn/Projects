package com.nosliw.common.test;

public class HAPTestSuiteInfo {

	//all the test cases information
	private HAPTestCaseGroup m_testCases;
	
	private String m_name;
	private String m_description;

	public HAPTestSuiteInfo(String name, String description){
		this.m_name = name;
		this.m_description = description;
		this.m_testCases = new HAPTestCaseGroup();
	}

	public HAPTestSuiteInfo(String name, String description, HAPTestCaseGroup testCases){
		this(name, description);
		this.addTestCases(testCases);
	}
	
	public void addTestCases(HAPTestCaseGroup testCases){
		this.m_testCases = this.m_testCases.mergeSoft(testCases);
	}
	
	public String getName(){  return this.m_name;  }
	public String getDescription(){  return this.m_description; }
	
	public HAPTestSuiteResult run(HAPTestSuiteResult parentResult){
		HAPTestSuiteResult out = new HAPTestSuiteResult(this.m_name);
	    out = this.m_testCases.run(out);
		if(parentResult!=null){
			parentResult.addTestResult(out);
			out = parentResult;
		}
	    return out;
	}
	
}
