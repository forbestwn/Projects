package com.webmobile.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.nosliw.HAPUserContext;
import com.nosliw.data.HAPData;
import com.nosliw.entity.data.HAPDataWraper;
import com.nosliw.entity.data.HAPEntityContainerAttributeWraper;
import com.nosliw.entity.data.HAPEntityID;
import com.nosliw.entity.data.HAPEntityWraper;
import com.nosliw.entity.data.HAPReferenceWraper;
import com.nosliw.util.HAPConstant;
import com.nosliw.util.HAPJsonUtility;
import com.nosliw.util.HAPUtility;
import com.test.business.servlet.BasicServlet;
import com.webmobile.service.AppService;
import com.webmobile.service.FaceService;
import com.webmobile.service.NavigationService;

public class MobileServiceServlet extends BasicServlet{

	public void doGet (HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		
		String content = "";

		try{
			String command = request.getParameter("command");

			String parms = request.getParameter("parms");
			JSONObject jsonParms = null;
			try{
				if(HAPUtility.isStringNotEmpty(parms)){
					jsonParms = new JSONObject(parms);	 
				}
			}
			catch(Exception e){e.printStackTrace();}

			String application = request.getParameter("application");
			application = "default#application.application:appgood";
			HAPUserContext userContext = this.getApplicationContext().getUserContext("default");
			HAPEntityWraper applicationWraper = userContext.getEntityWraperRequest(new HAPEntityID(application), null);

			if("appInit".equals(command)){
				Map<String, String> jsonMap = new LinkedHashMap<String, String>();
				jsonMap.put("application", applicationWraper.toStringValue(HAPConstant.FORMAT_JSON));
				
				jsonMap.put("startPoint", ((HAPReferenceWraper)applicationWraper.getChildWraperByPath("startPoint")).getIDData().toString());
				
				Map<String, String> navJsonMap = new LinkedHashMap<String, String>();
				HAPEntityContainerAttributeWraper navWrapers = (HAPEntityContainerAttributeWraper)applicationWraper.getChildWraperByPath("navigations");
				Iterator<HAPDataWraper> navIt = navWrapers.iterate();
				while(navIt.hasNext()){
					HAPEntityWraper navWraper = ((HAPReferenceWraper)navIt.next()).getReferenceWraper();
					HAPEntityWraper navCommandWraper = (HAPEntityWraper)navWraper.getChildWraperByPath("command");
					navJsonMap.put(navCommandWraper.getID().toString(), navWraper.getID().toString());
				}
				jsonMap.put("navigation", HAPJsonUtility.getMapJson(navJsonMap));
				content = HAPJsonUtility.getMapJson(jsonMap);
			}
			else if("getApplication".equals(command)){
				String applicationID = jsonParms.optString("applicationID");
				content = AppService.getApplicationData(new HAPEntityID(applicationID), this.getApplicationContext());
				content = HAPJsonUtility.formatJson(content);
			}
			else if("getFace".equals(command)){
				String facePath = jsonParms.optString("facePath");
				String applicationID = jsonParms.optString("applicationID");
				content = AppService.getApplicationDataWithFacePaths(new HAPEntityID(applicationID), facePath, this.getApplicationContext());
				content = HAPJsonUtility.formatJson(content);
			}
			else if("getUITemplate".equals(command)){
				String uiTemplateID = jsonParms.optString("uiTemplateId");
				content = FaceService.readUITemplate(uiTemplateID, getApplicationContext());
				content = HAPJsonUtility.formatJson(content);
			}
			else if("navigate".equals(command)){
				String navId = jsonParms.optString("navigateID");
				String commandId = jsonParms.optString("commandID");
				JSONObject commandDataJson = jsonParms.optJSONObject("commandData");
				Map<String, HAPData> commandData = getMappedData(commandDataJson);
				content = NavigationService.navigate(navId, commandId, commandData, userContext);
			}
			else if("navigation".equals(command)){
				String startPointID = jsonParms.optString("faceStartPoint");
				JSONObject startPointData = jsonParms.optJSONObject("faceStartData");
				Map<String, HAPData> faceInputDataMap = getMappedData(startPointData);
				content = NavigationService.navigation(new HAPEntityID(startPointID), faceInputDataMap, userContext);
				content = HAPJsonUtility.formatJson(content);
			}
		}
		catch(Exception e){e.printStackTrace();}
		
		response.setContentType("application/json");
	    PrintWriter writer = response.getWriter();
	    writer.println(content);
	    System.out.println(content);
	}
	
	private Map<String, HAPData> getMappedData(JSONObject jsonData){
		Map<String, HAPData> out = new LinkedHashMap<String, HAPData>();
		if(jsonData!=null){
			 Iterator it = jsonData.keys();
			 while(it.hasNext()){
				 String key = (String)it.next();
				 HAPData inputData = this.getApplicationContext().getDataTypeManager().parseJson(jsonData.optJSONObject(key), null, null);
				 if(inputData!=null)		 out.put(key, inputData);
			 }
		}
		return out;
	}
	
	public void doPost (HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}	
	
}
