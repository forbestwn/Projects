package com.nosliw.common.interpolate;

import com.nosliw.common.configure.HAPConfigureImp;
import com.nosliw.common.pattern.HAPPatternManager;

public class HAPInterpolateUtility {
	/*
	 * find all element bounded by start and end tokens, and process it by processor
	 */
	public static String process(String text, Object obj, String startToken, String endToken, HAPExpressionProcessor processor){
		int startTokenLen = startToken.length();
		int endTokenLen = endToken.length();
		
		String out = text;
		int start = 0;
		int end = -1;
		start = out.indexOf(startToken, start);
		while(start!=-1){
			end = out.indexOf(endToken, start);
			if(end==-1)  break;
			String expression = out.substring(start+startTokenLen, end);
			String varValue = processor.process(expression, obj);
			if(varValue==null){
				varValue = out.substring(start, end+endTokenLen);
			}
			out = out.substring(0, start) + varValue + out.substring(end+endTokenLen);
			start = out.indexOf(startToken, start+varValue.length()-1);
		}
		return out;
	}
	
	public static String interpolate(String text, HAPConfigureImp configure){
		return (String)HAPPatternManager.getInstance().getPatternProcessor(HAPPatternProcessorDocVariable.class.getName()).parse(text, configure.getGlobalVaribles());
	}
	
}
