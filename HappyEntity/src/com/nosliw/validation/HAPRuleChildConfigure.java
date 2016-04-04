package com.nosliw.validation;

import com.nosliw.parm.HAPParms;

/*
 * interface for ruleconfigure children which defined the parameter that can used by parent rule
 */
public interface HAPRuleChildConfigure {

	/*
	 * the parms for parent rule
	 */
	public HAPParms getParentRuleParms();
}
