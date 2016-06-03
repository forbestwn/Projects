package com.nosliw.common.test;

import com.nosliw.common.utils.HAPConstant;

public class HAPResultTestCase extends HAPResult{

	private Exception m_e;
	
	public HAPResultTestCase(HAPTestDescription testDesc) {
		super(testDesc);
	}

	public void addException(Exception e){	this.setFail();	}
	
	public Exception getException(){  return m_e; }
	
	public void addIsScuss(boolean b){
		if(!b)  this.setFail();  
	}
	
	public void addResult(HAPResultTestCase result){
		if(!result.isSuccess())  this.setFail();  
	}

	@Override
	public String getType() {
		return HAPConstant.CONS_TESTRESULT_TYPE_CASE;
	}
	
}
