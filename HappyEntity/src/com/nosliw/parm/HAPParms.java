package com.nosliw.parm;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.entity.imp.parm.HAPParmDefinitionEntity;
import com.nosliw.entity.imp.parm.HAPParmEntity;

/*
 * parms container, 
 * some parms follow Parm Definition
 * other parms have no definition, so we can add or set any value we like
 */

public interface HAPParms {

	public String[] getParmNames();
	
	public HAPData getValue(String parm);
	
	public void addParmDefinition(HAPParmDefinition parmDef);
	
	public HAPParmDefinitionEntity getParmDefinition(String parm);
	
	public HAPServiceData setParm(String parm, HAPData value);
	
	public HAPServiceData setParm(HAPParmEntity parm);
}
