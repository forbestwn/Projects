package com.nosliw.entity.options;

import java.util.List;
import java.util.Map;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.HAPData;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.data.HAPWraper;
import com.nosliw.data.info.HAPDataTypeDefInfo;

/*
 * options that have dynamic options values
 * the options value depend on parms input Map<String, HAPData> 
 */
public abstract class HAPOptionsDefinitionDynamic extends HAPOptionsDefinition{

	public HAPOptionsDefinitionDynamic(String name, HAPDataTypeDefInfo dataTypeDefInfo, String desc, HAPDataTypeManager dataTypeMan) {
		super(name, dataTypeDefInfo, desc, dataTypeMan);
	}

	@Override
	public String getType(){return HAPConstant.CONS_OPTIONS_TYPE_DYNAMIC;}

	public abstract List<HAPWraper> getOptions(Map<String, HAPData> parms);
	
}
