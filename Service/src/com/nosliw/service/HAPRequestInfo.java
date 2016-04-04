package com.nosliw.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPStringable;
import com.nosliw.common.utils.HAPJsonUtility;

public class HAPRequestInfo implements HAPStringable{

	public String requestId;
	public String transactionId;
	
	public HAPRequestInfo(String req, String trans){
		this.requestId = req;
		this.transactionId = trans;
	}
	
	public HAPRequestInfo(){
	}
	
	@Override
	public String toStringValue(String format) {
		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
		jsonMap.put("requestId", this.requestId);
		jsonMap.put("transactionId", this.transactionId);
		return HAPJsonUtility.getMapJson(jsonMap);
	}

	public static HAPRequestInfo getRequestInfo(JSONObject jsonObj){
		HAPRequestInfo out = new HAPRequestInfo();
		
		out.requestId = jsonObj.optString("requestId", null);
		out.transactionId = jsonObj.optString("transactionId", null);
		
		return out;
	}
	
}
