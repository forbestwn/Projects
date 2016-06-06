package com.nosliw.common.configure;

import java.io.InputStream;
import java.lang.reflect.Field;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPFileUtility;

public class HAPConfigureManager {

	private static HAPConfigureManager m_instance;
	
	//default configure infor
	private static HAPConfigurableImp m_rootConfigure;
	
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
		HAPConfigurableImp out = (HAPConfigurableImp)m_rootConfigure.clone();
		return out;
	}
	
	private static void init(){
		//import global variables
		m_rootConfigure = new HAPConfigurableImp();
		InputStream input = HAPFileUtility.getInputStreamOnClassPath(HAPConfigurableImp.class, "global.properties");
		if(input!=null)  m_rootConfigure.importFromFile(input);
		
		//import from constant
		Field[] declaredFields = HAPConstant.class.getDeclaredFields();
		for (Field field : declaredFields) {
		    if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
		    	try {
					m_rootConfigure.addGlobalValue(field.getName(), field.get(HAPConstant.class).toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		}
	}
}
