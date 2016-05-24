package com.nosliw.common.test;

public class HAPTestableImp implements HAPTestable{

	@Override
	public HAPTestSuiteResult test(HAPTestSuiteResult parentResult){
		HAPTestSuiteInfo testSuiteInfo = HAPTestUtility.processTestSuiteClass(this.getClass());
		if(testSuiteInfo==null)  return null;
		return testSuiteInfo.run(parentResult);
	}

}
