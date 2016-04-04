package com.webmobile.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.entity.HAPEntityWraper;
import com.webmobile.manager.UserContext;

public class SaveEntityServlet  extends BasicServlet{

	public void doPost (HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		String entityId = request.getParameter("id");
		String entityWidget = request.getParameter("data");

		System.out.println("********************** Saving data *********************");
		System.out.println(entityWidget);
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.println("");		

//	    HAPEntityWraper entityWraper = this.getUserContext("default").getEntityManager().parseString(entityWidget, HAPDataTypeManager.FORMAT_JSON_WRAPER);
//	    this.getUserContext("default").getEntityManager().updateEntity((HAPEntityWraper)entityWraper);
//	    this.getUserContext("default").getEntityManager().persistEntity(entityWraper.getID());
	}
	
	
}
