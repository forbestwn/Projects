package com.nosliw.entity.transaction;

import com.nosliw.common.configure.HAPConfigurable;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.entity.dataaccess.HAPEntityDataAccess;
import com.nosliw.entity.dataaccess.HAPDataContext;

public class HAPTransactionNormal extends HAPTransaction{

	public HAPTransactionNormal(HAPConfigurable configure, HAPEntityDataAccess access, HAPDataContext dataContext){
		super(configure, access, dataContext);
		this.setOperationScope(HAPConstant.CONS_ENTITYOPERATION_SCOPE_GLOBAL);
	}
	
}
