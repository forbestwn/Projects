package com.nosliw.common.configure;

import java.io.InputStream;

import com.nosliw.common.utils.HAPFileUtility;

public class HAPConfigureUtility {

	private static String KEY_VARIABLE_GLOBAL = "global";
	
	public static String isGlobalVariable(String name){
		if(!name.startsWith(KEY_VARIABLE_GLOBAL)){
			return null;
		}
		else{
			return name.substring(KEY_VARIABLE_GLOBAL.length()+1);
		}
	}
}
