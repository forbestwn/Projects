package com.nosliw.configure;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.nosliw.application.HAPApplicationContext;
import com.nosliw.application.HAPApplicationManager;

public class HAPApplicationConfigureImp extends HAPApplicationManager implements HAPApplicationConfigure{

	Properties m_prop;
	
	public HAPApplicationConfigureImp(InputStream inputStream, HAPApplicationContext appContext){
		super(appContext);
		appContext.setApplicationConfigure(this);
		m_prop = new Properties();
		if(inputStream!=null){
	        try {
				this.m_prop.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
	}
	
	@Override
	public String getStringValue(String name, String value) {
		String out = this.m_prop.getProperty(name);
		if(out==null)  out = value;
		return out;
	}

	@Override
	public boolean getBooleanValue(String name, boolean value){
		String out = this.m_prop.getProperty(name);
		if(out==null)  return value;
		
		if(out.equals("yes") || out.equals("true")){
			return true;
		}
		else{
			return false;
		}
	}
	
}
