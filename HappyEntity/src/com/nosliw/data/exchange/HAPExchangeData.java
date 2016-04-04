package com.nosliw.data.exchange;

import java.util.Iterator;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataImp;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.application.core.data.HAPDataTypeExtend;
import com.nosliw.application.core.data.HAPDataTypeInfo;
import com.nosliw.application.core.data.HAPDataTypeManager;
import com.nosliw.application.core.data.HAPWraper;

public abstract class HAPExchangeData extends HAPDataImp{

	public HAPExchangeData(HAPDataType dataType) {
		super(dataType);
	}

	public abstract Iterator<HAPData> iterate();
	
	public abstract HAPData getElement(HAPDataElementPathInfo segInfo);

	public static HAPData getElement(HAPData data, HAPDataElementPathInfo segInfo){
		if(data instanceof HAPExchangeData){
			return ((HAPExchangeData) data).getElement(segInfo);
		}
		else{
			return data;
		}
	}
	
	public static HAPDataElementPathInfo getCurrentInfo(String path){
		HAPDataElementPathInfo info = new HAPDataElementPathInfo();
		
		int i = path.indexOf(":");
		if(i!=-1){
			String seg = path.substring(0, i);
			info.seg = seg;
			info.rest = path.substring(i+1);
		
			String[] parts = seg.split("\\?");
			info.info = parts[0];
			info.type = parts[1];
		}
		else{
			info.seg = path;
			info.rest = "";
		
			String[] parts = path.split("\\?");
			info.info = parts[0];
			info.type = parts[1];
			
			String[] sub = info.type.split("-");
			if(sub.length>2){
				info.categary = sub[0];
				info.type = sub[1];
			}
		}
		return info;
	}

}
