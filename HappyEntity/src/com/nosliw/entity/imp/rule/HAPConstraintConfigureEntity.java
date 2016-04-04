package com.nosliw.entity.imp.rule;

import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.entity.data.HAPEntityData;
import com.nosliw.entity.imp.parm.HAPParmsEntity;
import com.nosliw.parm.HAPParms;
import com.nosliw.validation.HAPConstraintConfigure;

public class HAPConstraintConfigureEntity extends HAPEntityData implements HAPConstraintConfigure{

	@Override
	public String getName() {
		return null;
	}

	@Override
	public HAPParms getParms() {
		return null;
	}

	@Override
	public HAPParms getParentRuleParms() {
		return null;
	}

	
	public HAPServiceData setParm(String name, String value) {
		return null;
	}

	public HAPServiceData setRuleParm(String name, String value) {
		return null;
	}
}
