package com.nosliw.entity.imp.rule;

import com.nosliw.entity.data.HAPEntityData;
import com.nosliw.parm.HAPParms;
import com.nosliw.validation.HAPConstraintConfigure;
import com.nosliw.validation.HAPRuleChildConfigure;
import com.nosliw.validation.HAPRuleConfigure;

public class HAPRuleConfigureEntity extends HAPEntityData implements HAPRuleConfigure{

	@Override
	public HAPParms getParentRuleParms() {
		return null;
	}

	@Override
	public HAPRuleChildConfigure[] getChildren() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	public void addConstraintConfg(HAPConstraintConfigure constraitn) {
	}

	public void addChildRule(HAPRuleConfigure rule) {
	}

	public HAPParms getParms() {
		return null;
	}

	@Override
	public HAPRuleConfigure cloneConfigure() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
