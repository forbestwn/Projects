package com.nosliw.common.configure;

import com.nosliw.common.serialization.HAPStringable;

public interface HAPConfigureValue{

	public String getStringValue();

	public Boolean getBooleanValue();

	public Integer getIntegerValue(); 

	public Float getFloatValue();

	public String[] getArrayValue();
	
	public HAPConfigureValue clone();
}
