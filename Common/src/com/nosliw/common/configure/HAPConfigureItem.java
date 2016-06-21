package com.nosliw.common.configure;

abstract class HAPConfigureItem {

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
}
