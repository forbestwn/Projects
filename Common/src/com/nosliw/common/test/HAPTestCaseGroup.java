package com.nosliw.common.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/*
 * class for test cases
 * it may contains multiple test cases
 * all of the test cases come from one test case class
 */
public class HAPTestCaseGroup {

	List<HAPTestCaseInfo> m_testCasesInfos;

	public HAPTestCaseGroup(){
		this.m_testCasesInfos = new ArrayList<HAPTestCaseInfo>();
	}

	public HAPTestCaseGroup(Collection<HAPTestCaseInfo> testCasesInfos){
		this.m_testCasesInfos = new ArrayList<HAPTestCaseInfo>(testCasesInfos);
		this.sort();
	}
	
	public HAPTestSuiteResult run(HAPTestSuiteResult parentResult){
		for(HAPTestCaseInfo testCaseInfo : this.m_testCasesInfos){
			HAPTestCaseResult testCaseResult = new HAPTestCaseResult(testCaseInfo.getName());
			testCaseInfo.run(testCaseResult);
		}
		return parentResult;
	}
	
	public HAPTestCaseGroup mergeSoft(HAPTestCaseGroup testCasesGroup){
		HAPTestCaseGroup out = new HAPTestCaseGroup(this.m_testCasesInfos);
		out.m_testCasesInfos.addAll(testCasesGroup.m_testCasesInfos);
		out.sort();
		return out;
	}
	
	private void sort(){
		Collections.sort(this.m_testCasesInfos, new Comparator<HAPTestCaseInfo>() {
	        public int compare(HAPTestCaseInfo p1, HAPTestCaseInfo p2) {
	           return Integer.valueOf(p1.getSequence()).compareTo(p2.getSequence());
	        }
		});
	}
}
