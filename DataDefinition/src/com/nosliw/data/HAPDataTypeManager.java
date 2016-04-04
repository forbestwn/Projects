package com.nosliw.data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.nosliw.common.configure.HAPConfigurable;
import com.nosliw.common.serialization.HAPStringable;
import com.nosliw.common.strtemplate.HAPStringTemplateUtil;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPFileUtility;
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.data.basic.bool.HAPBoolean;
import com.nosliw.data.basic.floa.HAPFloat;
import com.nosliw.data.basic.number.HAPInteger;
import com.nosliw.data.basic.string.HAPString;
import com.nosliw.data.basic.string.HAPStringData;
import com.nosliw.data.info.HAPDataTypeInfo;
import com.nosliw.data.info.HAPDataTypeInfoWithVersion;
import com.nosliw.data.utils.HAPAttributeConstant;

public class HAPDataTypeManager implements HAPStringable{

	public final static String DEFAULT_CATEGARY = HAPConstant.CONS_DATATYPE_CATEGARY_SIMPLE;
	public final static String DEFAULT_TYPE = HAPConstant.CONS_DATATYPE_TYPE_STRING;
	
	//static object for default data type: Bool, Integer, String, Float
	public static HAPBoolean BOOLEAN;
	public static HAPInteger INTEGER;
	public static HAPString STRING;
	public static HAPFloat FLOAT;
	
	//map for data type string vs data type obj
	private Map<String, HAPDataType> m_dataTypes;
	//store information for all the data type for particular categary
	private Map<String, Set<String>> m_dataTypeCategary;

	//store data operation scripts
	private Map<String, String> m_dataTypeOperationScripts;
	
	//configure info
	private HAPConfigurable m_configures;

	public HAPDataTypeManager(HAPConfigurable configures){
		this.m_configures = configures;
		
		this.m_dataTypes = new LinkedHashMap<String, HAPDataType>();
		this.m_dataTypeCategary = new LinkedHashMap<String, Set<String>>();
		this.m_dataTypeOperationScripts = new LinkedHashMap<String, String>();
		this.loadBasicDataType();
	}
	
	public HAPDataType registerDataType(HAPDataType dataType){
		HAPDataTypeInfo dataTypeInfo = dataType.getDataTypeInfo(); 
		String key = this.getDataTypeKey(dataTypeInfo);
		this.m_dataTypes.put(key, dataType);
		
		Set<String> types = this.m_dataTypeCategary.get(dataTypeInfo.getCategary());
		if(types==null){
			types = new HashSet<String>();
			this.m_dataTypeCategary.put(dataTypeInfo.getCategary(), types);
		}
		types.add(dataTypeInfo.getType());

		//store data type operation script of file and buffer
		this.processDataTypeOperationScript(dataType);
		
		return dataType;
	}
	
	public String[] getAllDataCategarys(){		return this.m_dataTypeCategary.keySet().toArray(new String[0]);	}
	
	public Set<String> getDataTypesByCategary(String categary){
		return this.m_dataTypeCategary.get(categary);
	}
	
	public HAPDataType[] getAllDataTypes(){		return this.m_dataTypes.values().toArray(new HAPDataType[0]);	}
	
	public HAPDataType getDataType(HAPDataTypeInfo dataTypeInfo){
		String key = this.getDataTypeKey(dataTypeInfo);
		return this.m_dataTypes.get(key);
	}

	public HAPDataTypeInfo getDataTypeInfoByTypeName(String type){
		for(String categary : this.getAllDataCategarys()){
			if(this.isCategary(categary, type)){
				return new HAPDataTypeInfo(categary, type);
			}
		}
		return null;
	}
	
	public boolean isCategary(String categary, String type){
		return this.getDataType(new HAPDataTypeInfo(categary, type))!=null;
	}
	
	public HAPData getDefaultValue(HAPDataTypeInfo dataTypeInfo){
		HAPData out = null;
		HAPDataType dataType = this.getDataType(dataTypeInfo);
		if(dataType!=null){
			out = dataType.getDefaultData();
		}
		return out;
	}

	public static HAPDataTypeInfo getDefaultDataTypeInfo(){		return new HAPDataTypeInfo(DEFAULT_CATEGARY, DEFAULT_TYPE);	}
	
	private void loadBasicDataType(){
		
		HAPDataTypeInfoWithVersion booleanDataTypeInfo = new HAPDataTypeInfoWithVersion(HAPConstant.CONS_DATATYPE_CATEGARY_SIMPLE, HAPConstant.CONS_DATATYPE_TYPE_BOOLEAN);
		HAPDataTypeManager.BOOLEAN = (HAPBoolean)this.registerDataType(HAPBoolean.createDataType(booleanDataTypeInfo, null, null, null, "", this));

		HAPDataTypeInfoWithVersion integerDataTypeInfo = new HAPDataTypeInfoWithVersion(HAPConstant.CONS_DATATYPE_CATEGARY_SIMPLE, HAPConstant.CONS_DATATYPE_TYPE_INTEGER);
		HAPDataTypeManager.INTEGER = (HAPInteger)this.registerDataType(HAPInteger.createDataType(integerDataTypeInfo, null, null, null, "", this));

		HAPDataTypeInfoWithVersion stringDataTypeInfo = new HAPDataTypeInfoWithVersion(HAPConstant.CONS_DATATYPE_CATEGARY_SIMPLE, HAPConstant.CONS_DATATYPE_TYPE_STRING);
		HAPDataTypeManager.STRING = (HAPString)this.registerDataType(HAPString.createDataType(stringDataTypeInfo, null, null, null, "", this));
		
		
		HAPDataTypeInfoWithVersion floatDataTypeInfo = new HAPDataTypeInfoWithVersion(HAPConstant.CONS_DATATYPE_CATEGARY_SIMPLE, HAPConstant.CONS_DATATYPE_TYPE_FLOAT);
		HAPDataTypeManager.FLOAT = (HAPFloat)this.registerDataType(HAPFloat.createDataType(floatDataTypeInfo, null, null, null, "", this));
	}

	public HAPStringData getStringData(String value){
		return STRING.createDataByValue(value);
	}
	
	private String getDataTypeKey(HAPDataTypeInfo info){
		return info.toString();
	}

	/****************************** operation script ********************************/
	public Set<String> getSupportedScripts(){
		Set<String> out = new HashSet<String>();
		out.add(HAPConstant.CONS_OPERATIONDEF_SCRIPT_JAVASCRIPT);
		return out;
	}
	
	/*
	 * create data operation script for data type 
	 */
	private String buildDataTypeOperationScript(HAPDataType dataType){
		StringBuffer out = new StringBuffer();
		List<HAPDataType> dataTypes = this.getAllVersionsDataType(dataType);
		for(HAPDataType dataTypeWithVersion : dataTypes){
			String script = dataTypeWithVersion.buildLocalOperationScript(HAPConstant.CONS_OPERATIONDEF_SCRIPT_JAVASCRIPT);
			out.append(script);
		}
		return out.toString();
	}
	
	private String buildDataTypeOperationScriptFileName(HAPDataTypeInfo dataTypeInfo){
		return dataTypeInfo.toString()+".js";
	}
	
	/*
	 * store data type operation script of file and buffer
	 */
	private String processDataTypeOperationScript(HAPDataType dataType){
		HAPDataTypeInfo dataTypeInfo = dataType.getDataTypeInfo();
		
		String operationScript = this.buildDataTypeOperationScript(dataType);
		InputStream templateStream = HAPFileUtility.getInputStreamOnClassPath(HAPDataTypeImp.class, "DataTypeOperations.txt");
		Map<String, String> parms = new LinkedHashMap<String, String>();
		parms.put("dataTypeInfo", dataTypeInfo.toString());
		parms.put("dataTypeOperations", operationScript);
		String script = HAPStringTemplateUtil.getStringValue(templateStream, parms);
		
		
		//write the operation script to temp file
		String operationScriptFileName = HAPFileUtility.buildFullFileName(this.getTempFileLocation(), this.buildDataTypeOperationScriptFileName(dataTypeInfo));
		HAPFileUtility.writeFile(operationScriptFileName, script);
		
		//store operation script to buffer
		m_dataTypeOperationScripts.put(this.getDataTypeKey(dataTypeInfo), script);
		return operationScript;
	}
	

	/****************************** service ********************************/
	public String getDataTypeOperationScript(HAPDataTypeInfo dataTypeInfo){
		String out = this.m_dataTypeOperationScripts.get(this.getDataTypeKey(dataTypeInfo));
		if(out==null){
			out = this.processDataTypeOperationScript(this.getDataType(dataTypeInfo));
		}
		return out;
	}

	public String getRelatedDataTypeOperationScript(HAPDataTypeInfo dataTypeInfo){
		StringBuffer out = new StringBuffer();
		this.buildRelatedDataTypeOperationScript(dataTypeInfo, out);
		return out.toString();
	}

	private void buildRelatedDataTypeOperationScript(HAPDataTypeInfo dataTypeInfo, StringBuffer out){
		HAPDataType dataType = this.getDataType(dataTypeInfo);
		out.append(this.getDataTypeOperationScript(dataTypeInfo));
		
		HAPDataTypeInfo parentDataTypeInfo = dataType.getParentDataTypeInfo();
		if(parentDataTypeInfo!=null){
			this.buildRelatedDataTypeOperationScript(parentDataTypeInfo, out);
		}
	}

	
	public List<HAPDataType> getAllVersionsDataType(HAPDataTypeInfo dataTypeInfo){
		HAPDataType dataType = this.getDataType(dataTypeInfo);
		return this.getAllVersionsDataType(dataType);
	}

	private List<HAPDataType> getAllVersionsDataType(HAPDataType dataType){
		List<HAPDataType> out = new ArrayList<HAPDataType>();
		HAPDataType dataTypeTemp = dataType;
		while(dataTypeTemp!=null){
			out.add(dataTypeTemp);
			dataTypeTemp = dataTypeTemp.getOlderDataType();
		}
		return out;
	}
	
	public Map<String, List<HAPDataType>> getRelatedAllVersionsDataType(HAPDataTypeInfo dataTypeInfo){
		Map<String, List<HAPDataType>> out = new LinkedHashMap<String, List<HAPDataType>>();
		this.buildRelatedAllVersionsDataType(dataTypeInfo, out);
		return out;
	}
	
	private void buildRelatedAllVersionsDataType(HAPDataTypeInfo dataTypeInfo, Map<String, List<HAPDataType>> out){
		List<HAPDataType> dataType = this.getAllVersionsDataType(dataTypeInfo);
		out.put(dataTypeInfo.getKey(), dataType);
		
		HAPDataTypeInfo parentDataTypeInfo = dataType.get(0).getParentDataTypeInfo();
		if(parentDataTypeInfo!=null){
			this.buildRelatedAllVersionsDataType(parentDataTypeInfo, out);
		}
	}
	
	
	
	/****************************** configure ********************************/
	public HAPConfigurable getConfiguration(){return this.m_configures;}
	
	/*
	 * get temporate file location
	 */
	public String getTempFileLocation(){
		String scriptLocation = this.getConfiguration().getStringValue(HAPConstant.CONS_DATATYPEMAN_SETTINGNAME_SCRIPTLOCATION);
		return scriptLocation;
	}

	
	/****************************** transform between data object and json string ********************************/
	
	/*
	 * transform string to data object, according to the structure of the string, for instance: 
	 * 		json : start with { 
	 * 		otherwise, treat as simple text
	 */
	public HAPData parseString(String text, String categary, String type){
		if(text==null)  return null;
		if(text.equals(""))  return this.getStringData(text);
		
		String token = text.substring(0, 1);
		if(token.equals("{")){
			//json
			try {
				 JSONObject jsonObj = new JSONObject(text);
				 return this.parseJson(jsonObj, categary, type);
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}
		}
		else if(token.equals("#")){
			//literate
			return null;
		}
		else{
			if(categary!=null && type!=null){
				HAPDataType dataType = this.getDataType(new HAPDataTypeInfo(categary, type));
				return dataType.toData(text, HAPConstant.CONS_SERIALIZATION_TEXT);
			}
			else{
				//simple / text
				return this.getStringData(text);
			}
		}
	}

	public HAPData parseJson(JSONObject jsonObj, String categary, String type){
		if(HAPBasicUtility.isStringEmpty(categary)){
			categary = jsonObj.optJSONObject(HAPAttributeConstant.ATTR_DATA_DATATYPEINFO).optString(HAPAttributeConstant.ATTR_DATATYPEINFO_CATEGARY);
		}
		if(HAPBasicUtility.isStringEmpty(type)){
			type = jsonObj.optJSONObject(HAPAttributeConstant.ATTR_DATA_DATATYPEINFO).optString(HAPAttributeConstant.ATTR_DATATYPEINFO_TYPE);
		}
		Object valueObj = jsonObj.opt(HAPAttributeConstant.ATTR_DATA_VALUE);
		if(valueObj==null)  valueObj = jsonObj;
		return this.getDataType(new HAPDataTypeInfo(categary, type)).toData(valueObj, HAPConstant.CONS_SERIALIZATION_JSON);
	}

	public HAPWraper parseWraper(JSONObject jsonObj){
		return null;
	}

	@Override
	public String toStringValue(String format) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		
		Map<String, String> jsonTypeMap = new HashMap<String, String>();
		for(String key : this.m_dataTypes.keySet()){
			jsonTypeMap.put(key, this.m_dataTypes.get(key).toStringValue(format));
		}
		jsonMap.put("data", HAPJsonUtility.getMapJson(jsonTypeMap));
		
		return HAPJsonUtility.getMapJson(jsonMap);
	}

	@Override
	public String toString(){
		StringBuffer out = new StringBuffer();
		
		out.append("\n\n\n**************************     DataTypeManager  Start   *****************************\n");
		out.append(HAPJsonUtility.formatJson(this.toStringValue(HAPConstant.CONS_SERIALIZATION_JSON)));
		out.append("\n**************************     DataTypeManager  End   *****************************\n\n\n");
		
		return out.toString();
	}
}