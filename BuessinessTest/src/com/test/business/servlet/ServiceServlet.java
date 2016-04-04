package com.test.business.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.HAPUserContextInfo;
import com.nosliw.exception.HAPServiceData;
import com.nosliw.service.HAPJsonService;
import com.nosliw.service.HAPRequestInfo;
import com.nosliw.util.HAPConstant;
import com.nosliw.util.HAPJsonUtility;

public class ServiceServlet extends BasicServlet{
	public void doPost (HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
	
	public void doGet (HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		try{
			m_oldServiceData = null;
			
			HAPUserContextInfo userContextInfo = new HAPUserContextInfo("default");
			String content = "";
			String command = request.getParameter("command");
			if("groupRequest".equals(command)){
				String groupRequest = request.getParameter("parms");
				JSONArray jsonGroupReqs = new JSONArray(groupRequest);
				StringBuffer contents = new StringBuffer();
				contents.append("[");
				for(int i=0; i<jsonGroupReqs.length(); i++){
					JSONObject req = jsonGroupReqs.getJSONObject(i);
					String com = req.getString("command");
					JSONObject jsonInfos = req.getJSONObject("infos");
					JSONObject jsonParms = req.getJSONObject("parms");
					contents.append(this.processRequest(com, jsonParms, jsonInfos, userContextInfo));
					if(i+1<jsonGroupReqs.length())			contents.append(",");
				}
				contents.append("]");
				content = contents.toString();
			}
			else{
				String parms = request.getParameter("parms");
				String infos = request.getParameter("infos");
				JSONObject jsonInfos = new JSONObject(infos);	 
				JSONObject jsonParms = new JSONObject(parms);	 
				content = this.processRequest(command, jsonParms, jsonInfos, userContextInfo);
			}
			
			response.setContentType("application/json");
		    PrintWriter writer = response.getWriter();
		    writer.println(content);		
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}	

	private HAPServiceData m_oldServiceData = null;
	private String processRequest(String command, JSONObject jsonParms, JSONObject jsonInfos, HAPUserContextInfo userContextInfo){
		System.out.println("*********************** Start Service ************************");
		System.out.println("command " + command);
		System.out.println("parms " + jsonParms.toString());
		System.out.println("infos " + jsonInfos.toString());
		
		HAPJsonService jsonServices = new HAPJsonService(this.getApplicationContext());
		HAPRequestInfo reqInfo = HAPRequestInfo.getRequestInfo(jsonInfos);
		if(this.m_oldServiceData!=null){
			reqInfo.transactionId = this.m_oldServiceData.getTransactionId();
		}
		HAPServiceData serviceData = jsonServices.service(command, jsonParms, reqInfo, userContextInfo);
		this.m_oldServiceData = serviceData;
		
		String content = serviceData.toStringValue(HAPConstant.FORMAT_JSON);
		content = HAPJsonUtility.formatJson(content);
		
		System.out.println("return: \n" + content);
		System.out.println("*********************** End Service ************************");
		return content;
	}
}
