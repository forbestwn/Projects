package com.nosliw.entity.dataaccess;

import com.nosliw.common.configure.HAPConfigurable;

public abstract class HAPEntityPersistent extends HAPEntityDataAccessImp{

	public HAPEntityPersistent(HAPConfigurable configure) {
		super(configure, null, null);
	}

}
