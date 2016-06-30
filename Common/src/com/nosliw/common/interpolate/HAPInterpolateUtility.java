package com.nosliw.common.interpolate;

import com.nosliw.common.configure.HAPConfigureImp;
import com.nosliw.common.pattern.HAPPatternManager;

public class HAPInterpolateUtility {
	/*
	 * find all element bounded by start and end tokens, and process it by processor
	 */
	public static HAPInterpolateOutput process(String text, Object obj, String startToken, String endToken, HAPExpressionProcessor processor){
		HAPInterpolateOutput out = new HAPInterpolateOutput();

		int startTokenLen = startToken.length();
		int endTokenLen = endToken.length();
		
		String output = text;
		int start = 0;
		int end = -1;
		start = output.indexOf(startToken, start);
		while(start!=-1){
			end = output.indexOf(endToken, start);
			if(end==-1)  break;
			String expression = output.substring(start+startTokenLen, end);
			String varValue = processor.process(expression, obj);
			if(varValue==null){
				//unsolved element
				varValue = output.substring(start, end+endTokenLen);
				out.addUnsolved(expression);
			}
			output = output.substring(0, start) + varValue + output.substring(end+endTokenLen);
			start = output.indexOf(startToken, start+varValue.length()-1);
		}
		out.setOutput(output);
		return out;
	}
	
	public static String interpolate(String text, HAPConfigureImp configure){
		HAPInterpolateOutput out = (HAPInterpolateOutput)HAPPatternManager.getInstance().getPatternProcessor(HAPPatternProcessorDocVariable.class.getName()).parse(text, configure);
		return out.getOutput();
	}
	
}
