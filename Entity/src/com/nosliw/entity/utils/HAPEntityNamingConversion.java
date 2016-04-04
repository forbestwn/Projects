package com.nosliw.entity.utils;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPNamingConversionUtility;

public class HAPEntityNamingConversion {

	public static String createAttributeWithCriticalFullName(String criticalValue, String attributeName){
		return HAPNamingConversionUtility.createKeyword(HAPConstant.CONS_ATTRIBUTE_PATH_CRITICAL)
				+HAPConstant.CONS_SEPERATOR_DETAIL
				+criticalValue
				+HAPConstant.CONS_SEPERATOR_DETAIL
				+attributeName;
	}
	
	public static String createContainerElementName(){
		return HAPNamingConversionUtility.createKeyword(HAPConstant.CONS_ATTRIBUTE_PATH_ELEMENT);
	}
	
	public static String getGroupName(String name){
		if(name.startsWith(HAPConstant.CONS_SYMBOL_GROUP)){
			return name.substring(1);
		}
		return null;
	}
}
