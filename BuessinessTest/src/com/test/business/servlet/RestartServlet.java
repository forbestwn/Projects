package com.test.business.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.business.manager.ApplicationContext;
import com.test.business.service.BootStrap;

public class RestartServlet  extends BasicServlet{

	public void doGet (HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		
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
