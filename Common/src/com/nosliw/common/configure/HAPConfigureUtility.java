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
	
	/*
	 * factory method to create configuration object from property file only
	 */
	public static HAPConfigureImp createConfigureFromPropertyFile(String file, Class pathClass){
		HAPConfigureImp out = HAPConfigureManager.getInstance().newConfigure();
		InputStream input = HAPFileUtility.getInputStreamOnClassPath(pathClass, file);
		if(input!=null)  out.importFromFile(input);
		return out;
	}
	
	/*
	 * soft merge configure with configure read from file
	 * this can be used for this situation: configure from file can be considered as default configure and configure as customerize configure
	 * therefore, customerized configure should override default configure 
	 * file: property file name
	 * pathClass: class package to looking for the file
	 * confgiure: configure base 
	 */
	public static HAPConfigureImp createConfigureFromFileWithBaseConfigure(String file, Class pathClass, HAPConfigureImp configure){
		//read from file
		HAPConfigureImp fileConfig = createConfigureFromPropertyFile(file, pathClass);
		return (HAPConfigureImp)configure.hardMerge(fileConfig, true);
	}

	/*
	 * factory method to create configuration object from property file
	 * file: property file name
	 * pathClass: class package to looking for the file
	 * base: whether to create base configure
	 */
	public HAPConfigureImp createConfigureFromFileWithBaseName(String file, Class pathClass){
		return createConfigureFromFileWithBaseConfigure(file, pathClass, HAPConfigureManager.getInstance().createConfigure());
	}
}
