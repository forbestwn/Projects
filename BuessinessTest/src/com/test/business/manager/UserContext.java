package com.test.business.manager;

import javax.servlet.ServletContext;

import com.nosliw.HAPUserContext;

public class UserContext extends HAPUserContext{

	private ServletContext m_servletContext;
	
	public UserContext(ApplicationContext appContext) {
		super(appContext, "default");
	}

	public ServletContext getServletContext(){return this.m_servletContext;}
	public void setServletContext(ServletContext context){this.m_servletContext = context;}
	
}
