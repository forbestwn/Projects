package com.nosliw.entity.data;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

import com.nosliw.common.serialization.HAPStringable;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.entity.utils.HAPEntityUtility;

public class HAPReferencePath implements HAPStringable{

	private HAPEntityID m_entityID;
	
	private String m_attrPath;
	
	public HAPReferencePath(HAPEntityID entityId, String attrPath){
		this.m_attrPath = attrPath;
		this.m_entityID = entityId;
	}
	
	public HAPEntityID getEntityID(){
		return this.m_entityID;
	}
	
	public String getAttrPath(){
		return this.m_attrPath;
	}
	
	public String toStringValue(){
		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
		
		
		String out = HAPJsonUtility.getMapJson(jsonMap);
		return out;
	}
	
	@Override
	public boolean equals(Object o){
		if(o==null)  return false;
		
		if(o instanceof HAPReferencePath){
			HAPReferencePath path = (HAPReferencePath)o;
			if(HAPBasicUtility.isEquals(path.m_attrPath, this.m_attrPath) && HAPBasicUtility.isEquals(path.m_entityID, this.m_entityID)){
				return true;
			}
			else{
				return false;
			}
		}
		else return false;
	}

	@Override
	public String toStringValue(String format) {
		return null;
	}

	public static HAPReferencePath parseJson(JSONObject jsonEntityID){
		return null;
	}
}