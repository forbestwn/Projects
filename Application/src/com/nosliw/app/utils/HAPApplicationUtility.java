package com.nosliw.app.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.nosliw.common.configure.HAPConfiguration;
import com.nosliw.common.utils.HAPFileUtility;

public class HAPApplicationUtility {

	/*
	 * 
	 */
	static public List<String> getFileNames(HAPConfiguration filePathConfigure){
		List<String> out = new ArrayList<String>();
		
		String path = filePathConfigure.getStringValue("path");
		String[] fileNames = filePathConfigure.getArrayValue("files");
		for(String fileName : fileNames){
			out.add(HAPFileUtility.buildFullFileName(path, fileName));
		}
		return out;
	}
	
	/*
	 * 
	 */
	static public List<InputStream> getFileInputStreams(HAPConfiguration filePathConfigure){
		List<InputStream> out = new ArrayList<InputStream>();
		
		String path = filePathConfigure.getStringValue("path");
		String[] fileNames = filePathConfigure.getArrayValue("files");
		for(String fileName : fileNames){
			out.add(HAPFileUtility.getInputStreamFromFile(path, fileName));
		}
		return out;
	}
	
}
