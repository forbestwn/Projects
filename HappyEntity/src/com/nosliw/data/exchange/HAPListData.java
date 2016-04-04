package com.nosliw.data.exchange;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataImp;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.application.utils.HAPJsonUtility;

public class HAPListData extends HAPExchangeData{

	List<HAPData> m_data;
	
	public HAPListData() {
		super(HAPList.dataType);
		this.m_data = new ArrayList<HAPData>();
	}

	@Override
	public Iterator<HAPData> iterate(){
		return this.m_data.iterator();
	}
	
	public List<HAPData> getAllData(){return this.m_data;}

	public void add(HAPData data){
		this.m_data.add(data);
	}
	
	@Override
	public HAPData getElement(HAPDataElementPathInfo segInfo){
		HAPDataElementPathInfo subSegInfo = HAPExchangeData.getCurrentInfo(segInfo.rest);
		Iterator<HAPData> it = this.iterate();
		HAPListData outList = new HAPListData();
		while(it.hasNext()){
			outList.add(HAPExchangeData.getElement(it.next(), subSegInfo));
		}
		return outList;
	}

	
	@Override
	public HAPData cloneData() {
		return null;
	}

	@Override
	public String toDataStringValue(String format) {
		if(format.equals(HAPConstant.FORMAT_JSON)){
			List<String> jsonArray = new ArrayList<String>();
			for(HAPData data : this.m_data){
				jsonArray.add(data.toStringValue(format));
			}
			return HAPJsonUtility.getArrayJson(jsonArray.toArray(new String[0]));
		}
		return null;
	}

}
