package com.nosliw.validation;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.parm.HAPParmDefinition;

/*
 * Rule applied by combining all the constraints
 */
public interface HAPRule {

	/*
	 * constraint name, must be unique
	 */
	public String getName();

	/*
	 * description of the constait
	 */
	public String getDescription();

	/*
	 * a piece of rule applied to value
	 * value : data to be validated
	 * configure : configuration applied to validation
	 */
	public HAPServiceData check(HAPData value, HAPRuleConfigure configure);

	/*
	 * parameter definition for this constaint
	 */
	public HAPParmDefinition[] getParmDefinitions(); 
}
