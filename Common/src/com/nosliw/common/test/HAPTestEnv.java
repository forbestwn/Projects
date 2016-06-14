package com.nosliw.common.test;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.document.HAPDocumentEntity;

/*
 * test enviroment data
 * it is created at test suite level
 * 		every test case can clone it and add variable to it in before method
 * 		child test suite will get the test env from parent test suite if it does not have its own
 */
public class HAPTestEnv {

	private Map<String, String> m_globalVars;
	
	private Object m_baseObj;
	
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

	public Map<String, String> getGlobalVariables(){	return this.m_globalVars;	}
	public Object getBaseObject(){  return this.m_baseObj; }
	public void setBaseObject(Object obj){  this.m_baseObj = obj; }
	
	public void updateDocument(HAPDocumentEntity docEntity){
		docEntity.updateDocument(this.m_globalVars, this.m_baseObj);
	}
	
	public HAPTestEnv softMerge(HAPTestEnv testEnv){
		HAPTestEnv out = testEnv.clone();
		out.m_globalVars.putAll(this.m_globalVars);
		return out;
	}
	
	public HAPTestEnv clone(){
		HAPTestEnv out = new HAPTestEnv();
		out.m_globalVars.putAll(this.m_globalVars);
		return out;
	}
}
