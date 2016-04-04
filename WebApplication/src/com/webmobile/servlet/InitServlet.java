package com.webmobile.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.webmobile.manager.ApplicationContext;
import com.webmobile.service.BootStrap;

public class InitServlet extends HttpServlet{

	   public void init() throws ServletException
	    {
			ApplicationContext appContext = new ApplicationContext();
			this.getServletContext().setAttribute("appContext", appContext);
		    new BootStrap(appContext).init();
	    }
}
