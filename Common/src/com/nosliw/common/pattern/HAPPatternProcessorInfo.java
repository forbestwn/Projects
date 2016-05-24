package com.nosliw.common.pattern;

public class HAPPatternProcessorInfo {
	//class name to create processor instance
	private String m_className;
	
	//processor name
	private String m_name;
	
	public HAPPatternProcessorInfo(){
	}
	
	public String getClassName(){	return this.m_className;	}
	public void setClassName(String className){  this.m_className = className; }
	
	public String getName(){	return this.m_name;	}
	public void setName(String name){  this.m_name = name; }

	public HAPPatternProcessor createPatternProcessor(){
		HAPPatternProcessor out = null;
		try {
			out = (HAPPatternProcessor)Class.forName(this.m_className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return out;
	}
	
}
