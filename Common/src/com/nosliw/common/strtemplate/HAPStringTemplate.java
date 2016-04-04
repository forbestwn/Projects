package com.nosliw.common.strtemplate;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPFileUtility;

public class HAPStringTemplate {

	private String m_template;
	
	public HAPStringTemplate(String template){
		this.m_template = template;
	}

	public HAPStringTemplate(InputStream stream){
		this.m_template = HAPFileUtility.readFile(stream);
	}
	
	public HAPStringTemplate(File file){
		this.m_template = HAPFileUtility.readFile(file);
	}
	
	public String getContent(Map<String, String> parms){
		String out = m_template;
		if(HAPBasicUtility.isStringNotEmpty(m_template)){
			for(String key : parms.keySet()){
				String fill = parms.get(key);
				if(fill==null)  fill = "";
				out = out.replace("||"+key+"||", fill);
			}
		}
		return out;
	}
	
}
