package com.nosliw.common.configure;

public class HAPConfigurablePropertyFile implements HAPConfigurable{

	private HAPConfigureImp m_configure;
	
	public HAPConfigurablePropertyFile(String file, String base){
		this.m_configure = HAPConfigureManager.getInstance().createConfigureFromFileWithBaseName(file, this.getClass(), base);
	}
	
	@Override
	public HAPConfigureValue getConfigureValue(String attr) {
		return this.m_configure.getConfigureValue(attr);
	}

	@Override
	public HAPConfiguration getConfiguration() {
		return this.m_configure;
	}

}
