package com.nosliw.options.imp;

import java.util.Map;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataTypeDefInfo;
import com.nosliw.application.core.data.HAPDataTypeManager;
import com.nosliw.application.core.data.HAPWraper;
import com.nosliw.application.core.data.simple.text.HAPText;
import com.nosliw.application.core.data.simple.text.HAPTextData;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.entity.data.HAPDataWraper;
import com.nosliw.entity.options.HAPOptionsDefinition;

public class HAPOptionsDefinitionCity extends HAPOptionsDefinition{

	public HAPOptionsDefinitionCity(HAPDataTypeManager dataTypeMan) {
		super("city", new HAPDataTypeDefInfo(HAPConstant.CATEGARY_SIMPLE, HAPConstant.DATATYPE_SIMPLE_TEXT), "", dataTypeMan);
	}

	@Override
	public HAPWraper[] getOptions(Map<String, HAPData> parms, Map<String, HAPDataWraper> wraperParms) {
		HAPTextData provinceData = (HAPTextData)parms.get("province");
		HAPText text = (HAPText)this.getDataTypeManager().getDataType(this.getDataTypeInfo());
		String province = provinceData.getText();
		if("Ontario".equals(province)){
			return new HAPWraper[]{
					new HAPWraper(text.createTextData("Toronto"), this.getDataTypeManager()),
					new HAPWraper(text.createTextData("Markham"), this.getDataTypeManager()),
					new HAPWraper(text.createTextData("Ottowa"), this.getDataTypeManager()),
			};
		}
		else if("BC".equals(province)){
			return new HAPWraper[]{
					new HAPWraper(text.createTextData("Vancouver"), this.getDataTypeManager()),
					new HAPWraper(text.createTextData("Victoria"), this.getDataTypeManager()),
			};
		}
		else if("Alberta".equals(province)){
			return new HAPWraper[]{
					new HAPWraper(text.createTextData("Calgary"), this.getDataTypeManager()),
					new HAPWraper(text.createTextData("Edmonton"), this.getDataTypeManager()),
			};
		}
		
		return new HAPWraper[0];
	}
}
