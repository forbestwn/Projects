package com.nosliw.common.configure;

public abstract class HAPConfigurableImp implements HAPConfigurable{

	private HAPConfigureImp m_configure;

	protected HAPConfigurableImp(String propertyFile, HAPConfigureImp baseConfigure){
		
	}
	
	@Override
	public HAPConfigureValue getConfigureValue(String attr) {
		return null;
	}

	@Override
	public HAPConfiguration getConfiguration() {
		return this.m_configure;
	}

}
