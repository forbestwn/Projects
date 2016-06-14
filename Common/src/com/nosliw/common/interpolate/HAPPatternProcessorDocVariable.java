package com.nosliw.common.interpolate;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.test.HAPAssert;
import com.nosliw.common.test.HAPResultTestCase;
import com.nosliw.common.test.HAPTestCase;
import com.nosliw.common.test.HAPTestEnv;
import com.nosliw.common.utils.HAPConstant;

public class HAPPatternProcessorDocVariable extends HAPPatternProcessorInterpolationStatic{

	public HAPPatternProcessorDocVariable(){
		super(HAPConstant.CONS_SEPERATOR_VARSTART, HAPConstant.CONS_SEPERATOR_VAREND);
	}
	
	@HAPTestCase(description="PorcessVariable:  {{CONS_SEPERATOR_VARSTART}}part1{{CONS_SEPERATOR_VAREND}}part2{{CONS_SEPERATOR_VARSTART}}var2{{CONS_SEPERATOR_VAREND}}part3, the variables within the text are replaced with variables values ")
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
