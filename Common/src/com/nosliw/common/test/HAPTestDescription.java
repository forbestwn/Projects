package com.nosliw.common.test;

public class HAPTestDescription {

	private String m_name;
	private String m_description;

	public HAPTestDescription(String name, String description){
		this.m_name = name;
		this.m_description = description;
	}

	public HAPTestDescription(HAPTestSuite testSuite){
		this(testSuite.name(), testSuite.description());
	}
	
	public HAPTestDescription(HAPTestCase testCase){
		this(testCase.name(), testCase.description());
	}
	
	public String getName() {	return m_name;	}
	public void setName(String name) {  this.m_name = name;	}

	public String getDescription() {	return m_description;	}
	public void setDescription(String description) {	this.m_description = description;	}
}
