package com.nosliw.parm;

import com.nosliw.application.core.data.HAPData;


/*
 * parm interface, containing name and value pair
 * value is store in simple/text
 * their is no validation in parm entity, validation is done by parms which is the container of parm
 * parms entity does the validation according to the parm definition
 */

public interface HAPParm {

	public String getName();

	public HAPData getValue();

	public void setParm(String parm, String value);
}
