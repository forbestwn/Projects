package com.nosliw.data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.configure.HAPConfigurable;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.strtemplate.HAPStringTemplateUtil;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.common.utils.HAPNamingConversionUtility;
import com.nosliw.data.info.HAPDataOperationInfo;
import com.nosliw.data.info.HAPDataTypeInfo;
import com.nosliw.data.info.HAPDataTypeInfoWithVersion;
import com.nosliw.data.utils.HAPAttributeConstant;
import com.nosliw.data.utils.HAPDataErrorUtility;
import com.nosliw.data.utils.HAPDataUtility;

/*
 * data type definition implemetation
 * this implementation support parent data type (like extends in class definition)
 * all the operations for this data type are provided through HAPDataTypeOperations sections
 * when do operation, following the steps:
 * 		seach in HAPDataTypeOperations list one by one, and operate on the first one found
 * 		if not found in HAPDataTypeOperations list, then try to operate on parent data type
 * 		in order to operate on parent data type, the data on current data type must convert to parent data type
 */

public abstract class HAPDataTypeImp implements HAPDataType{
	
	private HAPDataTypeInfoWithVersion m_dataTypeInfo;
	private String m_description;

	//parent data type
	private HAPDataTypeImp m_parentDataType;
	private HAPDataTypeInfo m_parentDataTypeInfo;

	//operation sections
	private HAPDataTypeOperations m_operations;

	//older and newer version DataType
	private HAPDataTypeImp m_olderDataType;
	private HAPDataTypeImp m_newerDataType;
	
	//we can configure the behavior of data type
	private HAPConfigurable m_configures;

	//store all newData operations (constructor)
	private Set<HAPDataOperationInfo> m_newDataOperations;
	//store all local data operations
	private Map<String, HAPDataOperationInfo> m_localOperations;
	//cache all available operation information including the operation path
	private Map<String, HAPDataOperationInfo> m_availableOperations;

	private HAPDataTypeManager m_dataTypeMan;

	protected HAPDataTypeImp(HAPDataTypeInfoWithVersion dataTypeInfo,        
							HAPDataType olderDataType, 		
							HAPDataTypeInfo parentDataTypeInfo, 
							HAPConfigurable configures,
							String description,
							HAPDataTypeManager dataTypeMan){
		super();
		this.m_dataTypeInfo = dataTypeInfo;
		this.m_parentDataTypeInfo = parentDataTypeInfo;
		this.m_dataTypeMan = dataTypeMan;
		
		this.m_configures = configures;
		this.m_description = description;

		this.setOlderDataType((HAPDataTypeImp)olderDataType);
		
		this.initDataType();
	}

	/*
	 * init data type object based on the configure information
	 */
	protected void initDataType(){}


	/****************************** Operation Related ********************************/
	@Override
	public HAPServiceData operate(String operation, HAPData[] parms){
		
		HAPDataOperationInfo operationInfo = this.getOperationInfoByName(operation);
		if(operationInfo==null){
			//cannot find by name, means does not defined in anywhere (older version, parent)
			return HAPDataErrorUtility.createDataOperationNotDefinedError(this.getDataTypeInfo(), operation, null); 
		}
		
		String convertPath = operationInfo.getConvertPath();
		if(HAPBasicUtility.isStringEmpty(convertPath)){
			//within current version, no convertion
			return this.operateLocally(operation, parms);
		}
		else{
			//do convertion of the parms
			HAPData[] oldParms = parms;
			HAPDataTypeImp newDataType = this;
			String[] convertPathSegs = HAPNamingConversionUtility.parsePathInfos(convertPath); 
			for(String convertPathSeg : convertPathSegs){
				HAPData[] newParms = new HAPData[parms.length];
				String[] convertPathSegDetails = HAPNamingConversionUtility.parseDetailInfos(convertPathSeg);
				switch(convertPathSegDetails[0]){
				case HAPConstant.CONS_OPERATIONDEF_PATH_PARENT:
				{
					//convert parms to parent type
					for(int i=0; i<oldParms.length; i++){
						HAPData d = oldParms[i];
						if(newDataType.getDataTypeInfo().equalsWithoutVersion(HAPDataUtility.getDataTypeInfo(d))){ // just convert all the parms of the same data type (not considering the version)
							//conver to parent type
							HAPServiceData s = d.getDataType().operate(HAPConstant.CONS_DATAOPERATION_TOPARENTTYPE, new HAPData[]{d});
							if(s.isSuccess())  newParms[i] = (HAPData)s.getData();	
							else	return s;
						}
						else{
							newParms[i] = d;
						}
					}
					newDataType = newDataType.getParentDataType();
					break;
				}
				case HAPConstant.CONS_OPERATIONDEF_PATH_VERSION:
				{
					int newVersion = Integer.parseInt(convertPathSegDetails[1]);
					newDataType = this.getDataTypeByVersion(newVersion);
					for(int i=0; i<oldParms.length; i++){
						HAPData d = oldParms[i];
						if(newDataType.getDataTypeInfo().equalsWithoutVersion(HAPDataUtility.getDataTypeInfo(d))){ // just convert all the parms of the same data type (not considering the version)
							//conver to parent type
							HAPServiceData s = this.operate(HAPConstant.CONS_DATAOPERATION_TOVERSION, new HAPData[]{d, HAPDataTypeManager.INTEGER.createDataByValue(newVersion)});
							if(s.isSuccess())	newParms[i] = (HAPData)s.getData();
							else	return s;
						}
						else{
							newParms[i] = d;
						}
					}
					break;
				}
				}
				oldParms = newParms; 
			}
			HAPServiceData out = newDataType.operate(operation, oldParms);
			if(out.isSuccess() && out.getData()!=null){
				HAPData outData = (HAPData)out.getData();
				if(HAPDataUtility.getDataTypeInfoWithVersion(outData).equalsWithoutVersion(this.getDataTypeInfo())){
					//convert the result back to current version
					HAPServiceData s = this.operate(HAPConstant.CONS_DATAOPERATION_FROMVERSION, new HAPData[]{outData});
					if(s.isSuccess())	out.setData(s.getData());
					else	return s;
				}
			}
			return out;
		}
	}
		
	@Override
	public HAPServiceData newData(HAPData[] data){
		HAPDataOperationInfo newDataOp = null;
		for(HAPDataOperationInfo opInfo : this.m_newDataOperations){
			//check parm length
			if(opInfo.getInNumber()==data.length){
				//check each data type
				List<HAPDataTypeInfo> dataTypeInfo = opInfo.getInDataTypeInfos();
				boolean sameParmType = true;
				for(int i=0; i<data.length; i++){
					if(!HAPDataUtility.getDataTypeInfo(data[i]).equals(dataTypeInfo.get(i))){
						sameParmType = false;
						break;
					}
				}
				if(sameParmType){
					newDataOp = opInfo;
					break;
				}
			}
		}
		
		HAPServiceData out = null;
		if(newDataOp!=null){
			out = this.operateLocally(newDataOp.getName(), data);
		}
		else{
			out = HAPDataErrorUtility.createNewDataOperationNotDefinedError(this.getDataTypeInfo(), data, null);
		}
		return out;
	}
	
	@Override
	public String getOperateScript(String operation, String format){
		HAPServiceData out = this.getDataTypeOperationsObject().getOperateScript(operation, format);
		if(out.isSuccess())  return (String)out.getData();
		else return null;
	}
	
	@Override
	public Set<HAPDataTypeInfo> getOperationDependentDataTypes(String operation){
		HAPServiceData out = this.getDataTypeOperationsObject().getOperationDependentDataType(operation);
		if(out.isSuccess())  return (Set<HAPDataTypeInfo>)out.getData();
		else return new HashSet<HAPDataTypeInfo>();
	}

	@Override
	public Map<String, HAPDataOperationInfo> getOperationInfos(){
		if(this.m_availableOperations==null)	this.buildOperationInfos();
		return this.m_availableOperations;
	}
	
	@Override
	public HAPDataOperationInfo getOperationInfoByName(String name){		return this.getOperationInfos().get(name);	}

	@Override
	public Set<HAPDataOperationInfo> getNewDataOperations(){
		if(this.m_newDataOperations==null)  this.buildOperationInfos();
		return this.m_newDataOperations;
	}

	
	public void setDataTypeOperations(HAPDataTypeOperations operations){this.m_operations = operations;}

	/*
	 * build all the available operation infor for this data type and save them so that we don't need to build it next time when need operation
	 */
	private Map<String, HAPDataOperationInfo> buildOperationInfos(){

		this.m_availableOperations = new LinkedHashMap<String, HAPDataOperationInfo>();
		this.m_localOperations = new LinkedHashMap<String, HAPDataOperationInfo>();
		this.m_newDataOperations = new HashSet<HAPDataOperationInfo>();
		
		
		this.buildOperationInfosLocally();
		
		this.buildOperationInfosWithinVersion();
		
		this.buildOperationInfosForParent();
		
		return this.m_availableOperations;
	}
	
	/*
	 * try to get operation info from local data type (current version def)
	 */
	private Map<String, HAPDataOperationInfo> buildOperationInfosLocally(){
		//locally
		HAPDataTypeOperations dataTypeOps = this.getDataTypeOperationsObject();
		
		if(dataTypeOps!=null){
			Map<String, HAPDataOperationInfo> ops = dataTypeOps.getOperationInfos();
			for(String name : ops.keySet()){
				if(name.startsWith(HAPConstant.CONS_DATAOPERATION_NEWDATA)){
					//constructor operation
					this.getNewDataOperations().add(ops.get(name));
				}
				else{
					//normal operation
					this.getDataOperationInfosLocally().put(name, ops.get(name));
					if(!this.m_availableOperations.containsKey(name)){
						this.m_availableOperations.put(name, ops.get(name));
					}
				}
			}
		}
		return this.m_availableOperations;
	}
	
	/*
	 * build available operation info (basic info + convert path) from old version
	 */
	private Map<String, HAPDataOperationInfo> buildOperationInfosWithinVersion(){
		if(this.m_olderDataType==null)   return this.m_availableOperations;
		//older version
		HAPDataTypeImp olderDataType = this.m_olderDataType;
		Map<String, HAPDataOperationInfo> olderOpInfos = olderDataType.buildOperationInfosWithinVersion();
		for(String name : olderOpInfos.keySet()){
			if(!this.m_availableOperations.containsKey(name)){
				String oldPath = HAPNamingConversionUtility.cascadePath(
						HAPNamingConversionUtility.cascadeDetail(HAPConstant.CONS_OPERATIONDEF_PATH_VERSION, olderDataType.getDataTypeInfo().getVersionNumber()+""),
						olderOpInfos.get(name).getConvertPath());
				this.m_availableOperations.put(name, new HAPDataOperationInfo(olderOpInfos.get(name), oldPath));
			}
		}
		return this.m_availableOperations;
	}
	
	/*
	 * build available operation info (basic info + convert path) from parent
	 */
	private Map<String, HAPDataOperationInfo> buildOperationInfosForParent(){
		HAPDataTypeImp parentDataType = this.getParentDataType();
		if(parentDataType==null)  return this.m_availableOperations;
		
		Map<String, HAPDataOperationInfo> parentOps = parentDataType.getOperationInfos();
		if(parentOps==null){
			//parent available operation has not been build, build it now
			parentOps = parentDataType.buildOperationInfos();
		}
		
		for(String name : parentOps.keySet()){
			if(!this.m_availableOperations.containsKey(name)){
				String parentPath = HAPNamingConversionUtility.cascadePath(HAPConstant.CONS_OPERATIONDEF_PATH_PARENT,
						parentOps.get(name).getConvertPath());
				this.m_availableOperations.put(name, new HAPDataOperationInfo(parentOps.get(name), parentPath));
			}
		}
		return this.m_availableOperations;
	}

	private HAPDataTypeOperations getDataTypeOperationsObject(){return this.m_operations;}
	
	private HAPServiceData operateLocally(String operation, HAPData[] parms){
		return this.getDataTypeOperationsObject().operate(operation, parms);
	}

	private Map<String, HAPDataOperationInfo> getDataOperationInfosLocally(){
		if(this.m_localOperations==null)  this.buildOperationInfos();
		return m_localOperations;
	}

	
	/****************************** Version ********************************/
	@Override
	public HAPDataType getOlderDataType(){	return this.m_olderDataType;	}
	
	@Override
	public HAPDataType getNewerDataType(){  return this.m_newerDataType;	}

	void setOlderDataType(HAPDataTypeImp dataType){
		this.m_olderDataType = dataType;
		if(dataType!=null)		dataType.m_newerDataType = this;
	}
	
	/*
	 * get Data type with specified version number
	 */
	private HAPDataTypeImp getDataTypeByVersion(int version){
		HAPDataTypeImp out = null;
		if(version==this.getVersion())   return this;
		if(version>this.getVersion()){
			out = (HAPDataTypeImp)this.getNewerDataType();
		}
		else if(version<this.getVersion()){
			out = (HAPDataTypeImp)this.getOlderDataType();
		}
		if(out==null)   return null;
		else  return out.getDataTypeByVersion(version);
	}

	/*
	 * Utility method to get version number of current data type
	 */
	private int getVersion(){ return this.getDataTypeInfo().getVersionNumber();	}
	
	
	/****************************** Configure ********************************/
	protected String getConfigure(String name){return this.m_configures.getStringValue(name);}
	
	
	/****************************** Basice Information ********************************/
	@Override
	public String getDescription(){return this.m_description;}
	
	@Override
	public HAPDataTypeInfoWithVersion getDataTypeInfo(){return this.m_dataTypeInfo;}

	@Override
	public HAPData[] getDomainDatas(){return null;	}

	@Override
	public HAPDataTypeInfo getParentDataTypeInfo(){return this.m_parentDataTypeInfo;}
	
	private HAPDataTypeImp getParentDataType(){
		if(this.m_parentDataTypeInfo==null)   return null;
		if(this.m_parentDataType==null){
			this.m_parentDataType = (HAPDataTypeImp)this.getDataTypeManager().getDataType(this.m_parentDataTypeInfo);
		}
		return this.m_parentDataType;
	}
	
	protected HAPDataTypeManager getDataTypeManager(){	return this.m_dataTypeMan;	}

	/****************************** Serialization ********************************/
	@Override
	public String buildLocalOperationScript(String scriptName){
		StringBuffer opFunctions = new StringBuffer();
		for(String name : this.getDataOperationInfosLocally().keySet()){
			String script = this.buildOperationFunctionScript(name, scriptName);
			if(script!=null)			opFunctions.append(script);
		}
		
		StringBuffer newFunctions = new StringBuffer();
		for(HAPDataOperationInfo dataOpInfo : this.getNewDataOperations()){
			String name = dataOpInfo.getName();
			String script = this.buildOperationFunctionScript(name, scriptName);
			if(script!=null)			newFunctions.append(script);
		}
		
		InputStream templateStream = HAPFileUtility.getInputStreamOnClassPath(HAPDataTypeImp.class, "DataTypeWithVersionOperations.txt");
		Map<String, String> parms = new LinkedHashMap<String, String>();
		parms.put("version", this.getVersion()+"");
		parms.put("opFunctions", opFunctions.toString());
		parms.put("newFunctions", newFunctions.toString());
		return HAPStringTemplateUtil.getStringValue(templateStream, parms);
	}
	
	private String buildOperationFunctionScript(String name, String scriptName){
		String script = this.getOperateScript(name, scriptName);
		if(script==null)  return null;

		InputStream templateStream = HAPFileUtility.getInputStreamOnClassPath(HAPDataTypeImp.class, "DataOperationFunction.txt");
		Map<String, String> parms = new LinkedHashMap<String, String>();
		parms.put("functionScript", script);
		parms.put("functionName", name);
		String out = HAPStringTemplateUtil.getStringValue(templateStream, parms);
		return out;
	}
	
	@Override
	public String toDataStringValue(HAPData data, String format){return data.toStringValue(format);	}
	
	@Override
	public String toStringValue(String format){
		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
		
		jsonMap.put(HAPAttributeConstant.ATTR_DATATYPE_DATATYPEINFO, this.getDataTypeInfo().toStringValue(format));
		if(this.getParentDataType()!=null){
			jsonMap.put(HAPAttributeConstant.ATTR_DATATYPE_PARENT, this.getParentDataTypeInfo().toStringValue(format));
		}

		if(this.getDataTypeOperationsObject()!=null){
			//all the available operations (name, input, output, convert path)
			Map<String, String> jsonOpInfoMap = new LinkedHashMap<String, String>();
			for(String name : this.getOperationInfos().keySet()){
				jsonOpInfoMap.put(name, this.getOperationInfos().get(name).toStringValue(format));
			}
			jsonMap.put(HAPAttributeConstant.ATTR_DATATYPE_OPERATIONINFOS, HAPJsonUtility.getMapJson(jsonOpInfoMap));
			
			List<String> jsonNewOpInfoList = new ArrayList<String>();
			for(HAPDataOperationInfo opInfo : this.getNewDataOperations()){
				jsonNewOpInfoList.add(opInfo.toStringValue(format));
			}
			jsonMap.put(HAPAttributeConstant.ATTR_DATATYPE_NEWOPERATIONINFOS, HAPJsonUtility.getListObjectJson(jsonNewOpInfoList));
		}
		return HAPJsonUtility.getMapJson(jsonMap);
	}
	
	@Override
	public String toString(){
		return HAPJsonUtility.formatJson(this.toStringValue(HAPConstant.CONS_SERIALIZATION_JSON));
	}	
}
