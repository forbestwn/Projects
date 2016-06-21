package com.nosliw.common.interpolate;

import java.util.HashSet;
import java.util.Set;

public class HAPInterpolateOutput {

	private String m_output;
	private Set<String> m_unsolved;
	
	public HAPInterpolateOutput(){
		this.m_unsolved = new HashSet<String>();
	}
	
	public void addUnsolved(String name){
		this.m_unsolved.add(name);
	}
	
	public String getOutput(){ return this.m_output; }
	public Set<String> getUnsolved(){  return this.m_unsolved; }
	public void setOutput(String output){ this.m_output = output; } 
	
	public boolean isResolved(){  return this.m_unsolved.size()<=0; }
}
