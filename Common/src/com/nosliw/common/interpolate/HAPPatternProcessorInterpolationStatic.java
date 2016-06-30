package com.nosliw.common.interpolate;

import com.nosliw.common.configure.HAPConfigureImp;
import com.nosliw.common.pattern.HAPPatternProcessorImp;

public abstract class HAPPatternProcessorInterpolationStatic extends HAPPatternProcessorImp{

	private String m_startToken;
	private String m_endToken;
	
	public HAPPatternProcessorInterpolationStatic(String startToken, String endToken){
		this.m_startToken = startToken;
		this.m_endToken = endToken;
	}
	
	@Override
	public Object parse(String text, Object data) {
		if(data==null)  return text;
		return HAPInterpolateUtility.process(text, data, this.m_startToken, this.m_endToken, new HAPExpressionProcessor(){
			@Override
			public String process(String expression, Object object) {
				HAPConfigureImp varValues = (HAPConfigureImp)object;
				String varValue = varValues.getStringValue(expression);
				return varValue;
			}
		});
	}

	@Override
	public String compose(Object obj, Object data) {
		return null;
	}
}
