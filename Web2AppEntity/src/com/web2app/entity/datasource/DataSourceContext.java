package com.web2app.entity.datasource;

import java.util.LinkedHashMap;
import java.util.Map;

import com.adiak.ADKResourceManager;
import com.nosliw.entity.HAPEntityData;
import com.web2app.entity.organization.OrganizationInfo;

public class DataSourceContext extends HAPEntityData{

	public final static String CONTEXT_DEFAULT = "default";
	public final static String CONTEXT_LOCAL = "local";
	
	private static final String ATTRIBUTE_NAME = "name";
	private final static String ATTRIBUTE_BASEURL = "baseurl";
	private final static String ATTRIBUTE_BASEDOMPATH = "basedompath";
	private final static String ATTRIBUTE_BUSINESSINFO = "businessinfo";
	
	private final static String ATTRIBUTE_SOURCE_INPUT = "ATTRIBUTE_SOURCE_INPUT";
	
	private Map<String, Object> m_values = null;
	
	private ADKResourceManager m_resourceMan = null;
	
	public DataSourceContext()
	{
		super();
		this.m_values = new LinkedHashMap<String, Object>();
	}
	
	public ADKResourceManager getResourceMan()
	{
		return this.m_resourceMan;
	}
	public void setResourceMan(ADKResourceManager man)
	{
		this.m_resourceMan = man;
	}
	
	public String getName()
	{
		return this.getAtomAttributeValue(ATTRIBUTE_NAME);
	}
	
	public boolean isLocal(){
		return this.getAtomAttributeValue(ATTRIBUTE_NAME).equals(CONTEXT_LOCAL);
	}
	
	public OrganizationInfo getBusinessInfo(){
		return (OrganizationInfo)this.getTempValue(ATTRIBUTE_BUSINESSINFO);
	}
	
	public Object getSourceInput(){
		return this.getTempValue(ATTRIBUTE_SOURCE_INPUT);
	}
	public void setSourceInput(Object data){
		this.setTempValue(ATTRIBUTE_SOURCE_INPUT, data);
	}
	
	public String getBaseUrl(){
		String baseurl = getAtomAttributeValue(DataSourceContext.ATTRIBUTE_BASEURL);
		return baseurl;
	}
	public String getBaseDompath(){
		String basedompath = getAtomAttributeValue(DataSourceContext.ATTRIBUTE_BASEDOMPATH);
		return basedompath;
	}
	
	public void setBusinessInfo(OrganizationInfo info){
		this.setTempValue(ATTRIBUTE_BUSINESSINFO, info);
	}
	
	public Object getTempValue(String attr)
	{
		return this.m_values.get(attr);
	}
	
	public void setTempValue(String attr, Object value)
	{
		this.m_values.put(attr, value);
	}
	
}
   