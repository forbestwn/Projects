package com.adiak.ui.entity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.adiak.datasource.ADKDataSource;
import com.nosliw.entity.HAPContainerWraper;
import com.nosliw.entity.HAPEntityData;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.entity.HAPDataWraper;
import com.nosliw.entity.datadefinition.HAPDataTunnelEntity;

public class ADKFaceEntity extends HAPEntityData{
	private static final String ATTRIBUTE_BODYTEMPLATE = "body:template";
	private static final String ATTRIBUTE_LAYOUT = "layout";
	private static final String ATTRIBUTE_LAYOUTTEMPLATE = "layout:template";
	private static final String ATTRIBUTE_LAYOUTBORDERS = "layout:borders";
	private static final String ATTRIBUTE_DATASOURCES = "datasources";
	private static final String ATTRIBUTE_DATATUNNELS = "datatunnels";
	
	public ADKTemplateBodyEntity getBodyTemplateEntity(){
		HAPEntityWraper wraper = (HAPEntityWraper)this.getChildPathWraper(ATTRIBUTE_BODYTEMPLATE);
		if(wraper==null)  return null;
		else{
			return (ADKTemplateBodyEntity)wraper.getEntityData();
		}
	}

	public ADKTemplateLayoutEntity getBodyLayoutEntity(){
		return (ADKTemplateLayoutEntity)this.getAttributeEntityValue(ATTRIBUTE_LAYOUT);
	}
	
	public ADKTemplateLayoutEntity getLayoutEntity(){
		HAPEntityWraper wraper = (HAPEntityWraper)this.getChildPathWraper(ATTRIBUTE_LAYOUTTEMPLATE);
		if(wraper==null)  return null;
		else{
			return (ADKTemplateLayoutEntity)wraper.getEntityData();
		}
	}
	
	public Map<String, ADKTemplateBorderEntity> getFaceBorders(){
		Map<String, ADKTemplateBorderEntity> out = new LinkedHashMap<String, ADKTemplateBorderEntity>();
		HAPContainerWraper borderEntities = (HAPContainerWraper)this.getChildPathWraper(ATTRIBUTE_LAYOUTBORDERS);
		
		Iterator<HAPDataWraper> it = borderEntities.iterate();
		while(it.hasNext()){
			HAPEntityData faceBorder = (HAPEntityData)it.next().getData();
			out.put(((ADKFaceBorderEntity)faceBorder).getName(), ((ADKFaceBorderEntity)faceBorder).getBorderTemplate());
		}
		return out;
	}
	
//	public ADKTemplateBorderEntity[] getLayoutBorderTemplates(){
//		List<ADKTemplateBorderEntity> out = new ArrayList<ADKTemplateBorderEntity>();
//		HAPMapValueWraper borderTemplates = (HAPMapValueWraper)this.getCombinedAttributeWraper(ATTRIBUTE_LAYOUTBORDERTEMPLATE);
//		HAPComplexEntity[] faceBorders = (HAPComplexEntity[])borderTemplates.getEntities();
//		for(HAPComplexEntity faceBorder : faceBorders){
//			out.add(((ADKFaceBorderEntity)faceBorder).getBorderTemplate());
//		}
//		return out.toArray(new ADKTemplateBorderEntity[0]);
//	}

	public ADKDataSource[] getDataSources(){
		HAPContainerWraper mapWraper = (HAPContainerWraper)this.getAttributeValueWraper(ATTRIBUTE_DATASOURCES);
		
		List<ADKDataSource> out = new ArrayList<ADKDataSource>();
		Iterator<HAPDataWraper> it = mapWraper.iterate();
		while(it.hasNext()){
			HAPEntityData entity = (HAPEntityData)it.next().getData();
			out.add((ADKDataSource)entity);
		}
		return out.toArray(new ADKDataSource[0]);
	}
	
	public HAPDataTunnelEntity[] getDataTunnels(){
//		HAPSetValueWraper setWraper = (HAPSetValueWraper)this.getAttributeValueWraper(ATTRIBUTE_DATATUNNELS);
//		HAPComplexEntity[] entitys = setWraper.get;
//		List<ADKDataSource> out = new ArrayList<ADKDataSource>();
//		for(HAPComplexEntity entity : entitys){
//			out.add((ADKDataSource)entity);
//		}
		return null;
	}
	
}
