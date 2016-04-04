package com.nosliw.entity.imp.datadefinition;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.application.core.data.HAPDataTypeManager;
import com.nosliw.application.core.data.exchange.HAPDataElementPathInfo;
import com.nosliw.application.core.data.exchange.HAPExchangeData;
import com.nosliw.application.core.data.exchange.HAPListData;
import com.nosliw.application.core.data.exchange.HAPMapData;
import com.nosliw.application.core.data.exchange.HAPTableColumnData;
import com.nosliw.application.core.data.exchange.HAPTableData;
import com.nosliw.application.core.data.exchange.HAPTableRow;
import com.nosliw.application.core.data.exchange.HAPTableRowData;
import com.nosliw.application.utils.HAPConstant;
import com.nosliw.entity.data.HAPDataWraper;
import com.nosliw.entity.data.HAPEntityContainerAttributeWraper;
import com.nosliw.entity.data.HAPEntityData;
import com.nosliw.entity.data.HAPEntityWraper;
import com.nosliw.entity.definition.HAPEntityDefinitionBasic;
import com.nosliw.entity.utils.HAPEntityUtility;


public class HAPDataTunnelEntity extends HAPEntityData{

	private static final String ATTRIBUTE_FROM = "from";
	private static final String ATTRIBUTE_TO = "to";
	
	public HAPDataTunnelEntity(HAPDataType dataType, HAPEntityDefinitionBasic entityInfo){
		super(dataType, entityInfo);
	}
	
	private HAPDataTypeAtomEntity getFromDataType(){
		return (HAPDataTypeAtomEntity)this.getAttributeEntityValue(ATTRIBUTE_FROM);
	}

	private HAPDataTypeAtomEntity getToDataType(){
		return (HAPDataTypeAtomEntity)this.getAttributeEntityValue(ATTRIBUTE_TO);
	}

	public HAPData process(Map<String, HAPData> contextData){
		HAPDataTunnelEndPointEntity inEndPoint = (HAPDataTunnelEndPointEntity)this.getChildPathWraper("in").getData();
		HAPDataTunnelEndPointEntity outEndPoint = (HAPDataTunnelEndPointEntity)this.getChildPathWraper("out").getData();
		String inId = inEndPoint.getEndPointID();
		String outId = outEndPoint.getEndPointID();
		HAPData inData = contextData.get(inId);
		HAPData outData = contextData.get(outId);
		
		HAPEntityContainerAttributeWraper tunnels = (HAPEntityContainerAttributeWraper)this.getChildPathWraper("spottunnels");
		Iterator<HAPDataWraper> bit = tunnels.iterate();
		while(bit.hasNext()){
			HAPEntityWraper tunnelSpotWraper = (HAPEntityWraper)bit.next();
			String inPath = tunnelSpotWraper.getChildWraperByPath("inpath").getData().toString();
			String outPath = tunnelSpotWraper.getChildWraperByPath("outpath").getData().toString();
			HAPData inTunnelData = this.getElement(inData, inPath); 
			outData = createData(inTunnelData, outPath, outData);
		}		
		contextData.put(outId, outData);
		return outData;
	}

	public HAPData getElement(HAPData data, String path){
		HAPDataElementPathInfo segInfo = HAPExchangeData.getCurrentInfo(path);
		HAPData inTunnelData = HAPExchangeData.getElement(data, segInfo);
		return inTunnelData;
	}
	
	public HAPData createData(HAPData indata, String path, HAPData data){
		HAPDataElementPathInfo segInfo = HAPExchangeData.getCurrentInfo(path);
		String type = segInfo.type;
		if("map".equals(type)){
			HAPDataElementPathInfo nextSeg = HAPExchangeData.getCurrentInfo(segInfo.rest);
			HAPMapData mapData = (HAPMapData)data;
			if(mapData==null)  mapData = new HAPMapData();
			HAPData mapEle = mapData.get(nextSeg.info);
			mapData.put(nextSeg.info, createData(indata, segInfo.rest, mapEle));
			return mapData;
		}		
		else if("table".equals(type)){
			HAPTableData tableData = (HAPTableData)data;
			if(tableData==null)  tableData = new HAPTableData();
			
			HAPDataElementPathInfo nextSeg = HAPExchangeData.getCurrentInfo(segInfo.rest);
			String columnName = nextSeg.info;
			
			HAPTableColumnData columnData = new HAPTableColumnData(columnName);
			Iterator<HAPData> it = ((HAPExchangeData)indata).iterate();
			while(it.hasNext()){
				HAPData d = it.next();
				columnData.appendData(createData(d, segInfo.rest, null));
			}
			tableData.addColumnData(columnData);
			return tableData;
		}
		else if("list".equals(type)){
			HAPListData listData = (HAPListData)data;
			if(listData==null)  listData = new HAPListData();
			
			Iterator<HAPData> it = ((HAPExchangeData)indata).iterate();
			while(it.hasNext()){
				listData.add(createData(it.next(), segInfo.rest, null));
			}
			return data;
		}
		else{
			return indata;
		}		
	}
	
	public HAPData createData1(HAPData in, String path, HAPData data){
		
		int index1 = path.indexOf(":");
		String p1 = path.substring(0, index1);
		String rest1 = path.substring(index1+1, path.length());

		String[] aa = p1.split("?");
		String name = aa[0];
		String type = aa[1];
		
		if(this.isAtomType(type)){
			if(in==null){
				in = data;
//				in = this.getApplicationContext().getDataTypeManager()
//						.createAtomData(new HAPDataType(name, type), data.toString());
			}
			else{
				in = data;
//				in.setValue(data);
			}
		}
		else{
			int index2 = rest1.indexOf(":");
			String p2 = rest1.substring(0, index2);
			String rest2 = rest1.substring(index2+1, rest1.length());

			String[] bb = p2.split("\\?");
			String name1 = bb[0];
			String type1 = bb[1];
			
			HAPListData dat = null;
			
			if(in==null){
				in = createPathData(p1);
			}
			if(HAPConstant.DATATYPE_CONTAINER_MAP.equals(type)){
				HAPData mapEle = ((HAPMapData)in).get(name1);
				mapEle = createData(mapEle, rest1, data);
				((HAPMapData)in).put(name1, mapEle);
			}
			if(HAPConstant.DATATYPE_CONTAINER_TABLE.equals(type)){
				HAPTableColumnData columnEle = ((HAPTableData)in).getColumnData(name1);
				columnEle = (HAPTableColumnData)createData(columnEle, rest1, data);
				((HAPTableData)in).addColumnData(columnEle);
			}
			if(HAPConstant.DATATYPE_CONTAINER_LIST.equals(type)){
				HAPListData datas =  ((HAPListData)data);
				Iterator<HAPData> it = datas.iterate();
				while(it.hasNext()){
					((HAPListData)in).add(createData(null, rest1, it.next()));
				}
			}
		}
		return in;
	}
	
	public boolean isAtomType(String pathseg){
		String[] aa = pathseg.split("-");
		if(aa.length<2)  return false;
		String categary = aa[0];
		String type = aa[1];
		
		if(HAPConstant.CATEGARY_SIMPLE.equals(categary) || HAPConstant.CATEGARY_BLOCK.equals(categary))  return true;
		return false;
	}
	
	public HAPData createPathData(String pathSeg){
		HAPData data = null;
		String[] segs = pathSeg.split("?");
		if(HAPConstant.DATATYPE_CONTAINER_MAP.equals(segs[1])){
			data = new HAPMapData();
		}
		else if(HAPConstant.DATATYPE_CONTAINER_TABLE.equals(segs[1])){
			data = new HAPTableData();
		}
		else if(HAPConstant.DATATYPE_CONTAINER_LIST.equals(segs[1])){
			data = new HAPListData();
		}
		else{
//			String[] aTypes = segs[1].split("-");
//			HAPDataType type = new HAPDataType(aTypes[0], aTypes[1]);
//			this.getApplicationContext().getDataTypeManager().createAtomData(type, null);
		}
		return data;
	}
	
	
	/*
	public HAPData process1(HAPData input){
		
		HAPDataTypeWraperEntity fromDataType = this.getFromDataType();
		HAPDataTypeWraperEntity toDataType = this.getToDataType();
		
		HAPDataType outDataType = new HAPDataType(toDataType.getCategary(), toDataType.getType()); 
		
		if(HAPUtility.isCompositType(outDataType)){
			if(HAPDataTypeManager.DATATYPE_TABLE.equals(outDataType)){
				HAPTableData out = new HAPTableData();
				HAPTableData tableIn = (HAPTableData)input;
				Set<HAPDataTunnelEntity> subTunnels = getSubTunnels();
				for(HAPDataTunnelEntity subTunnel : subTunnels){
					HAPDataTypeWraperEntity subFromDataType = subTunnel.getFromDataType();
					HAPDataTypeWraperEntity subToDataType = subTunnel.getToDataType();
					HAPTableColumnData columnData = tableIn.getColumnData(subFromDataType.getName());
					HAPTableColumnData outColumnData = (HAPTableColumnData)subTunnel.process(columnData);
					out.addColumnData(outColumnData);
					return out;
				}
			}
			else if(HAPDataTypeManager.DATATYPE_MAP.equals(outDataType)){
				HAPMapData out = new HAPMapData();
				
				Set<HAPDataTunnelEntity> subTunnels = getSubTunnels();
				for(HAPDataTunnelEntity subTunnel : subTunnels){
					HAPDataTypeWraperEntity subFromDataType = subTunnel.getFromDataType();
					HAPDataTypeWraperEntity subToDataType = subTunnel.getToDataType();
					String key = subToDataType.getName();
					
				}
				
				return out;
			}
			else if(HAPDataTypeManager.DATATYPE_LIST.equals(outDataType)){
				
			}
			else if(HAPDataTypeManager.DATATYPE_TABLECOLUMN.equals(outDataType)){
				HAPTableColumnData out = new HAPTableColumnData();
				HAPTableColumnData columnIn = (HAPTableColumnData)input;
				out.setName(toDataType.getName());
				List<HAPData> columnData = columnIn.getAllData();
				Set<HAPDataTunnelEntity> subTunnels = getSubTunnels();

				String fromChildType = ((HAPDataTypeTableColumnEntity)fromDataType).getChildType(); 
				String fromChildCategary = ((HAPDataTypeTableColumnEntity)fromDataType).getChildCategary(); 
				String toChildType = ((HAPDataTypeTableColumnEntity)toDataType).getChildType(); 
				String toChildCategary = ((HAPDataTypeTableColumnEntity)toDataType).getChildCategary(); 
				HAPDataType fromChildDataType = new HAPDataType(fromChildCategary, fromChildType);
				
				if(subTunnels==null){
					out.setData(columnData);
				}
				else{
//					for(int i=0; i<columnData.size(); i++){
//						HAPData data = columnData.get(i);
//						for(HAPDataTunnelEntity subTunnel : subTunnels){
//							
//							if(HAPUtility.isCompositType(fromChildDataType)){
//								if(HAPDataTypeManager.DATATYPE_TABLE.equals(fromChildDataType)){
//									
//								}
//								else if(HAPDataTypeManager.DATATYPE_MAP.equals(fromChildDataType)){
//									
//								}
//								
//							}
//							HAPData subData = subTunnel.process(data);
//							HAPDataTypeEntity subFromDataType = subTunnel.getFromDataType();
//							
//							out.addColumnData(data);
//							return out;
//						}
//					}
				}
				
				return out;
			}
		}
		else{
			
		}
		return null;
	}
	*/
}
