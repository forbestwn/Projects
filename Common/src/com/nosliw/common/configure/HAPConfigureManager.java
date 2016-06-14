package com.nosliw.common.configure;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPFileUtility;

public class HAPConfigureManager {

	private static HAPConfigureManager m_instance;
	
	//pre configured base configuration
	private Map<String, HAPConfigureImp> m_baseConfigures;
	
	private HAPConfigureManager(){
		m_baseConfigures = new LinkedHashMap<String, HAPConfigureImp>();
		this.initDefaultBase();
	}
	
	public static HAPConfigureManager getInstance(){
		if(m_instance==null){
			m_instance = new HAPConfigureManager();
		}
		return m_instance;
	}
	
	public void registerConfigureBase(String name, HAPConfigureImp configureBase){
		this.m_baseConfigures.put(name, configureBase);
	}
	
	/*
	 * factory method to create configuration object
	 * base: whether to create base configure
	 */
	public HAPConfigureImp createConfigureWithBaseName(String base){
		HAPConfigureImp out = null;
		if(base!=null){
			out = (HAPConfigureImp)m_baseConfigures.get(base).clone();
		}
		else{
			out = new HAPConfigureImp();
		}
		return out;
	}

	public HAPConfigureImp createConfigureFromFileWithBaseConfigure(String file, Class pathClass, HAPConfigureImp baseConfigure){
		HAPConfigureImp out = null;
		if(baseConfigure!=null)  out = (HAPConfigureImp)baseConfigure.clone();
		else out = new HAPConfigureImp();
		
		InputStream input = HAPFileUtility.getInputStreamOnClassPath(pathClass, file);
		if(input!=null)  out.importFromFile(input);
		return out;
	}

	/*
	 * factory method to create configuration object from property file
	 * file: property file name
	 * pathClass: class package to looking for the file
	 * base: whether to create base configure
	 */
	public HAPConfigureImp createConfigureFromFileWithBaseName(String file, Class pathClass, String base){
		HAPConfigureImp out = new HAPConfigureImp();
		InputStream input = HAPFileUtility.getInputStreamOnClassPath(pathClass, file);
		if(input!=null)  out.importFromFile(input);

		//merge with base configure
		out.softMerge(this.getBaseConfigure(base), false);
		return out;
	}
	
	private HAPConfigureImp getBaseConfigure(String name){
		return this.m_baseConfigures.get(name);
	}
	
	private void initDefaultBase(){
		//import global variables
		HAPConfigureImp rootConfigure = this.createConfigureFromFileWithBaseName("global.properties", HAPConfigureManager.class, null);
		this.registerConfigureBase(HAPConstant.CONS_CONFIGURATION_DEFAULTBASE, rootConfigure);
		
		//import from constant
		Field[] declaredFields = HAPConstant.class.getDeclaredFields();
		for (Field field : declaredFields) {
		    if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
		    	try {
		    		rootConfigure.addGlobalValue(field.getName(), field.get(HAPConstant.class).toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		}
	}
}
