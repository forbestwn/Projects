package com.nosliw.common.test;

public class HAPTestManager {
	private static HAPTestManager m_instance;

	//id seed
	private int m_id;
	
	private HAPTestManager(){
		this.m_id = 0;
	}
	
	public static HAPTestManager getInstance(){
		if(m_instance==null)  m_instance = new HAPTestManager();
		return m_instance;
	}
	
	public int createId(){
		return this.m_id++;
	}
}
