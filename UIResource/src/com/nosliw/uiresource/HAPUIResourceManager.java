package com.nosliw.uiresource;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.configure.HAPConfiguration;
import com.nosliw.common.configure.HAPConfigureImp;
import com.nosliw.common.serialization.HAPStringable;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.data.HAPDataTypeManager;

public class HAPUIResourceManager implements HAPStringable{

	private Map<String, HAPUIResource> m_uiResource;
	
	private Map<String, String> m_uiResourceScripts;
	
	private HAPConfiguration m_setting;
	
	private HAPDataTypeManager m_dataTypeMan;
	
	public HAPUIResourceManager(HAPConfiguration setting, HAPDataTypeManager dataTypeMan) {
		this.m_dataTypeMan = dataTypeMan;
		this.m_uiResource = new LinkedHashMap<String, HAPUIResource>();
		this.m_uiResourceScripts = new LinkedHashMap<String, String>();
		this.createDefaultConfiguration();
		this.m_setting = setting;
	}

	private void createDefaultConfiguration(){
		this.m_setting = new HAPConfigureImp();
	}
	
	public void addUIResource(HAPUIResource resource){
		String name = resource.getId();
		this.m_uiResource.put(name, resource);
		this.m_uiResourceScripts.put(name, this.readUIResourceScript(name));
	}
	
	public HAPUIResource getUIResource(String name){
		return this.m_uiResource.get(name);
	}
	
	public HAPUIResource[] getAllUIResource(){
		return this.m_uiResource.values().toArray(new HAPUIResource[0]);
	}

	/*
	 * return the script string (an json structure containing block and expression) for ui resource
	 */
	public String getUIResourceScript(String name){
		String out = this.m_uiResourceScripts.get(name);
		if(out==null){
			out = this.readUIResourceScript(name);
			this.m_uiResourceScripts.put(name, out);
		}
		return out;
	}
	
	public String readUIResourceScript(String name){
		String fileName = HAPUIResourceUtility.getUIResourceScriptFileName(name, this); 
		String out = HAPFileUtility.readFile(fileName);
		return out;
	}
	
	/*
	 * get temporate file location
	 */
	public String getTempFileLocation(){
		String scriptLocation = this.m_setting.getStringValue(HAPConstant.CONS_UIRESOURCEMAN_SETTINGNAME_SCRIPTLOCATION);
		return scriptLocation;
	}
	
	@Override
	public String toStringValue(String format) {
		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
		for(String name : this.m_uiResource.keySet()){
			jsonMap.put(name, this.m_uiResource.get(name).toStringValue(format));
		}
		return HAPJsonUtility.getMapJson(jsonMap);
	}
	
	@Override
	public String toString(){ return this.toStringValue(HAPConstant.CONS_SERIALIZATION_JSON); }

	protected HAPDataTypeManager getDataTypeManager(){	return this.m_dataTypeMan;	}
	protected HAPConfiguration getConfiguration(){ return this.m_setting; }
	
}
