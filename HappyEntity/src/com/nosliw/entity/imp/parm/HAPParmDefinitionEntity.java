package com.nosliw.entity.imp.parm;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.entity.data.HAPAtomWraper;
import com.nosliw.entity.data.HAPDataWraper;
import com.nosliw.entity.data.HAPEntityData;
import com.nosliw.entity.imp.datadefinition.HAPDataTypeEntity;
import com.nosliw.entity.utils.HAPEntityUtility;
import com.nosliw.validation.HAPRuleConfigure;

/*
 * this class is used to define parameter, including:
 * 		parameter name
 * 		parameter data type
 * 		parameter default value
 * 		parameter options value / rule, if options is defined, then rule configure is ignored
 */

public class HAPParmDefinitionEntity extends HAPEntityData{

	public final static String ATTRIBUTE_NAME = "name";
	public final static String ATTRIBUTE_DESCRIPTION = "description";
	public final static String ATTRIBUTE_DEFAULT = "defaultvalue";
	public final static String ATTRIBUTE_TYPE = "datatype";
	public final static String ATTRIBUTE_RULE = "rules:rule";

	public final static String ATTRIBUTE_OPTIONS = "options";
	public final static String ATTRIBUTE_OPTIONSNAME = "options:name";

	public String getName(){
//		return this.getAtomAttributeValue(ATTRIBUTE_NAME);
		return null;
	}
	public String getDescription(){	
//		return this.getAtomAttributeValue(ATTRIBUTE_DESCRIPTION);
		return null;
	}

	public HAPDataTypeEntity getDataTypeEntity(){return (HAPDataTypeEntity)this.getAttributeEntityValue(ATTRIBUTE_TYPE);}
	
	public HAPData getDefaultValue(){	
		HAPAtomWraper wraper = (HAPAtomWraper)this.getAttributeValueWraper(ATTRIBUTE_DEFAULT);
		return wraper.getData();
	}

	public HAPServiceData validateParmValue(HAPParmEntity parm){
		HAPServiceData out = HAPServiceData.createSuccessData();
		if(HAPEntityUtility.isWraperEmpty(this.getChildPathWraper(ATTRIBUTE_OPTIONS))){
			//if no options, use rule to validate
			out = this.getUserContext().getDataValidationManager().check(parm.getValue(), this.getRule());
		}
		else{
//			HAPOptionsEntity optionsEntity = (HAPOptionsEntity)this.getChildPathWraper(ATTRIBUTE_OPTIONS).getData();
//			boolean valid = this.getUserContext().getOptionsManager().isValidCommonValue(parm.getValue(), optionsEntity);
//			if(!valid) out = HAPServiceData.createFailureData();
		}
		return out;
	}
	
	
	public HAPRuleConfigure getRule(){
		HAPDataWraper wraper = this.getChildPathWraper(ATTRIBUTE_RULE);
		if(HAPEntityUtility.isWraperEmpty(wraper))  return null;
		return (HAPRuleConfigure)wraper.getData();
	}

	public String getOptionName(){
		HAPDataWraper wraper = this.getChildPathWraper(ATTRIBUTE_OPTIONSNAME);
		if(wraper==null)  return null;
		else return ((HAPAtomWraper)wraper).toString();
	}
}
