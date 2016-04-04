package com.nosliw.entity.imp.rule;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.entity.data.HAPEntityData;
import com.nosliw.entity.imp.parm.HAPParmDefinitionEntity;
import com.nosliw.parm.HAPParmDefinition;
import com.nosliw.validation.HAPRule;
import com.nosliw.validation.HAPRuleConfigure;

public class HAPRuleEntity  extends HAPEntityData implements HAPRule{

	@Override
	public String getName() {
		return null;
	}

	@Override
	public HAPServiceData check(HAPData value, HAPRuleConfigure configure) {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public HAPParmDefinition[] getParmDefinitions() {
		return null;
	}

}
