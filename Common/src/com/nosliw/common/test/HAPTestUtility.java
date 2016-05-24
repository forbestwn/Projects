package com.nosliw.common.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HAPTestUtility {

	public static HAPTestSuiteInfo processTestSuiteClass(Class testSuiteClass){
		HAPTestSuiteInfo out = null;
		if(testSuiteClass.isAnnotationPresent(HAPTestSuite.class)){
			HAPTestSuite testSuite = (HAPTestSuite)testSuiteClass.getAnnotation(HAPTestSuite.class);
			HAPTestCaseGroup testCases = HAPTestUtility.createTestCases(testSuiteClass);
			out = new HAPTestSuiteInfo(testSuite.name(), testSuite.description(), testCases);
		}
		
		return out;
	}
	
	/*
	 * process all test cases information defined within a test case class
	 */
	public static HAPTestCaseGroup createTestCases(Class testCaseClass){
		Set<HAPTestCaseInfo> testCasesInfo = new HashSet<HAPTestCaseInfo>();
		
		List<Method> beforeMethods = new ArrayList<Method>();
		List<Method> afterMethods = new ArrayList<Method>();
		
		Method[] methods = testCaseClass.getMethods();
		//get all pre and post method
		for(Method method : methods){
			if(method.isAnnotationPresent(HAPTestCaseBefore.class)){
				beforeMethods.add(method);
			}
			else if(method.isAnnotationPresent(HAPTestCaseAfter.class)){
				afterMethods.add(method);
			}
		}
		
		//get test case information
		for(Method method : methods){
			if(method.isAnnotationPresent(HAPTestCase.class)){
				HAPTestCase testCase = method.getAnnotation(HAPTestCase.class);
				HAPTestCaseRuntime testCaseRuntime = new HAPTestCaseRuntime(testCaseClass, method, beforeMethods, afterMethods);
				HAPTestCaseInfo testCaseInfo = new HAPTestCaseInfo(testCase, testCaseRuntime);
				testCasesInfo.add(testCaseInfo);
			}
		}
		return new HAPTestCaseGroup(testCasesInfo);
	}
}
