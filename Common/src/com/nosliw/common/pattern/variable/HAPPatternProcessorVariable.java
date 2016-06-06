package com.nosliw.common.pattern.variable;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.pattern.HAPPatternProcessor;
import com.nosliw.common.test.HAPAssert;
import com.nosliw.common.test.HAPResultTestCase;
import com.nosliw.common.test.HAPTestCase;
import com.nosliw.common.test.HAPTestEnv;
import com.nosliw.common.utils.HAPConstant;

public class HAPPatternProcessorVariable implements HAPPatternProcessor{

	@Override
	public String getName() {
		return HAPConstant.CONS_PATTERN_VARIABLE;
	}

	@Override
	public Object parse(String text, Object data) {
		Map<String, String> varValues = (Map<String, String>)data; 
		
		int startTokenLen = HAPConstant.CONS_SEPERATOR_VARSTART.length();
		int endTokenLen = HAPConstant.CONS_SEPERATOR_VAREND.length();
		
		String out = text;
		int start = 0;
		int end = -1;
		start = out.indexOf(HAPConstant.CONS_SEPERATOR_VARSTART, start);
		while(start!=-1){
			end = out.indexOf(HAPConstant.CONS_SEPERATOR_VAREND, start);
			if(end==-1)  break;
			String varName = out.substring(start+startTokenLen, end);
			String varValue = varValues.get(varName);
			if(varValue==null){
				varValue = out.substring(start, end+endTokenLen);
			}
			out = out.substring(0, start) + varValue + out.substring(end+endTokenLen);
			start = out.indexOf(HAPConstant.CONS_SEPERATOR_VARSTART, start+varValue.length()-1);
		}
		return out;
	}

	@Override
	public String compose(Object obj, Object data) {
		return null;
	}
	
	@HAPTestCase(description="PorcessVariable:  part1{{CONS_SEPERATOR_VAREND}}part2{{CONS_SEPERATOR_VARSTART}}var2{{CONS_SEPERATOR_VAREND}}part3, the variables within the text are replaced with variables values ")
	public void processVariable(HAPResultTestCase result, HAPTestEnv testEnv) {
		Map<String, String> varsValue = new LinkedHashMap<String, String>();
		varsValue.put("plus", "+");
		varsValue.put("deduct", "-");
		varsValue.put("equals", "=");
		
		String input;
		String output;
		
		input = "234{{plus}}123{{equals}}357";
		output = "234+123=357";
		this.test(input, output, varsValue, result);

		input = "{{minus}}234{{plus}}123{{equals}}357";
		output = "{{minus}}234+123=357";
		this.test(input, output, varsValue, result);

		input = "234{plus}}123{{equals}}357";
		output = "234{plus}}123=357";
		this.test(input, output, varsValue, result);

		input = "234{{plus}123{{equals}}357";
		output = "234{{plus}123{{equals}}357";
		this.test(input, output, varsValue, result);
	}
	
	private void test(String input, String output, Map<String, String> variableValues, HAPResultTestCase result){
		try{
			String out = (String)this.parse(input, variableValues);
			HAPAssert.assertEquals(output, out, result);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
