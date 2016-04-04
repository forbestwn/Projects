package com.nosliw.validation;

/*
 * the interface for define the rule and its configure that can be applied to 
 * 		entity attribute definition
 * 		parmater definition 
 * 		data definition
 * rule define the relationship among its child rules and child constrain
 * a rule configure can be a child rule which  
 * the order of child rules and constrains matters
 */

public interface HAPRuleConfigure extends HAPRuleChildConfigure{
	
	//name of predefined rule
	public String getName();
	
	//child rule and constrains, order matters
	public HAPRuleChildConfigure[] getChildren();

	public String toStringValue(String format);

	public HAPRuleConfigure cloneConfigure();
	
}
