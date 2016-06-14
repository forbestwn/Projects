package com.nosliw.common.configure;

/*
 * 
 */
public interface HAPConfigurable {
	
	public HAPConfigureValue getConfigureValue(String attr);  
	
	public HAPConfiguration getConfiguration();
	
}
