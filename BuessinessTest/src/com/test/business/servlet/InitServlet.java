package com.test.business.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.test.business.manager.ApplicationContext;
import com.test.business.service.BootStrap;

public class InitServlet extends HttpServlet{

	   public void init() throws ServletException
	   {
			ApplicationContext appContext = new ApplicationContext();
			this.getServletContext().setAttribute("appContext", appContext);
		    try {
				new BootStrap(appContext).init();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
}
