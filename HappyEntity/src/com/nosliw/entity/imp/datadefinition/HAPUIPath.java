package com.nosliw.entity.imp.datadefinition;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.application.utils.HAPJsonUtility;
import com.nosliw.entity.data.HAPEntityID;

public class HAPUIPath {

	public HAPEntityID parentId;
	public String path;
	public String name;
	public Map<String, String> parms = new LinkedHashMap<String, String>();
	
	public String toStringValue(String format){
		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
		jsonMap.put("parentID", this.parentId.toString());
		jsonMap.put("path", path);
		jsonMap.put("name", name);
		jsonMap.put("parms", HAPJsonUtility.getMapJson(parms));
		return HAPJsonUtility.getMapJson(jsonMap);
	}
	
}
