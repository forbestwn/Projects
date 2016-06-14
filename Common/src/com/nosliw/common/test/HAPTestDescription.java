package com.nosliw.common.test;

import com.nosliw.common.document.HAPDocumentEntity;

public class HAPTestDescription extends HAPDocumentEntity{
	public final static String ATTR_NAME = "name";
	public final static String ATTR_DESCRIPTION = "description";

	public HAPTestDescription(String name, String description){
		this.setValue(ATTR_NAME, name);
		this.setValue(ATTR_DESCRIPTION, description);
	}

	public HAPTestDescription(HAPTestSuite testSuite){
		this(testSuite.name(), testSuite.description());
	}
	
	public HAPTestDescription(HAPTestCase testCase){
		this(testCase.name(), testCase.description());
	}
}
