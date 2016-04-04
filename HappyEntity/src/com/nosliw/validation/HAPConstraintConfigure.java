package com.nosliw.validation;

import com.nosliw.parm.HAPParms;

/*
 * this interface define the constraint configureation that can be applied to constrain
 * 
 * 
 */
public interface HAPConstraintConfigure extends HAPRuleChildConfigure{
	
	/*
	 * constrain name
	 */
	public String getName();
	
	/*
	 * the parms for constraint 
	 */
	public HAPParms getParms();
	
}
