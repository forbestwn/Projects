package com.nosliw.data.exchange;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataImp;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.application.utils.HAPJsonUtility;

public class HAPTableData extends HAPExchangeData{

	private Map<String, HAPTableColumnData> m_columnData;
	
	public HAPTableData() {
		super(HAPTable.dataType);
		this.m_columnData = new LinkedHashMap<String, HAPTableColumnData>();
	}

	public Iterator<HAPData> iterate(){
		List<HAPData> rows = new ArrayList<HAPData>();
		int size = this.size();
		for(int i=0; i<size; i++){
			rows.add(this.getRow(i));
		}
		return rows.iterator();
	}
	
	public int size(){
		int size = 0;
		Iterator<String> it = this.m_columnData.keySet().iterator();
		if(it.hasNext()){
			String column = it.next();
			HAPTableColumnData columnData = this.getColumnData(column);
			if(columnData != null)  size = columnData.size();
		}
		return size;
	}

	public HAPTableColumnData getColumnData(String column){ 
		return this.m_columnData.get(column);
	}
	
	public HAPTableColumnData addColumn(String column){
		HAPTableColumnData out = new HAPTableColumnData();
		this.m_columnData.put(column, out);
		return out;
	}
	public void addColumnData(HAPTableColumnData data){
		this.m_columnData.put(data.getName(), data);
	}
	
	public HAPTableRowData getRow(int row){
		HAPTableRowData out = new HAPTableRowData();
		for(String key : m_columnData.keySet()){
			out.put(key, this.m_columnData.get(key).getData(row));
		}
		return out;
	}

	public void addRow(HAPTableRowData row){
		
		for(String key : row.keySet()){
			HAPData data = row.get(key);
			
			HAPTableColumnData column = this.m_columnData.get(key);
			if(column == null){
				column = this.addColumn(key);
			}
			column.appendData(data);
		}
	}

	@Override
	public HAPData getElement(HAPDataElementPathInfo segInfo){
		HAPDataElementPathInfo subSegInfo = HAPExchangeData.getCurrentInfo(segInfo.rest);
		HAPListData outList = new HAPListData();
		
		HAPTableColumnData tableColumnData = this.getColumnData(subSegInfo.info);
		List<HAPData> columnDatas = tableColumnData.getAllData();
		for(HAPData columnData : columnDatas){
			outList.add(HAPExchangeData.getElement(columnData, subSegInfo));
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
			 List<Map<String, String>> tableArrayJson = new ArrayList<Map<String, String>>();
			 int length = this.size();
			 for(int i=0; i<length; i++){
				 tableArrayJson.add(new LinkedHashMap<String, String>());
			 }
			 
			for(String key : this.m_columnData.keySet()){
				HAPTableColumnData columnData = this.m_columnData.get(key);
				for(int i=0; i<length; i++){
					tableArrayJson.get(i).put(key, columnData.getData(i).toStringValue(HAPConstant.FORMAT_JSON_DATATYPE));
				}
			}
			 
			List<String> outJson = new ArrayList<String>();
			for(Map<String, String> rowJsonMap : tableArrayJson){
				Map<String, String> mapJsonMap = new LinkedHashMap<String, String>();
				mapJsonMap.put("dataType", HAPTableRow.dataType.getDataTypeInfo().toStringValue(format));
				mapJsonMap.put("data", HAPJsonUtility.getMapJson(rowJsonMap));
				outJson.add(HAPJsonUtility.getMapJson(mapJsonMap));
			}
			
			return HAPJsonUtility.getArrayJson(outJson.toArray(new String[0]));
		}
		return null;
	}
}
