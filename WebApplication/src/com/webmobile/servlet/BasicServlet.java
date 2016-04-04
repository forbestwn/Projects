package com.webmobile.servlet;

import javax.servlet.http.HttpServlet;

import com.webmobile.manager.ApplicationContext;
import com.webmobile.manager.UserContext;

public class BasicServlet extends HttpServlet{

	
	protected ApplicationContext getApplicationContext(){
		ApplicationContext appContext = (ApplicationContext)this.getServletContext().getAttribute("appContext");
		return appContext;
	}
	
	protected UserContext getUserContext(String user){
		ApplicationContext appContext = (ApplicationContext)this.getServletContext().getAttribute("appContext");
		return (UserContext)appContext.getUserContext(user);
	}
	
}
