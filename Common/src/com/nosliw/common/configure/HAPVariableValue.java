package com.nosliw.common.configure;

public class HAPVariableValue extends HAPResolvableConfigureItem{

	public HAPVariableValue(String rawString) {
		super(rawString);
	}

	@Override
	protected void setResolvedContent(String resolvedContent) {
		
	}

	@Override
	String getType() {
		return HAPConfigureItem.VARIABLE;
	}

	public HAPVariableValue clone(){
		HAPVariableValue out = new HAPVariableValue(null);
		out.cloneFrom(this);
		return out;
	}
}
