package com.nosliw.common.pattern;

public abstract class HAPPatternProcessorImp implements HAPPatternProcessor{
	@Override
	public String getName(){
		return this.getClass().getName();
	}
}
