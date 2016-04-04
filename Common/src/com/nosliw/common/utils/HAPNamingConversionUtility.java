package com.nosliw.common.utils;

public class HAPNamingConversionUtility {

	/*
	 * cascade two part element together
	 */
	public static String cascadePart(String part1, String part2){
		return cascadeParts(part1, part2, HAPConstant.CONS_SEPERATOR_PART);
	}

	/*
	 * get all sub parts from full
	 */
	public static String[] parsePartlInfos(String parts){
		return parts.split(HAPConstant.CONS_SEPERATOR_PART);
	}
	
	/*
	 * cascade two detail element together
	 */
	public static String cascadeDetail(String detail1, String detail2){
		return cascadeParts(detail1, detail2, HAPConstant.CONS_SEPERATOR_DETAIL);
	}

	/*
	 * get all sub path from full path
	 */
	public static String[] parseDetailInfos(String details){
		return details.split(HAPConstant.CONS_SEPERATOR_DETAIL);
	}
	
	/*
	 * cascade two Path element together
	 */
	public static String cascadePath(String path1, String path2){
		return cascadeParts(path1, path2, HAPConstant.CONS_SEPERATOR_PATH);
	}

	/*
	 * get all sub path from full path
	 */
	public static String[] parsePathInfos(String fullPath){
		return fullPath.split(HAPConstant.CONS_SEPERATOR_PATH);
	}
	
	public static String cascadeParts(String part1, String part2, String seperator){
		if(HAPBasicUtility.isStringEmpty(part1))	return part2;
		else if(HAPBasicUtility.isStringEmpty(part2))	return part1;
		else{
			return new StringBuffer().append(part1).append(seperator).append(part2).toString();
		}
	}
	
	/*
	 * key word are always satart with "#", this character is the way to judge if it is a keyword
	 * if yes, then return the keyword part
	 * if not, then reutnr null;
	 */
	static public HAPSegmentParser isKeywordPhrase(String name, String seperator){
		if(name.subSequence(0, 1).equals(HAPConstant.CONS_SYMBOL_KEYWORD)){
			return new HAPSegmentParser(name.substring(1), seperator);
		}
		else  return null;
	}

	static public HAPSegmentParser isKeywordPhrase(String name){
		return isKeywordPhrase(name, HAPConstant.CONS_SEPERATOR_DETAIL);
	}

	static public String getKeyword(String keyword){
		return getKeyword(keyword, HAPConstant.CONS_SYMBOL_KEYWORD);
	}
	
	static public String createKeyword(String key){
		return createKeyword(key, HAPConstant.CONS_SYMBOL_KEYWORD);
	}

	/*
	 * key word are always start with some special characters
	 */
	static public String getKeyword(String keyword, String keywordSymbol){
		if(keyword.startsWith(keywordSymbol)){
			return keyword.substring(keywordSymbol.length());
		}
		return null;
	}
	
	/*
	 * build keyword format 
	 * if key already have the format, then just return key
	 */
	static public String createKeyword(String key, String keywordSymbol){
		if(key.contains(keywordSymbol))  return key;
		return keywordSymbol + key;
	}
	
	static public String buildPath(String path1, String path2){
		if(HAPBasicUtility.isStringEmpty(path1)){
			return path2;
		}
		else{
			return path1 + HAPConstant.CONS_SEPERATOR_PATH + path2;
		}
	}
	
	
}
