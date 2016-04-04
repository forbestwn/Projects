package com.nosliw.entity.imp.rule;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.entity.data.HAPEntityData;
import com.nosliw.parm.HAPParmDefinition;
import com.nosliw.validation.HAPConstraint;
import com.nosliw.validation.HAPConstraintConfigure;

public class HAPConstraintEntity extends HAPEntityData implements HAPConstraint{

	@Override
	public String getName() {
		return null;
	}

	@Override
	public HAPServiceData check(HAPData value, HAPConstraintConfigure configure) {
		return null;
	}

	@Override
	public HAPParmDefinition[] getParmDefinitions() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public boolean isAppliable(HAPDataType dataType) {
		return false;
	}

}
