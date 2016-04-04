package com.test.business.servlet;

import javax.servlet.http.HttpServlet;

import com.test.business.manager.ApplicationContext;
import com.test.business.manager.UserContext;

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
