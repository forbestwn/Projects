package com.nosliw.application;

public class HAPApplicationInfo extends HAPApplicationManager{

	public HAPApplicationInfo(HAPApplicationContext appContext){
		super(appContext);
		this.getApplicationContext().setApplicationInfo(this);
	}
	
	//name used to identify ApplicationContext from other ApplicationContext if exists
	public String getApplicationName(){
		return "Web2App";
	}
	
	
	
}
