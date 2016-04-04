package com.nosliw.data.simple.image;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.application.core.data.HAPSimpleData;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.application.utils.HAPJsonUtility;
import com.nosliw.data.simple.text.HAPTextData;
import com.nosliw.data.simple.time.HAPTime;
import com.nosliw.data.simple.time.HAPTimeData;

public class HAPImageData extends HAPSimpleData{

	private String m_url;
	
	HAPImageData(String url, HAPDataType dataType){
		super(dataType);
		this.m_url = url;
	}

	@Override
	public HAPData cloneData() {
		return new HAPImageData(this.m_url, this.getDataType());
	}

	@Override
	public String toDataStringValue(String format) {
		if(format.equals(HAPConstant.FORMAT_JSON)){
			Map<String, String> jsonMap = new LinkedHashMap<String, String>();
			jsonMap.put("url", String.valueOf(this.m_url));
			return HAPJsonUtility.getMapJson(jsonMap);
		}
		return null;
	}
	
	public static HAPImageData toImageData(Object value, String format, HAPImage imageDataType){
		HAPImageData out = null;
		
		if(HAPConstant.FORMAT_JSON.equals(format)){
			JSONObject jsonValue = (JSONObject)value;
			String url = jsonValue.optString("url");
			out = new HAPImageData(url, imageDataType);
		}
		return out;
	}
	
	public String getUrl(){ return this.m_url;}
	public void setUrl(String url){this.m_url = url;}
	
	@Override
	public String toString(){
		return this.m_url;
	}
}
