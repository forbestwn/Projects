package com.webmobile.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webmobile.manager.UserContext;
import com.webmobile.service.BootStrap;

public class TransferServlet extends BasicServlet{

	public void doGet (HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		
		
	    response.setContentType("text/html");
	    // Actual logic goes here.
	    PrintWriter out = response.getWriter();
	    out.println("");		

	
	}

}
