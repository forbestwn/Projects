package com.nosliw.application;

public class HAPUserContextInfo {

	private String m_id = "default";
	
	public HAPUserContextInfo(String id){
		this.m_id = id;
	}
	
	public String getId(){
		return this.m_id;
	}
	
}
