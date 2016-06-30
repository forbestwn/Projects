package com.nosliw.common.configure;

import com.nosliw.common.serialization.HAPStringable;

abstract class HAPConfigureItem implements HAPStringable{

	public static final String CONFIGURE = "CONFIGURE";
	public static final String VALUE = "VALUE";
	public static final String VARIABLE = "VARIABLE";
	
	//the parent configure
	private HAPConfigureImp m_parent = null;
	
	protected HAPConfigureImp getParent(){return this.m_parent;}
	protected void setParent(HAPConfigureImp parent){  this.m_parent = parent; }
	
	protected HAPConfigureImp getRootParent(){
		HAPConfigureImp out = this.getParent();
		while(out!=null){
			out = out.getParent();
		}
		return out;
	}
	
	abstract String getType();
	
}
