package com.nosliw.validation;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.parm.HAPParmDefinition;

/*
 * Attribute Constraint: used based on single attribute value
 * normally applied when user commit the complex entity
 */
public interface HAPConstraint {

	/*
	 * constraint name, must be unique
	 */
	public String getName();

	/*
	 * description of the constait
	 */
	public String getDescription();
	
	/*
	 * a piece of constraint applied to value
	 * value : data to be validated
	 * configure : configuration applied to validation
	 */
	public HAPServiceData check(HAPData value, HAPConstraintConfigure configure);
	
	/*
	 * if this constraint can be applied to certain data type
	 */
	public boolean isAppliable(HAPDataType dataType);
	
	/*
	 * parameter definition for this constaint
	 */
	public HAPParmDefinition[] getParmDefinitions(); 
}
