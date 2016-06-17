package com.nosliw.common.configure;

/*
 * store configure items 
 * value for configure item can be retrieved as string, boolean, integer, float, array and HAPConfigurable itself 
 */
public interface HAPConfiguration {

	public HAPConfigureValue getConfigureValue(String attr);  
	public HAPConfiguration cloneChildConfigure(String name);
	
	public HAPConfiguration softMerge(HAPConfiguration configuration, boolean ifNewConf);
	public HAPConfiguration hardMerge(HAPConfiguration configuration, boolean ifNewConf);

	public HAPConfiguration clone();
}
