package com.webmobile.manager;

import com.adiak.ADKUserContext;

public class UITemplateUserContext extends ADKUserContext{

	public UITemplateUserContext(ApplicationContext appContext) {
		super(appContext, "untemplate");
	}

	public String getId(){
		return "uitemplate";
	}
	
}
