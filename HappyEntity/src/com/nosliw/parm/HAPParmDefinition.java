package com.nosliw.parm;

import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.validation.HAPRuleConfigure;

/*
 * this interface is used to define parameter, including:
 * 		parameter name
 * 		parameter data type
 * 		parameter default value
 * 		parameter options value / rule, if options is defined, then rule configure is ignored
 */

public interface HAPParmDefinition {

	public String getName();
	
	public String getDescription();

	public HAPDataType getDataType();

	public HAPRuleConfigure getRule();

	public HAPServiceData validateParmValue(HAPParm parm);
	
//	public HAPOptionsConfigure getOptionsConfigure();

}
