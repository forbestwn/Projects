package com.nosliw.entity.imp.datadefinition;

import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.entity.data.HAPEntityData;
import com.nosliw.entity.data.HAPEntityWraper;
import com.nosliw.entity.data.HAPReferenceWraper;
import com.nosliw.entity.definition.HAPEntityDefinitionBasic;
import com.nosliw.entity.utils.HAPEntityUtility;

public class HAPDataTunnelEndPointEntity extends HAPEntityData{

	public HAPDataTunnelEndPointEntity(HAPDataType dataType, HAPEntityDefinitionBasic entityInfo){
		super(dataType, entityInfo);
	}

	public String getEndPointID(){
		String out = "";
		String type = this.getChildPathWraper("type").getData().toString();
		if("refport".equals(type)){
			
			HAPReferenceWraper portRefWraper = (HAPReferenceWraper)this.getChildPathWraper("point");
			out = out + portRefWraper.getIDData().toString();
			
//			HAPReferenceWraper parentRefWraper = (HAPReferenceWraper)this.getChildPathWraper("point.parent");
//			String path = this.getChildPathWraper("point.path").getData().toString();
//			if(HAPUtility.isStringEmpty(path)){
//				path = "";
//			}
//			else{
//				path = "."+path;
//			}
//			out = out + parentRefWraper.getIDData().toString() + path;
		}
		else if("ref".equals(type)){
			HAPReferenceWraper endPointRefWraper = (HAPReferenceWraper)this.getChildPathWraper("point");
			
			String pathData = endPointRefWraper.getPathData();
			
			HAPEntityWraper endPointWraper = endPointRefWraper.getReferenceWraper();
			HAPDataTunnelEndPointEntity entPoinEntity = (HAPDataTunnelEndPointEntity)endPointWraper.getData();
			out = entPoinEntity.getEndPointID();
		}
		else if("valueport".equals(type)){
			out = this.getChildPathWraper("pointId").getData().toString();
		}
		else if("uipath".equals(type)){
			out = this.getChildPathWraper("point").getData().toString();
		}
		
		return out;
	}
	
}
