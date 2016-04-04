package com.webmobile.service;

import com.adiak.datasource.ADKDataSource;
import com.adiak.ui.entity.ADKFaceEntity;
import com.adiak.ui.entity.ADKTemplateEntity;
import com.nosliw.HAPApplicationManager;
import com.nosliw.HAPUserContext;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.exception.HAPServiceData;
import com.webmobile.manager.UserContextManager;
import com.webmobile.manager.UserContext;
import com.webmobile.util.PathUtility;
import com.webmobile.util.Utility;

public class AppService extends UserContextManager{

	public AppService(UserContext sysContext) {
		super(sysContext);
		sysContext.setAppService(this);
	}

	public String getTemplateHtml(ADKTemplateEntity template){
		return PathUtility.readTemplateHtml(template, (UserContext)this.getUserContext());
	}

	public String getTemplateJS(ADKTemplateEntity template){
		return PathUtility.readTemplateJs(template, (UserContext)this.getUserContext());
	}
	
	public ADKFaceEntity getFaceEntity(String name){
//		HAPServiceData data = getUserContext().getEntityManager().getEntityWraperByAttributeValue("ui.face", "name", name);
//		if(data.isSuccess())  return (ADKFaceEntity)((HAPEntityWraper)data.getSuccessData()).getEntityData();
		return null;
	}
	
}
