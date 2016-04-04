package com.nosliw.data.exchange;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataImp;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.application.utils.HAPJsonUtility;

public class HAPTableColumnData extends HAPExchangeData{
	private String m_name;
	private List<HAPData> m_data;
	
	public HAPTableColumnData() {
		super(HAPTableColumn.dataType);
		this.m_data = new ArrayList<HAPData>();
	}

	public HAPTableColumnData(String name) {
		super(HAPTableColumn.dataType);
		this.m_data = new ArrayList<HAPData>();
		this.m_name = name;
	}

	public HAPTableColumnData(int size){
		super(HAPTableColumn.dataType);
		this.m_data = new ArrayList<HAPData>(size);
		for(int i=0; i<size; i++){
			this.m_data.add(null);
		}
	}

	@Override
	public Iterator<HAPData> iterate(){
		return this.m_data.iterator();
	}

	public int size(){return this.m_data.size();}
	
	public String getName(){return this.m_name;}
	public void setName(String name){this.m_name = name;}

	public List<HAPData> getAllData(){return this.m_data;}
	public void setData(List<HAPData> data){this.m_data = data;}
	public void appendData(HAPData data){this.m_data.add(data);}
	public HAPData getData(int index){return this.m_data.get(index);}

	public HAPData getElement(HAPDataElementPathInfo segInfo){
		return this;
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
