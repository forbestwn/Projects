package com.nosliw.common.configure;

import java.util.Set;

/*
 * store configure items 
 * value for configure item can be retrieved as string, boolean, integer, float, array and HAPConfigurable itself 
 */
public interface HAPConfigurable {

	public Set<String> getConfigureItems();

	public Object getValue(String attr);
	
	public String getStringValue(String attr);
	public Boolean getBooleanValue(String attr);
	public Integer getIntegerValue(String attr);
	public Float getFloatValue(String attr);
	public String[] getArrayValue(String attr); 
	
	public HAPConfigurable getConfigurableValue(String attr);
	
	public HAPConfigurable mergeWith(HAPConfigurable configuration);
}
