package com.test.business.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.log.HAPLogger;
import com.test.business.util.PathUtility;

public class FileLogger implements HAPLogger{

	public FileLogger(){
	}
	
	public void log(String title, String categary, String content, boolean appending){
		
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy_MM_dd");
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss");
		
		//get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		String dateStr = dateFormat1.format(date);
		String timeStr = dateFormat2.format(date);
		
		
		
//		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
//		jsonMap.put("time", timeStr);
//		jsonMap.put("log", content);
//		String logContent = HAPJsonUtility.formatJson(HAPJsonUtility.getMapJson(jsonMap)) + "\n,\n\n";

		String logContent =  "\"" + title+"_"+timeStr + "\":" + content + "\n\n";
		
		String fileName = PathUtility.getLogPath() + "/"+categary+"/"+categary+"_"+dateStr+".txt";
		this.writerToFile(fileName, logContent, appending);
	}

	private void writerToFile(String filename, String content, boolean appending){
		try{
			boolean isNewFile = false;
			
    		File file =new File(filename);
 
    		//if file doesnt exists, then create it
    		file.getParentFile().mkdir();
    		if(!file.exists()){
    			file.createNewFile();
    			isNewFile = true;
    		}
    		
    		String pureFileContent = content;
    		if(appending){
        		StringBuffer fileContentBuffer = new StringBuffer();
        		FileReader fr = new FileReader(filename); 
        		BufferedReader br = new BufferedReader(fr); 
        		String s; 
        		while((s = br.readLine()) != null) { 
        			fileContentBuffer.append(s);
        		} 
        		fr.close(); 
        		
        		String fileContent = "";
        		if(isNewFile) fileContent = "{";
        		fileContent = fileContent + fileContentBuffer.toString();
        		pureFileContent = fileContent;
        		if(!isNewFile){
        			int i = fileContent.lastIndexOf("}");
        			if(i!=-1)  pureFileContent = pureFileContent.substring(0, i);
        		}
        		
        		if(isNewFile)  pureFileContent = pureFileContent + content + "}\n ";
        		else pureFileContent = pureFileContent + "," + content + "}\n ";
        		
    		}
    		else{
    			pureFileContent = "{" + pureFileContent + "}";
    		}
 
    		//true = append file
    		FileWriter fileWritter = new FileWriter(filename, false);
//    		pureFileContent = HAPJsonUtility.formatJson(pureFileContent);
    		fileWritter.write(pureFileContent);
    	    fileWritter.close();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
	}
}
