package com.nosliw.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nosliw.app.instance.HAPApplicationClientContext;
import com.nosliw.app.instance.HAPApplicationClientInfo;
import com.nosliw.app.instance.HAPApplicationInstance;
import com.nosliw.app.service.HAPJsonService;
import com.nosliw.app.service.HAPRequestInfo;
import com.nosliw.app.utils.HAPApplicationErrorUtility;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.entity.dataaccess.HAPClientContext;

public class HAPServiceServlet extends HttpServlet{

	private static final long serialVersionUID = 6885333428614821237L;
	
	public void doPost (HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
	
	public void doGet (HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		try{
			String content = "";
			String command = request.getParameter("command");
			String clientId = request.getParameter("clientId");
			if("groupRequest".equals(command)){
				HAPServiceData out = null;
				HAPServiceData clientIdServiceData = this.getClientContext(clientId);
				if(clientIdServiceData.isSuccess()){
					HAPApplicationClientContext appClientContext = (HAPApplicationClientContext)clientIdServiceData.getData();
					String groupRequest = request.getParameter("parms");
					JSONArray jsonGroupReqs = new JSONArray(groupRequest);

					List<String> requestsResult = new ArrayList<String>();
					for(int i=0; i<jsonGroupReqs.length(); i++){
						JSONObject req = jsonGroupReqs.getJSONObject(i);
						HAPRequestInfo requestInfo = HAPRequestInfo.getRequestInfo(req);
						String result = (String)appClientContext.getRequestResultHistory(requestInfo);
						if(result==null){
							//not in history, then it is new quest
							HAPServiceData serviceData = processRequest(req, appClientContext);
							String requestResult = serviceData.toStringValue(HAPConstant.CONS_SERIALIZATION_JSON);
							//save result to client context history
							appClientContext.addReqeustResultHistory(new HAPRequestInfo(req.getString("requestId")), requestResult);
							requestsResult.add(requestResult);
						}
						else{
							//otherwise, use data from history
							requestsResult.add(result);
						}
					}
					out = HAPServiceData.createSuccessData(requestsResult);
				}
				else{
					out = clientIdServiceData;
				}
				content = out.toStringValue(HAPConstant.CONS_SERIALIZATION_JSON);
			}
			else{
//				String parms = request.getParameter("parms");
//				String infos = request.getParameter("infos");
//				JSONObject jsonInfos = new JSONObject(infos);	 
//				JSONObject jsonParms = new JSONObject(parms);	 
//				content = this.processRequest(command, jsonParms, jsonInfos);
			}
			
			response.setContentType("application/json");
		    PrintWriter writer = response.getWriter();
		    
//		    System.out.println(HAPJsonUtility.formatJson(content));
		    writer.println(HAPJsonUtility.formatJson(content));		
//		    writer.println(content);		
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private HAPServiceData getClientContext(String clientId){
		HAPServiceData out = null;
		HAPClientContext clientContext = HAPApplicationInstance.getApplicationInstantce().getClientContextManager().getClientContext(clientId);
		if(clientContext!=null){
			out = HAPServiceData.createSuccessData(clientContext);
		}
		else{
			out = HAPApplicationErrorUtility.createClientIdInvalidError(clientId);
		}
		return out;
	}
	
	/*
	 * process one request object
	 */
	private HAPServiceData processRequest(JSONObject req, HAPApplicationClientContext clientContext) throws Exception{
		HAPServiceData out = null;
		String reqType = req.getString("type");
		if(HAPConstant.CONS_REMOTESERVICE_TASKTYPE_NORMAL.equals(reqType)){
			//for normal request
			JSONObject serviceJson = req.getJSONObject("service");
			String com = serviceJson.getString("command");
			JSONObject jsonParms = serviceJson.getJSONObject("parms");
			out = this.processRequest(com, jsonParms, clientContext); 
		}
		else if(HAPConstant.CONS_REMOTESERVICE_TASKTYPE_GROUP.equals(reqType)){
			//for group task, 
			boolean success = true;
			String mode = req.getString("mode");
			List<HAPServiceData> serviceDatas = new ArrayList<HAPServiceData>();
			JSONArray jsonChildren = req.getJSONArray("children");
			for(int j=0; j<jsonChildren.length(); j++){
				HAPServiceData serviceData = this.processRequest(jsonChildren.getJSONObject(j), clientContext);
				serviceDatas.add(serviceData);
				if(serviceData.isFail()) {
					//if one child task fail, then stop processing 
					success = false;
					break;
				}
			}
			
			if(success==false){
				if(HAPConstant.CONS_REMOTESERVICE_GROUPTASK_MODE_ALWAYS.equals(mode)){
					//if group task mode is always, group task end with success 
					success = true;
				}
			}
			
			if(success)		out = HAPServiceData.createSuccessData(serviceDatas);
			else			out = HAPServiceData.createFailureData(serviceDatas, "");
		}
		return out;
	}
	
	private HAPServiceData processRequest(String command, JSONObject jsonParms, HAPApplicationClientContext clientContext){
		System.out.println("*********************** Start Service ************************");
		System.out.println("command " + command);
		System.out.println("parms " + jsonParms.toString());
		
		HAPServiceData serviceData = HAPJsonService.service(command, jsonParms, clientContext);
		
		String content = serviceData.toStringValue(HAPConstant.CONS_SERIALIZATION_JSON);
		content = HAPJsonUtility.formatJson(content);
		
		System.out.println("return: \n" + content);
		System.out.println("*********************** End Service ************************");
		return serviceData;
	}
}
