package com.webmobile.service;

import javax.servlet.ServletContext;

import com.nosliw.HAPUserContext;
import com.test.business.manager.ApplicationContext;

public class TemplateUserContext  extends HAPUserContext{

	private ServletContext m_servletContext;
	private UITemplateManager m_templateManager;
	
	public TemplateUserContext(ApplicationContext appContext) {
		super(appContext, "uitemplate");
	}

	public ServletContext getServletContext(){return this.m_servletContext;}
	public void setServletContext(ServletContext context){this.m_servletContext = context;}
	
	public UITemplateManager getUITemplateManager(){return this.m_templateManager;}
	public void setUITemplateManager(UITemplateManager man){this.m_templateManager = man;}
	
}
