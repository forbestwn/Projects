package com.nosliw.common.test;

import com.nosliw.common.document.HAPDocumentEntity;

/*
 * information about test item : 
 * 		what is input
 * 		what is expect output
 * so that it can be used during log
 */
public abstract class HAPTestItemDescription extends HAPDocumentEntity{

	public abstract String log();
	
}
