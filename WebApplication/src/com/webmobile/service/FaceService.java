package com.webmobile.service;

import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

import com.adiak.datasource.ADKDataSource;
import com.adiak.ui.entity.ADKFaceEntity;
import com.adiak.ui.entity.ADKTemplateBodyEntity;
import com.adiak.ui.entity.ADKTemplateBorderEntity;
import com.adiak.ui.entity.ADKTemplateLayoutEntity;
import com.nosliw.data.HAPData;
import com.nosliw.data.HAPDataTypeManager;
import com.nosliw.entity.HAPEntityWraper;
import com.nosliw.entity.datadefinition.HAPDataTunnelEntity;
import com.nosliw.entity.datadefinition.HAPDataTypeEntity;
import com.nosliw.entity.datadefinition.data.HAPMapData;
import com.nosliw.exception.HAPServiceData;
import com.nosliw.util.HAPUtility;
import com.webmobile.manager.UserContextManager;
import com.webmobile.manager.UserContext;
import com.webmobile.util.PathUtility;
import com.webmobile.util.Utility;

public class FaceService extends UserContextManager{

	public FaceService(UserContext sysContext) {
		super(sysContext);
		sysContext.setFaceService(this);
	}

	private static final String m_templateTemp = new String("function(){||template_js|| \n  var html=\"||template_html||\"; return Template_Function(html);}()");
	
	
	public String getFaceInterface(String faceName, String inputdata){
		String out = this.readHtml();

		/*
		try{

		out = out.replace("||facename||", faceName);

		ADKFaceEntity faceEntity = this.getUserContext().getAppService().getFaceEntity(faceName);
		String faceJson = faceEntity.toStringValue(HAPDataTypeManager.FORMAT_JSON_SIMPLE);
		out = out.replace("||face||", faceJson);

		
		ADKTemplateBodyEntity bodyTemplateEntity = faceEntity.getBodyTemplateEntity();
		String bodyHtml = StringEscapeUtils.escapeHtml4(this.getUserContext().getAppService().getTemplateHtml(bodyTemplateEntity));
		String bodyJs = this.getUserContext().getAppService().getTemplateJS(bodyTemplateEntity);
		String bodyTemplate = m_templateTemp;
		bodyTemplate = bodyTemplate.replace("||template_html||", bodyHtml);
		bodyTemplate = bodyTemplate.replace("||template_js||", bodyJs);
		out = out.replace("||bodytemplate||", bodyTemplate);
				
		ADKTemplateLayoutEntity layoutTemplateEntity = faceEntity.getLayoutEntity();
		String layoutHtml = StringEscapeUtils.escapeHtml4(this.getUserContext().getAppService().getTemplateHtml(layoutTemplateEntity));
		String layoutJs = this.getUserContext().getAppService().getTemplateJS(layoutTemplateEntity);
		String layoutTemplate = m_templateTemp;
		layoutTemplate = layoutTemplate.replace("||template_html||", layoutHtml);
		layoutTemplate = layoutTemplate.replace("||template_js||", layoutJs);
		out = out.replace("||layouttemplate||", layoutTemplate);
		
		String borderTemolates = "";
		borderTemolates = borderTemolates + "{";
		Map<String, ADKTemplateBorderEntity> faceBorders = faceEntity.getFaceBorders();
		for(String borderName : faceBorders.keySet()){
			ADKTemplateBorderEntity borderTemplateEntity = faceBorders.get(borderName);
			String borderHtml = StringEscapeUtils.escapeHtml4(this.getUserContext().getAppService().getTemplateHtml(borderTemplateEntity));
			String borderJs = this.getUserContext().getAppService().getTemplateJS(borderTemplateEntity);
			String borderTemplate = borderName;
			borderTemplate = borderTemplate + ":" + m_templateTemp;
			borderTemplate = borderTemplate.replace("||template_html||", borderHtml);
			borderTemplate = borderTemplate.replace("||template_js||", borderJs);
			borderTemplate = borderTemplate + ",";
			borderTemolates = borderTemolates + borderTemplate;
		}
		borderTemolates = borderTemolates + "}";
		out = out.replace("||bordertemplates||", borderTemolates);
		
		
		StringBuffer dataSourceJson = new StringBuffer();
		dataSourceJson.append("{");
		ADKDataSource[] dataSources = faceEntity.getDataSources();

		if(inputdata==null)  inputdata="{}";
		JSONObject jsonDataObj = new JSONObject(inputdata);
		
		for(ADKDataSource dataSource : dataSources){
			HAPData inputData = null;
			try{
				inputData = this.getDataTypeManager().parseJson(jsonDataObj.getJSONObject(dataSource.getName()));
			}
			catch(Exception e){}
			HAPData data = this.getDataSourceEntity(dataSource.getID()).process(inputData);
			HAPDataTypeEntity outputDataDef = dataSource.getOutputDataDefinition();
			
			dataSourceJson.append("\""+outputDataDef.getID()+"\""+":"+data.toStringValue(HAPDataTypeManager.FORMAT_JSON)+",");
		}
		dataSourceJson.append("}");
		out = out.replace("||facedata||", dataSourceJson.toString());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		*/
		return out;
	}

	public ADKDataSource getDataSourceEntity(String id){
		/*
		HAPServiceData data = this.getEntityManager().getEntityByID(id);
		if(data.isSuccess()){
			HAPEntityWraper wraper = (HAPEntityWraper)data.getSuccessData();
			return (ADKDataSource)wraper.getEntityData();
		}
		*/
		return null;
	}

	private String readHtml(){
		return PathUtility.readHtmlFile(this.getUserContext());
	}
	
}

