package com.webmobile.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nosliw.data.HAPDataType;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.definition.HAPAttributeDefinition;
import com.nosliw.entity.HAPEntityData;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.entity.HAPValueUtility;
import com.nosliw.entity.HAPDataWraper;
import com.nosliw.entity.HAPDataWraperTask;
import com.nosliw.exception.HAPServiceData;
import com.nosliw.util.HAPConstant;
import com.nosliw.util.HAPUtility;
import com.nosliw.widget.HAPWidgetManager;
import com.webmobile.manager.UserContext;
import com.webmobile.service.BootStrap;
import com.webmobile.util.Utility;

public class EntityServlet extends BasicServlet{

	public void doGet (HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		String html = HAPUtility.readFile(this.getToolMainTemplate(), "\n");
		
		HAPWidgetManager widgetMan = this.getApplicationContext().getWidgetManager();
		StringBuffer widgets = new StringBuffer("\n");
		for(String wName : widgetMan.getAllWidgetNames()){
			widgets.append(widgetMan.getWidget(wName) + "\n");
		}
		html = html.replace("||widgets||", widgets);
		
		String entityWidgets = widgetMan.toStringValue("json");
		html = html.replace("||entitywidgets||", entityWidgets);

		String entityId = request.getParameter("id");
		if(HAPUtility.isStringEmpty(entityId)){
			entityId = "datasource.test.datasource:datasource_table";
		}

		html = html.replace("||entityid||", entityId);
		
		
//		HAPEntityWraper entityWraper = this.getUserContext("default").getEntityManager().getEntityByID(entityId);
//		
//		String entityJson = entityWraper.toStringValue(HAPAttributeConstant.FORMAT_JSON_WRAPER);
//		String allScript = "entityManager.addEntity("+entityJson+");";
//		html = html.replace("||script||", allScript);

		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.println(html);		
	}
	
	private String getWraperScript(HAPDataWraper wraper){
		HAPDataType dataType = wraper.getDataType();
		String script = this.readWraperScript(dataType);
	
		if(HAPUtility.isAtomType(dataType)){
			String value = wraper.toStringValue(HAPConstant.FORMAT_JSON_SIMPLE);
			if(value==null)  value="\"\"";
			script = script.replace("||value||", value);
		}
		
		return script;
	}
	
	private String readWraperScript(HAPDataType type){
		
		String fileName = null;
		if(HAPUtility.isAtomType(type) ||HAPUtility.isEntityType(type)){
			fileName = this.getEntityTemple(type.getCategary());
		}
		else{
			fileName = this.getEntityTemple(type.getType());
		}
		String out = HAPUtility.readFile(fileName, "\n");
		return out;
	}
	
	private String getEntityTemple(String type){
	     String webRootPath=
			        System.getProperty("user.home",
			          File.separatorChar + "home" +
			          File.separatorChar + "zelda") +
			          File.separatorChar;
	     return webRootPath + "template/valuewraper/"+type+".html";
	}

	private String getToolMainTemplate(){
	     String webRootPath=
			        System.getProperty("user.home",
			          File.separatorChar + "home" +
			          File.separatorChar + "zelda") +
			          File.separatorChar;
	     return webRootPath + "template/valuewraper/toolmain.html";
	}

}
