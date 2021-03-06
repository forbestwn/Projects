package com.nosliw.common.test;

import java.lang.reflect.Method;
import java.util.List;

/*
 * this class store all the information execution related with test case
 */
public class HAPTestCaseRuntime {
	private Class m_testCaseClass;
	
	private List<Method> m_testCaseBeforeMethods;
	
	private List<Method> m_testCaseAfterMethods;
	
	private Method m_testCaseMethod;
	
	public HAPTestCaseRuntime(Class testCaseClass, Method testCaseMethod, List<Method> testCaseBeforeMethods, List<Method> testCaseAfterMethods){
		this.m_testCaseClass = testCaseClass;
		this.m_testCaseMethod = testCaseMethod;
		this.m_testCaseBeforeMethods = testCaseBeforeMethods;
		this.m_testCaseAfterMethods = testCaseAfterMethods;
	}

	public String getMethodName(){
		return this.m_testCaseMethod.getName();
	}
	
	public Object getBaseObject(HAPTestEnv testEnv){
		Object out = null;
		try{
			out = this.m_testCaseClass.newInstance();
			if(this.m_testCaseBeforeMethods!=null){
				for(Method method : this.m_testCaseBeforeMethods){
					method.invoke(out, testEnv);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		testEnv.setBaseObject(out);
		return out;
	}
	
	public HAPResultTestCase run(HAPResultTestCase result, HAPTestEnv testEnv){
		try{
			Object object = this.getBaseObject(testEnv); 
			if(object!=null){
				if(this.m_testCaseAfterMethods!=null){
					for(Method method : this.m_testCaseAfterMethods){
						method.invoke(object, testEnv);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			result.addException(e);
		}
		return result;
	}
}
