package com.nosliw.common.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HAPTestUtility {

	public static List<HAPTestInfo> sortTestInfo(List<HAPTestInfo> testInfos){
		Collections.sort(testInfos, new Comparator<HAPTestInfo>() {
	        public int compare(HAPTestInfo p1, HAPTestInfo p2) {
	        	return HAPTestUtility.compare(p1, p2);
	        }
		});
		return testInfos;
	}

	
	/*
	 * compare logic used when sorting test info
	 */
    public static int compare(HAPTestInfo p1, HAPTestInfo p2) {
        int out =  Integer.valueOf(p1.getSequence()).compareTo(p2.getSequence());
        if(out==0){
        	out = p1.getName().compareTo(p2.getName());
        }
        return out;
     }

	public static HAPTestSuiteInfo processTestSuiteClass(String testSuiteClassName){
		try {
			Class testSuiteClass = Class.forName(testSuiteClassName);
			return processTestSuiteClass(testSuiteClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static HAPTestSuiteInfo processTestSuiteClass(Class testSuiteClass){
		HAPTestSuiteInfo out = null;
		//if testSuiteClass has HAPTestSuite annotation, create a solid test suite
		//otherwise, not a sold one, just a container of test case
		if(testSuiteClass.isAnnotationPresent(HAPTestSuite.class)){
			HAPTestSuite testSuite = (HAPTestSuite)testSuiteClass.getAnnotation(HAPTestSuite.class);
			out = new HAPTestSuiteInfo(new HAPTestDescription(testSuite.name(), testSuite.description()));
		}
		else{
			out = new HAPTestSuiteInfo();
		}

		//find all test cases within this test suite class
		Set<HAPTestCaseInfo> testCases = HAPTestUtility.createTestCases(testSuiteClass);
		for(HAPTestCaseInfo testCase : testCases){
			out.addTest(testCase);
		}
		
		//if there is not a sold test suite and has no test cases, then return null
		if(out.isSolid() || testCases.size()>0)  return out;
		else return null;
	}
	
	/*
	 * process all test cases information defined within a test case class
	 */
	public static Set<HAPTestCaseInfo> createTestCases(Class testCaseClass){
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
		return testCasesInfo;
	}
}
