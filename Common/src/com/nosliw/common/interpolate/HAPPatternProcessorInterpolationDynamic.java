package com.nosliw.common.interpolate;

import com.nosliw.common.pattern.HAPPatternProcessorImp;

/*
 * process method call or attribute
 */
public abstract class HAPPatternProcessorInterpolationDynamic extends HAPPatternProcessorImp{

	private String m_startToken;
	private String m_endToken;
	
	public HAPPatternProcessorInterpolationDynamic(String startToken, String endToken){
		this.m_startToken = startToken;
		this.m_endToken = endToken;
	}
	
	@Override
	public Object parse(String text, Object data) {
		if(data==null)  return text;
		
		return HAPInterpolateUtility.process(text, data, this.m_startToken, this.m_endToken, new HAPExpressionProcessor(){
			@Override
			public String process(String expression, Object object) {
				String out = null;
				try {
					int index = expression.lastIndexOf("()");
					if(index!=-1){
						//method call
						String methodName = expression.substring(0, index);
						out = (String)object.getClass().getMethod(methodName).invoke(object);
					}
					else{
						//attribute
						String attrName = expression;
						out = (String)object.getClass().getField(attrName).get(object);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return out;
			}
		});
	}

	@Override
	public String compose(Object obj, Object data) {
		return null;
	}
}
