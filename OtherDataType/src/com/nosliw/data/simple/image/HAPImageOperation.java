package com.nosliw.data.simple.image;

import com.nosliw.application.core.data.HAPDataOperation;
import com.nosliw.application.core.data.HAPDataTypeManager;
import com.nosliw.data.simple.text.HAPText;

public class HAPImageOperation extends HAPDataOperation{

	private HAPImage m_imageDef;
	HAPImageOperation(HAPImage imageDef, HAPDataTypeManager dataTypeMan){
		super(dataTypeMan);
		this.m_imageDef = imageDef;
	}

}
