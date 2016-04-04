package com.nosliw.data;

import java.util.Map;
import java.util.Set;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.common.serialization.HAPStringable;
import com.nosliw.data.info.HAPDataOperationInfo;
import com.nosliw.data.info.HAPDataTypeInfo;
import com.nosliw.data.info.HAPDataTypeInfoWithVersion;

/*
 * The interface for data type in Nosliw
 * in Nosliw, everything is data, 
 * user can implements this interface to defined their own type of data
 * for instance, text, url, image, boolean, event entity
 * when define a new data type, user need define:
 * 		1. how to create new data
 * 		2. the available operations for this type (create a data instance is also an operation)
 * 		3. the default data 
 * 		4. how to validate the value of data
 * 		5. serialize and deserialize 
 * 		6. for some data type, it has limited data, for instance, gender, country, week day, ect
 * 		7. version number. data type may change with new requirement, version number provide this information so that other part may adjust according to the value
 * 		8. parent data type. all data type can define parent data type from which data type can inherent operation from parent
 * varsion number has to be back-compatible
 * version number may be used in following situation:
 * 		1. before doing data operation, check if data meet the version number required by operation, if not, try to upgrade the data to reqired version
 * 		2. when ui is binded with data, it should set the data version when set the data
 */

public interface HAPDataType extends HAPStringable{

	/*
	 * get basic information for this data type (categary, type, description)
	 */
	public HAPDataTypeInfoWithVersion getDataTypeInfo();

	public String getDescription();
	
	/*
	 * get default value this data type
	 * null : no default type
	 */
	public HAPData getDefaultData();

	/*
	 * get parent data type info
	 */
	public HAPDataTypeInfo getParentDataTypeInfo();

	/*
	 * get older/newer data type
	 * all data type with different version form a chain
	 * through these methods, we know the next node on the chain, 
	 */
	public HAPDataType getOlderDataType();
	public HAPDataType getNewerDataType();
	
	/*
	 * some data type has limited set of data
	 * unll or empty array: unlimted domain data
	 */
	public HAPData[] getDomainDatas();
	
	//operation info
	/*
	 * get all available operations info (local, older version, parent)
	 */
	public Map<String, HAPDataOperationInfo> getOperationInfos();
	public HAPDataOperationInfo getOperationInfoByName(String name);
	/*
	 * get constructor (newData) operations
	 */
	public Set<HAPDataOperationInfo> getNewDataOperations();
	
	//for java, run operate
	public HAPServiceData operate(String operation, HAPData[] data);
	//create a new data instance 
	public HAPServiceData newData(HAPData[] data);

	//for script language other than java, return operation script
	public String getOperateScript(String operation, String format);
	public String buildLocalOperationScript(String scriptName);
	//get dependent data type for operation
	public Set<HAPDataTypeInfo> getOperationDependentDataTypes(String operation);
	
	/*
	 * transform object (json, dom, string) to data object
	 */
	public HAPData toData(Object value, String format);
	public String toDataStringValue(HAPData data, String format);

	/*
	 * validate the data
	 * nothing to do with rule, but the data itself
	 * just check if the data is in good structure, good data range
	 */
	public HAPServiceData validate(HAPData data);
}