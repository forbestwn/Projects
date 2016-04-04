package com.webmobile.service;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;

import com.nosliw.HAPUserContext;
import com.nosliw.data.HAPData;
import com.nosliw.entity.data.HAPEntityID;
import com.nosliw.entity.data.HAPEntityWraper;
import com.nosliw.entity.data.HAPReferenceWraper;
import com.nosliw.entity.imp.datadefinition.HAPChainProcessorEntity;
import com.nosliw.util.HAPConstant;
import com.nosliw.util.HAPJsonUtility;

public class NavigationService {

	public static String navigation(HAPEntityID faceStartID, Map<String, HAPData> commandData, HAPUserContext userContext) throws Exception {
		Map<String, String> outMapJson = new LinkedHashMap<String, String>();
		
		HAPEntityWraper faceStartWraper = userContext.getEntityWraperRequest(faceStartID, null);
		HAPEntityID  faceID= ((HAPReferenceWraper)faceStartWraper.getChildWraperByPath("face")).getIDData();
		HAPEntityWraper faceWraper= ((HAPReferenceWraper)faceStartWraper.getChildWraperByPath("face")).getReferenceWraper();
		HAPEntityWraper faceUnitWraper = (HAPEntityWraper)faceWraper.getChildWraperByPath("uiunit");
		
		HAPChainProcessorEntity chainProcessorEntity = (HAPChainProcessorEntity)faceStartWraper.getChildWraperByPath("chainprocessor").getData();
		Map<String, HAPData> faceInputDataMap = chainProcessorEntity.chainProcessor(commandData);		
		Map<String, String> dataSourceJson = new LinkedHashMap<String, String>();
		for(String key : faceInputDataMap.keySet()){
			HAPData d = faceInputDataMap.get(key);
			dataSourceJson.put(key, d.toStringValue(HAPConstant.FORMAT_JSON_DATATYPE));
		}
		outMapJson.put("data", HAPJsonUtility.getMapJson(dataSourceJson));

		Map<String, String> faceMapJson = new LinkedHashMap<String, String>();
		faceMapJson.put("faceID", faceWraper.getID().toString());
		faceMapJson.put("faceUnitID", faceUnitWraper.getID().toString());
		outMapJson.put("face", HAPJsonUtility.getMapJson(faceMapJson));
		
		return HAPJsonUtility.getMapJson(outMapJson);
	}
	
	public static String navigate(String bodyId, String commandId, Map<String, HAPData> commandData, HAPUserContext userContext) throws Exception {
		HAPEntityWraper navigateWraper = userContext.getEntityWraperRequest(new HAPEntityID(bodyId), null);
		HAPEntityWraper faceStartWraper = (HAPEntityWraper)navigateWraper.getChildWraperByPath("facestart");
		HAPReferenceWraper nextFaceRefWraper = (HAPReferenceWraper)faceStartWraper.getChildWraperByPath("face");
		String content = FaceService.readFace(faceStartWraper.getID().toString(), commandData, userContext.getApplicationContext());

		content = HAPJsonUtility.formatJson(content);
		System.out.println("**********************  FACE !!!!!  ************************");
		System.out.println(content);

	    StringBuffer out = new StringBuffer();
	    out.append("<div data-role=\"page\" id=\""+nextFaceRefWraper.getIDData().toString()+"\"/>\n");
	    out.append("<script>\n");
	    out.append("goNextFace1(eval("+content+"));\n");
	    out.append("");
	    out.append("</script>\n");
	    out.append("</div>\n");

	    return out.toString();
	}

}
