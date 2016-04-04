package com.nosliw.options.imp;

import java.util.Map;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataTypeDefInfo;
import com.nosliw.application.core.data.HAPDataTypeManager;
import com.nosliw.application.core.data.HAPWraper;
import com.nosliw.application.core.data.simple.text.HAPText;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.entity.data.HAPDataWraper;
import com.nosliw.entity.options.HAPOptionsDefinition;

public class HAPOptionsDefinitionProvince extends HAPOptionsDefinition{

	public HAPOptionsDefinitionProvince(HAPDataTypeManager dataTypeMan) {
		super("province", new HAPDataTypeDefInfo(HAPConstant.CATEGARY_SIMPLE, HAPConstant.DATATYPE_SIMPLE_TEXT), "", dataTypeMan);
	}

	@Override
	public HAPWraper[] getOptions(Map<String, HAPData> parms, Map<String, HAPDataWraper> wraperParms) {
		HAPText text = (HAPText)this.getDataTypeManager().getDataType(this.getDataTypeInfo());
		return new HAPWraper[]{
				new HAPWraper(text.createTextData("Ontario"), this.getDataTypeManager()),
				new HAPWraper(text.createTextData("BC"), this.getDataTypeManager()),
				new HAPWraper(text.createTextData("Alberta"), this.getDataTypeManager()),
		};
	}

}
