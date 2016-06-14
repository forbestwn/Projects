package com.nosliw.common.test;

import com.nosliw.common.document.HAPDocumentEntity;

/*
 * information about test item : 
 * 		what is input
 * 		what is expect output
 * so that it can be used during log
 */
public abstract class HAPTestItemDescription extends HAPDocumentEntity{
	private final static String ATTR_INPUT = "input";
	private final static String ATTR_OUTPUT = "output";

	//helper method to deal with predefined input and out attribute
	protected void setInput(Object input){ this.setValue(ATTR_INPUT, input);}
	protected Object getInput(){  return this.getValue(ATTR_INPUT); }
	protected String getInputStr(){  return this.getStringValue(ATTR_INPUT); }  
	
	protected void setOutput(Object output){  this.setValue(ATTR_OUTPUT, output); }
	protected Object getOutput(){  return this.getValue(ATTR_OUTPUT); }
	protected String getOputputStr(){ return this.getStringValue(ATTR_OUTPUT); }
	
	//log document for test item
	public abstract String log();

	//run the test for test item
	public void test(HAPResultTestCase testResult, HAPTestEnv testEnv){
		//update variable placeholder
		testEnv.updateDocument(this);
		//create empty test case result to collect all the result (isSuccess, exception)
		HAPResultTestCase temp = new HAPResultTestCase(null);
		try{
			this.doTest(temp, testEnv);
		}
		catch(Exception e){
			e.printStackTrace();
			temp.addException(e);
		}
		temp.addTestItem(new HAPTestItem(this, temp.isSuccess()));
		testResult.importResult(temp);
	}

	abstract public void doTest(HAPResultTestCase testResult, HAPTestEnv testEnv);
}
