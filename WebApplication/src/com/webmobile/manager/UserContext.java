package com.webmobile.manager;

import javax.servlet.ServletContext;

import com.adiak.ADKApplicationContext;
import com.adiak.ADKUserContext;
import com.webmobile.service.AppService;
import com.webmobile.service.FaceService;

public class UserContext extends ADKUserContext{

	private AppService m_appService;
	private FaceService m_faceService;
	private ServletContext m_servletContext;
	
	public UserContext(ApplicationContext appContext) {
		super(appContext, "default");
	}

	public AppService getAppService(){return this.m_appService;}
	public void setAppService(AppService appService){this.m_appService=appService;}
	
	public FaceService getFaceService(){return this.m_faceService;}
	public void setFaceService(FaceService faceService){this.m_faceService=faceService;}
	
	public ServletContext getServletContext(){return this.m_servletContext;}
	public void setServletContext(ServletContext context){this.m_servletContext = context;}
	
}
