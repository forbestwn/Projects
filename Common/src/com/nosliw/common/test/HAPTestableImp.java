package com.nosliw.common.test;

public class HAPTestableImp implements HAPTestable{

	@Override
	public HAPResultTestSuite test(HAPResultTestSuite parentResult){
		HAPTestSuiteInfo testSuiteInfo = HAPTestUtility.processTestSuiteClass(this.getClass());
		if(testSuiteInfo==null)  return null;
		return testSuiteInfo.run(parentResult);
	}

}
