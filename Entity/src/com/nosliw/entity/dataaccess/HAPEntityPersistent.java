package com.nosliw.entity.dataaccess;

import com.nosliw.common.configure.HAPConfiguration;

public abstract class HAPEntityPersistent extends HAPEntityDataAccessImp{

	public HAPEntityPersistent(HAPConfiguration configure) {
		super(configure, null, null);
	}

}
