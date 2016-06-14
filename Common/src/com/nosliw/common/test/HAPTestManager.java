package com.nosliw.common.test;

import com.nosliw.common.configure.HAPConfigureImp;
import com.nosliw.common.configure.HAPConfigureManager;
import com.nosliw.common.utils.HAPConstant;

public class HAPTestManager {
	private static HAPTestManager m_instance;

	private static HAPConfigureImp m_configure;
	
	//id seed
	private int m_id;
	
	private HAPTestManager(){
		this.m_id = 0;
	}
	
	public static HAPTestManager getInstance(){
		if(m_instance==null){
			m_instance = new HAPTestManager();
			m_configure = HAPConfigureManager.getInstance().createConfigureWithBaseName(HAPConstant.CONS_CONFIGURATION_DEFAULTBASE);
		}
		return m_instance;
	}
	
	public int createId(){
		return this.m_id++;
	}
	
	public HAPTestEnv createTestEnv(){
		HAPTestEnv out = new HAPTestEnv(this.m_configure.getGlobalVaribles());
		return out;
	}
	
}
