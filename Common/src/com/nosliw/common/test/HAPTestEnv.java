package com.nosliw.common.test;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 * test enviroment data
 * it is created at test suite level
 * 		every test case can clone it and add variable to it in before method
 * 		child test suite will get the test env from parent test suite if it does not have its own
 */
public class HAPTestEnv {

	Map<String, String> m_globalVars;
	
	public HAPTestEnv(Map<String, String> globalVars){
		if(globalVars!=null){
			this.m_globalVars = globalVars;
		}
		else{
			this.m_globalVars = new LinkedHashMap<String, String>();
		}
	}

	public HAPTestEnv(){
		this(null);
	}

	public Map<String, String> getGlobalVariables(){
		return this.m_globalVars;
	}
	
	
	public HAPTestEnv softMerge(HAPTestEnv testEnv){
		return testEnv;
	}
}
