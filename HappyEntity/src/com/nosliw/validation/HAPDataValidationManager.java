package com.nosliw.validation;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.application.HAPApplicationContext;
import com.nosliw.application.HAPApplicationManager;
import com.nosliw.application.HAPUserContext;
import com.nosliw.application.core.data.HAPData;
import com.nosliw.common.exception.HAPServiceData;
import com.nosliw.entity.utils.HAPEntityUtility;
import com.nosliw.parm.HAPParmDefinition;

/*
 * manager class to manager all the rules and restraints
 *     register rule, constraint
 *     get rule, constraint by name
 *     validate data based on rule configure
 */

public class HAPDataValidationManager extends HAPApplicationManager{

	private Map<String, HAPRule> m_rules = null;
	private Map<String, HAPConstraint> m_constraints = null;
	
	public static final HAPRule RULE_NONE = new HAPRule(){
		public String getName() {return "NONE";}
		public String getDescription() {return null;}
		public HAPServiceData check(HAPData value, HAPRuleConfigure configure) {return HAPServiceData.createSuccessData();}
		public HAPParmDefinition[] getParmDefinitions() {return new HAPParmDefinition[0];}
	};
	
	
	public HAPDataValidationManager(HAPApplicationContext appContext) {
		super(appContext);
		this.m_rules = new LinkedHashMap<String, HAPRule>();
		this.m_constraints = new LinkedHashMap<String, HAPConstraint>();
		appContext.setDataValidationManager(this);
	}

	public void registerRule(HAPRule rule){
		this.m_rules.put(rule.getName(), rule);
	}
	
	public void registerConstraint(HAPConstraint constraint){
		this.m_constraints.put(constraint.getName(), constraint);
	}

	public HAPRule getRule(HAPRuleConfigure ruleConfigure){
		return this.getRule(ruleConfigure.getName());
	}
	
	public HAPRule getRule(String name){
		if(HAPEntityUtility.isStringEmpty(name))  return RULE_NONE;
		HAPRule rule = this.m_rules.get(name);
		if(rule==null)  return RULE_NONE;
		return rule;
	}
	
	public HAPConstraint getConstraint(String name){
		return this.m_constraints.get(name);
	}

	public HAPServiceData check(HAPData value, HAPRuleConfigure configure){
		HAPRule rule = this.getRule(configure);
		return rule.check(value, configure);
	}
	
}
