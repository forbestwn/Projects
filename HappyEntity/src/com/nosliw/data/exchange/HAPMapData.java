package com.nosliw.data.exchange;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataImp;
import com.nosliw.application.core.data.simple.image.HAPImage;
import com.nosliw.application.core.data.simple.image.HAPImageData;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.application.utils.HAPJsonUtility;

public class HAPMapData extends HAPExchangeData{

	Map<String, HAPData> m_data;
	
	public HAPMapData() {
		super(HAPMap.dataType);
		this.m_data = new LinkedHashMap<String, HAPData>();
	}

	public void put(String name, HAPData data){
		this.m_data.put(name, data);
	}

	public Set<String> keySet(){
		return this.m_data.keySet();
	}
	
	@Override
	public Iterator<HAPData> iterate(){
		return this.m_data.values().iterator();
	}
	
	public HAPData get(String name){
		return this.m_data.get(name);
	}

	public static HAPMapData toMapData(Object value, String format, HAPMap mapDataType){
		HAPMapData out = new HAPMapData();
		
		if(HAPConstant.FORMAT_JSON.equals(format)){
			JSONObject jsonValue = (JSONObject)value;
			Iterator it = jsonValue.keys();
			while(it.hasNext()){
				String key = (String)it.next();
				JSONObject keyValue = jsonValue.optJSONObject(key);
				HAPData keyData = mapDataType.getDataTypeManager().parseJson(keyValue, null, null);
				out.put(key, keyData);
			}
		}
		return out;
	}

	@Override
	public HAPData getElement(HAPDataElementPathInfo segInfo){
		HAPDataElementPathInfo subSegInfo = HAPExchangeData.getCurrentInfo(segInfo.rest);
		HAPData subData = this.get(subSegInfo.info);
		return HAPExchangeData.getElement(subData, subSegInfo);
	}
	
	@Override
	public HAPData cloneData() {
		return null;
	}

	@Override
	public String toDataStringValue(String format) {
		if(format.equals(HAPConstant.FORMAT_JSON)){
			Map<String, String> mapJson = new LinkedHashMap<String, String>();
			for(String key : this.m_data.keySet()){
				mapJson.put(key, this.m_data.get(key).toStringValue(format));
			}
			return HAPJsonUtility.getMapJson(mapJson);
		}
		return null;
	}
}
