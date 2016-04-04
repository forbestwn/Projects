package com.nosliw.data.simple.time;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.application.core.data.HAPSimpleData;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.application.utils.HAPJsonUtility;

public class HAPTimeData extends HAPSimpleData{

	private int m_hour;
	private int m_minute;
	private int m_second;
	
	public HAPTimeData(int hour, int minute, int second, HAPDataType dataType){
		super(dataType);
		this.m_hour = hour;
		this.m_minute = minute;
		this.m_second = second;
	}

	@Override
	public HAPData cloneData() {
		return new HAPTimeData(this.m_hour, this.m_minute, this.m_second, this.getDataType());
	}

	@Override
	public String toDataStringValue(String format) {
		if(format.equals(HAPConstant.FORMAT_JSON)){
			Map<String, String> jsonMap = new LinkedHashMap<String, String>();
			jsonMap.put("hour", String.valueOf(this.m_hour));
			jsonMap.put("minute", String.valueOf(this.m_minute));
			jsonMap.put("second", String.valueOf(this.m_second));
			
			Map<String, Integer> jsonType = new LinkedHashMap<String, Integer>();
			jsonType.put("hour", HAPConstant.JSON_DATATYPE_INTEGER);
			jsonType.put("minute", HAPConstant.JSON_DATATYPE_INTEGER);
			jsonType.put("second", HAPConstant.JSON_DATATYPE_INTEGER);
			
			return HAPJsonUtility.getMapJson(jsonMap, jsonType);
		}
		return null;
	}
	
	public static HAPTimeData toTimeData(Object value, String format, HAPTime timeDataType){
		HAPTimeData out = null;
		
		if(HAPConstant.FORMAT_JSON.equals(format)){
			JSONObject jsonValue = (JSONObject)value;
			int hour = jsonValue.optInt("hour");
			int minute = jsonValue.optInt("minute");
			int second = jsonValue.optInt("second");
			out = new HAPTimeData(hour, minute, second, timeDataType);
		}
		return out;
	}
}
