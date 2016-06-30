package com.nosliw.common.configure;

/*
 * configurable implementation 
 * configure from property file as default configure
 * costomerConfigure is customer configure that override default configure
 */
public abstract class HAPConfigurableImp implements HAPConfigurable{

	private HAPConfigureImp m_configure;

	protected HAPConfigurableImp(String propertyFile, HAPConfigureImp customerConfigure){
		this(propertyFile);
		if(customerConfigure==null)  customerConfigure = HAPConfigureManager.getInstance().createConfigure();
		this.m_configure = this.m_configure.merge(customerConfigure, false, true);
		this.resolveConfigure();
	}

	protected HAPConfigurableImp(String propertyFile){
		this.m_configure = HAPConfigureManager.getInstance().newConfigure();
		this.m_configure.importFromProperty(propertyFile, this.getClass());
	}
	
	@Override
	public HAPConfigureValue getConfigureValue(String attr) {
		return this.m_configure.getConfigureValue(attr);
	}

	@Override
	public HAPConfigureImp getConfiguration() {
		return this.m_configure;
	}

	/*
	 * use configure to override current configure
	 */
	protected void applyConfiguration(HAPConfigureImp configure){
		this.m_configure.merge(configure, false, true);
	}

	protected void resolveConfigure(){
		this.m_configure.resolve();
	}
	
}
