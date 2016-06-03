package com.nosliw.common.configure;

import java.io.InputStream;

import com.nosliw.common.utils.HAPFileUtility;

public class HAPConfigureManager {

	private static HAPConfigureManager m_instance;
	
	private static HAPConfigurableImp m_globalConfigure;
	
	private HAPConfigureManager(){
		this.init();
	}
	
	public static HAPConfigureManager getInstance(){
		if(m_instance==null){
			m_instance = new HAPConfigureManager();
		}
		return m_instance;
	}
	
	public HAPConfigurableImp newConfigure(){
		HAPConfigurableImp out = (HAPConfigurableImp)m_globalConfigure.clone();
		return out;
	}
	
	private static void init(){
		//import global variables
		m_globalConfigure = new HAPConfigurableImp();
		InputStream input = HAPFileUtility.getInputStreamOnClassPath(HAPConfigurableImp.class, "global.properties");
		if(input!=null)  m_globalConfigure.importFromFile(input);
	}

}
