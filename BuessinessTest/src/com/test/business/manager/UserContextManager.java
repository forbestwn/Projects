package com.test.business.manager;

import com.nosliw.HAPApplicationManager;
import com.nosliw.HAPUserContext;
import com.nosliw.HAPUserContextManager;

public class UserContextManager extends HAPUserContextManager{

	public UserContextManager(UserContext appContext) {
		super(appContext);
	}
	
	protected UserContext getUserContext(){
		return (UserContext)super.getUserContext();
	}
}
