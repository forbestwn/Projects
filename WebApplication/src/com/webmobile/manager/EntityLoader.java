package com.webmobile.manager;

import java.util.Iterator;

import com.nosliw.HAPUserContext;
import com.nosliw.entity.HAPContainerWraper;
import com.nosliw.entity.HAPEntityData;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.entity.HAPDataWraper;
import com.nosliw.entity.datadefinition.HAPDataTypeEntity;
import com.nosliw.exception.HAPServiceData;
import com.nosliw.transaction.HAPEntityOperation;
import com.nosliw.util.HAPUtility;
import com.nosliw.xmlimp.entity.HAPXMLEntityLoader;

public class EntityLoader extends HAPXMLEntityLoader{

	public EntityLoader(HAPUserContext userContext) {
		super(userContext);
	}

	@Override
	public void afterLoad()
	{
		super.afterLoad();
//		this.setAllDataTypePath();
	}
	
	
	protected void setAllDataTypePath(){
//		String[] groups = this.getGroups();
//		for(String group : groups){
//			HAPServiceData data = this.getEntitiesByGroup(group);
//			if(data.isSuccess()){
//				HAPEntityWraper[] wrapers = (HAPEntityWraper[])data.getSuccessData();
//				for(HAPEntityWraper wraper : wrapers){
//					this.setDataTypePath(wraper);
//				}
//			}
//		}
	}
	
	private void setDataTypePath(HAPEntityWraper wraper){
//		if(wraper.isEmpty())  return;
//		
//		HAPEntityData entity = wraper.getEntityData();
//		if(wraper.getDataType().getType().equals("common.datatype")){
//			((HAPDataTypeEntity)entity).createPath("", wraper.getID());
//		}
//		else{
//			String[] attributes = entity.getAttributes();
//			for(String attr : attributes){
//				HAPDataWraper attrWraper = entity.getAttributeValueWraper(attr);
//				
//				if(HAPUtility.isContainerType(attrWraper.getDataType())){
//					HAPContainerWraper containerWraper = (HAPContainerWraper)attrWraper;
//					Iterator<HAPDataWraper> it = containerWraper.iterate();
//					while(it.hasNext()){
//						HAPDataWraper eleWraper = it.next();
//						if(HAPUtility.isEntityType(eleWraper.getDataType())){
//							if(eleWraper instanceof HAPEntityWraper){
//								this.setDataTypePath((HAPEntityWraper)eleWraper);
//							}
//						}
//					}
//				}
//				else if(HAPUtility.isEntityType(attrWraper.getDataType())){
//					if(attrWraper instanceof HAPEntityWraper){
//						this.setDataTypePath((HAPEntityWraper)attrWraper);
//					}
//				}
//			}
//		}
	}

	@Override
	protected String getFileNameByEntity(HAPEntityWraper entityWraper) {
		// TODO Auto-generated method stub
		return null;
	}

}
