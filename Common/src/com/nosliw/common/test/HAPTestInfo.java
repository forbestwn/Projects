package com.nosliw.common.test;

import com.nosliw.common.utils.HAPBasicUtility;

public abstract class HAPTestInfo{
	//id used to identify test, it is unique globally
	private int m_id;
	
	//description infor for this test
	private HAPTestDescription m_description;

	//
	private int m_sequence;
	
	private boolean m_inited = false;;
	
	//parent test info
	private HAPTestInfo m_parentTestInfo;
	
	//test enviroment data for test suite
	private HAPTestEnv m_testEnv;
	
	public HAPTestInfo(HAPTestDescription description, int sequence, HAPTestEnv testEnv){
		this.m_description = description;
		this.m_sequence = sequence;
		this.m_id = HAPTestManager.getInstance().createId();
		if(testEnv==null)  this.m_testEnv = new HAPTestEnv();
		else   this.m_testEnv = testEnv;
	}

	public HAPTestInfo(HAPTestDescription description, int sequence){
		this(description, sequence, null);
	}

	abstract public String getType();
	abstract public HAPResult run(HAPResultTestSuite parentResult);
	abstract public void postInit();

	public int getId(){ return this.m_id; }
	public HAPTestDescription getDescription(){  return this.m_description; }
	public int getSequence(){  return this.m_sequence;  }
	public String getName(){ return this.getDescription().getName(); }
	protected void setName(String name){ this.getDescription().setName(name); }
	public boolean inited(){ return this.m_inited; }
	protected void setInited(){  this.m_inited = true; }
	
	protected void setDescription(HAPTestDescription desc){  this.m_description = desc; }
	protected void setSequence(int seq){ this.m_sequence = seq; }
	
	protected HAPTestEnv getTestEnv(){ return this.m_testEnv; }
	public void softMergeTestEnv(HAPTestEnv testEnv){ 
		this.m_testEnv = testEnv; 
	}

	protected HAPTestInfo getParentTestInfo(){ return this.m_parentTestInfo; }
	protected void setParentTestInfo(HAPTestInfo testInfo){  
		this.m_parentTestInfo = testInfo;
		//merge test env
		this.softMergeTestEnv(testInfo.getTestEnv());
	}
	
	protected HAPResult addToParentResult(HAPResultTestSuite parentResult, HAPResult result){
		HAPResult out = result;
		if(parentResult!=null){
			parentResult.addTestResult(result);
			out = parentResult;
		}
		return out;
	}

	@Override
	public boolean equals(Object obj){
		if(obj instanceof HAPTestInfo){
			HAPTestInfo t = (HAPTestInfo)obj;
			return HAPBasicUtility.isEquals(this.getType(), t.getType()) && HAPBasicUtility.isEquals(this.getDescription().getName(), t.getDescription().getName());
		}
		else return false;
	}
}
