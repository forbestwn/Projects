package com.webmobile.manager;

import com.nosliw.HAPApplicationContext;
import com.nosliw.HAPUserContext;

public class TemplateUserContext extends HAPUserContext{

	public TemplateUserContext(HAPApplicationContext appContext) {
		super(appContext, "template");
	}

}
