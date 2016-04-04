package com.webmobile.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.adiak.ui.entity.ADKTemplateEntity;
import com.nosliw.util.HAPUtility;
import com.webmobile.manager.UserContext;

public class Utility {

	
	public static String getWebRootPath(UserContext sysContext){
		String rootF = "";
		if(sysContext.getServletContext()==null){
			rootF = Utility.class.getClassLoader().getResource(".").getPath();
		}
		else{
			rootF = sysContext.getServletContext().getRealPath(".");
		}
		File rootFolder = new File(rootF);
		String path = rootFolder.getParentFile().getParentFile().getPath()+"/WebContent";
		return path;
	}
	
	
	
	
	public static String[] getChildrenFolderPath(String path){
		List<String> paths = new ArrayList<String>();
		File[] childrenFiles = new File(path).listFiles();
		for(File childFile : childrenFiles){
			paths.add(childFile.getPath());
		}
		return paths.toArray(new String[0]);
	}
}
