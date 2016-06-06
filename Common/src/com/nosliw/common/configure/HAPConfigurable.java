package com.nosliw.common.configure;

/*
 * store configure items 
 * value for configure item can be retrieved as string, boolean, integer, float, array and HAPConfigurable itself 
 */
public interface HAPConfigurable {

	public HAPConfigureValue getConfigureValue(String attr);  
	
	public HAPConfigurable softMerge(HAPConfigurable configuration, boolean ifNewConf);
	public HAPConfigurable hardMerge(HAPConfigurable configuration, boolean ifNewConf);

	public HAPConfigurable clone();
}
