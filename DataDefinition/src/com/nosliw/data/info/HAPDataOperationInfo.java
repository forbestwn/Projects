package com.nosliw.data.info;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.serialization.HAPStringable;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.data.utils.HAPAttributeConstant;

/*
 * store all the information related with operation:
 * 		operation Name
 * 		operation Parms data type
 * 		operation Out data type
 * 		operation Description
 * for ins and out attribute, data type version information is not appliable 
 * we consider two operation is equal when only when they have the same name. 
 */
public class HAPDataOperationInfo implements HAPStringable{

	//operation name
	private String m_name;
	//operation Out information
	private HAPDataTypeInfo m_outDataTypeInfo;
	//operation In information, multiple value
	private List<HAPDataTypeInfo> m_inDataTypeInfos;
	//operation description
	private String m_description;

	//path to describe how to convert the parms   version|123.parent.parent.version|456
	private String m_convertPath;
	//where the operation orignally come from
	private HAPDataOperationInfo m_originalDataOperationInfo;
	
	//all dependent data type infos
	private Map<String, Set<HAPDataTypeInfo>> m_dependentDataTypeInfo;
	
	public HAPDataOperationInfo(HAPDataOperationInfo operationInfo, String path){
		this(operationInfo.getName(), operationInfo.getInDataTypeInfos(), operationInfo.getOutDataTypeInfo(), operationInfo.getDescription());
		this.m_convertPath = path;
		this.m_originalDataOperationInfo = operationInfo;
		this.m_dependentDataTypeInfo = new LinkedHashMap<String, Set<HAPDataTypeInfo>>();
	}
	
	public HAPDataOperationInfo(String name, List<HAPDataTypeInfo> inDataTypeInfos, HAPDataTypeInfo outDataTypeInfo, String description){
		this.m_name = name;
		this.m_inDataTypeInfos = inDataTypeInfos;
		this.m_outDataTypeInfo = outDataTypeInfo;
		this.m_description = description;
		this.m_dependentDataTypeInfo = new LinkedHashMap<String, Set<HAPDataTypeInfo>>();
	}
	
	public String getName(){return this.m_name;}
	public HAPDataTypeInfo getOutDataTypeInfo(){return this.m_outDataTypeInfo;}
	public List<HAPDataTypeInfo> getInDataTypeInfos(){return this.m_inDataTypeInfos;}
	public String getDescription(){return this.m_description;}
	public String getConvertPath(){return this.m_convertPath;}
	public Set<HAPDataTypeInfo> getDependentDataTypeInfos(String scriptName){
		if(HAPBasicUtility.isStringEmpty(m_convertPath)){
			//no convert
			Set<HAPDataTypeInfo> dependents = this.m_dependentDataTypeInfo.get(scriptName);
			if(dependents==null)   return new HashSet<HAPDataTypeInfo>();
			else return dependents;
		}
		else{
			//get from original 
			return this.m_originalDataOperationInfo.getDependentDataTypeInfos(scriptName);
		}
	}
	public void setDependentDataTypeInfos(Set<HAPDataTypeInfo> infos, String scriptName){this.m_dependentDataTypeInfo.put(scriptName, infos);}
	
	public int getInNumber(){return this.m_inDataTypeInfos.size();}

	@Override
	public String toStringValue(String format) {
		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
		
		jsonMap.put(HAPAttributeConstant.ATTR_DATAOPERATIONINFO_NAME, this.m_name);
		jsonMap.put(HAPAttributeConstant.ATTR_DATAOPERATIONINFO_DESCRIPTION, this.m_description);
		jsonMap.put(HAPAttributeConstant.ATTR_DATAOPERATIONINFO_CONVERTPATH, this.m_convertPath);
		jsonMap.put(HAPAttributeConstant.ATTR_DATAOPERATIONINFO_OUT, this.m_outDataTypeInfo.toStringValue(format));
		jsonMap.put(HAPAttributeConstant.ATTR_DATAOPERATIONINFO_DEPENDENTDATATYPES, HAPJsonUtility.getSetObjectJson(this.getDependentDataTypeInfos(HAPConstant.CONS_OPERATIONDEF_SCRIPT_JAVASCRIPT)));
		
		List<String> inJsons = new ArrayList<String>();
		for(HAPDataTypeInfo info : this.m_inDataTypeInfos){
			inJsons.add(info.toStringValue(format));
		}
		jsonMap.put(HAPAttributeConstant.ATTR_DATAOPERATIONINFO_INS, HAPJsonUtility.getArrayJson(inJsons.toArray(new String[0])));
		
		return HAPJsonUtility.getMapJson(jsonMap);
	}
	
	@Override
	public String toString(){
		return HAPJsonUtility.formatJson(this.toStringValue(HAPConstant.CONS_SERIALIZATION_JSON));
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof HAPDataOperationInfo){
			HAPDataOperationInfo info = (HAPDataOperationInfo)o;
			if(!HAPBasicUtility.isEquals(this.getName(), info.getName()))  return false;
			return true;	
		}
		else return false;
	}
}
