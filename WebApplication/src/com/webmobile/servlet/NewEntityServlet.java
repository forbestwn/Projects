package com.webmobile.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.entity.HAPEntityData;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.util.HAPConstant;
import com.nosliw.util.HAPUtility;
import com.nosliw.widget.HAPWidgetManager;
import com.webmobile.manager.UserContext;
import com.webmobile.service.BootStrap;

public class NewEntityServlet  extends BasicServlet{

	public void doGet (HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		String html = HAPUtility.readFile(this.getToolMainTemplate(), "\n");
		
		HAPWidgetManager widgetMan = this.getUserContext("default").getWidgetManager();
		StringBuffer widgets = new StringBuffer("\n");
		for(String wName : widgetMan.getAllWidgetNames()){
			widgets.append(widgetMan.getWidget(wName) + "\n");
		}
		html = html.replace("||widgets||", widgets);
		
		String entityWidgets = widgetMan.toStringValue("json");
		html = html.replace("||entitywidgets||", entityWidgets);


		String entityType = request.getParameter("type");

		HAPEntityData entity = this.getUserContext("default").getValueFactory().newEntity(entityType, null);
		HAPEntityWraper entityWraper = this.getUserContext("default").getValueFactory().createEntityWraper(entity);
		
		String entityJson = entityWraper.toStringValue(HAPConstant.FORMAT_JSON_WRAPER);
		html = html.replace("||entityWraper||", entityJson);

		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.println(html);		
		
	}
	
	
	private String getToolMainTemplate(){
	     String webRootPath=
			        System.getProperty("user.home",
			          File.separatorChar + "home" +
			          File.separatorChar + "zelda") +
			          File.separatorChar;
	     return webRootPath + "template/valuewraper/toolnew.html";
	}
	
}
