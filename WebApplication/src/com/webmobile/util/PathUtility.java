package com.webmobile.util;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.adiak.ui.entity.ADKTemplateEntity;
import com.nosliw.util.HAPUtility;
import com.webmobile.manager.UserContext;

public class PathUtility {
	
	public static String getApplicationPath(){
		return "/Users/ningwang/Desktop/WebToApp/CoreProject/Application/web2app";
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
	
	public static String getUITemplatePaht(){
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
		return getApplicationPath() + "/uitemplate/"+templateFolders.get(type);
	}

	public static String readHtmlFile(UserContext sysContext){
		return getApplicationPath()+"/stringtemplate/main.html" + "\n";
	}

	public static String readTemplateHtml(ADKTemplateEntity template, UserContext sysContext){
		return readTemplateFile(template, "template.html", "\\\n", sysContext);
	}

	public static String readTemplateJs(ADKTemplateEntity template, UserContext sysContext){
		return readTemplateFile(template, "template.js", "\n", sysContext);
	}
	
	public static String readTemplateFile(ADKTemplateEntity template, String name, String nextLine, UserContext sysContext){
		String folderPath = getUITemplatePath(template.getType());
		String fileName = folderPath + "/" + template.getName() + "/" + name;
		return HAPUtility.readFile(fileName, nextLine);
	}

}

