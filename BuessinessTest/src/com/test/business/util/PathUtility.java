package com.test.business.util;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.util.HAPUtility;
import com.test.business.manager.UserContext;

public class PathUtility {
	
	public static String getApplicationPath(){
		return "/Users/ningwang/Desktop/WebToApp/CoreProject/Application/test_business";
//		return "D:/MyWork/CoreProject/Application/test_business";
	}

	public static String getStringTemplatePath(){
		return getApplicationPath() + "/stringtemplate";
	}

	public static String getEntityDefinitionPath(){
		return getApplicationPath()+"/entitydefinition";
	}
	
	public static String getUserDataPath(String user){
		return getApplicationPath()+"/userdata/"+user;
	}
	
	public static String getLogPath(){
		return "/Users/ningwang/Desktop/WebToApp" + "/logs";
	}

	public static String getUITemplatePath(){
		return getApplicationPath() + "/uitemplate";
	}
	
	public static String getUITemplatePath(String type){
		Map<String, String> templateFolders = new LinkedHashMap<String, String>(){
			  {
			        put("layout", "/layout");
			        put("body", "/body");
			        put("border", "/border");
			  }	
		};
		String typePath = templateFolders.get(type);
		if(typePath==null){
			typePath = "/" + type;
		}
		
		return getApplicationPath() + "/uitemplate/"+typePath;
	}

	public static String getUIResourcePath(){
		return getApplicationPath() +"/uiresource/";
	}
	
	public static String readHtmlFile(UserContext sysContext){
		return getApplicationPath()+"/stringtemplate/main.html" + "\n";
	}

}

