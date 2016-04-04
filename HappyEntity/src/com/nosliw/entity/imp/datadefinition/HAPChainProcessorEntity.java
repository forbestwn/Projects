package com.nosliw.entity.imp.datadefinition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.adiak.datasource.ADKDataSourcePoint;
import com.nosliw.application.core.data.HAPData;
import com.nosliw.application.core.data.HAPDataType;
import com.nosliw.entity.data.HAPDataWraper;
import com.nosliw.entity.data.HAPEntityContainerAttributeWraper;
import com.nosliw.entity.data.HAPEntityData;
import com.nosliw.entity.data.HAPEntityWraper;
import com.nosliw.entity.data.HAPReferenceWraper;
import com.nosliw.entity.definition.HAPEntityDefinitionBasic;

public class HAPChainProcessorEntity extends HAPEntityData{

	public HAPChainProcessorEntity(HAPDataType dataType, HAPEntityDefinitionBasic entityInfo){
		super(dataType, entityInfo);
	}
	
	public Map<String, HAPData> chainProcessor(Map<String, HAPData> indatas){
		Map<String, HAPData> out = new LinkedHashMap<String, HAPData>();
		
		Map<String, HAPData> contextData = new LinkedHashMap<String, HAPData>();
		if(indatas!=null)	contextData.putAll(indatas);
		
		List<HAPEntityWraper> processEntitys = new ArrayList<HAPEntityWraper>();
		
		HAPEntityContainerAttributeWraper dataTunnels = (HAPEntityContainerAttributeWraper)this.getChildPathWraper("datatunnels");
		Iterator<HAPDataWraper> dataTunnelsIt = dataTunnels.iterate();
		while(dataTunnelsIt.hasNext()){
			processEntitys.add((HAPEntityWraper)dataTunnelsIt.next());
		}
		
		HAPEntityContainerAttributeWraper processors = (HAPEntityContainerAttributeWraper)this.getChildPathWraper("processors");
		Iterator<HAPDataWraper> processorsIt = processors.iterate();
		while(processorsIt.hasNext()){
			processEntitys.add((HAPEntityWraper)processorsIt.next());
		}
		
		Set<String> processedIds = new HashSet<String>(); 
		if(indatas!=null)		processedIds.addAll(indatas.keySet());
		
		HAPEntityContainerAttributeWraper outDataPoints = (HAPEntityContainerAttributeWraper)this.getChildPathWraper("outdatas");
		Iterator<HAPDataWraper> outDataPointsIt = outDataPoints.iterate();
		while(outDataPointsIt.hasNext()){
			HAPDataTunnelEndPointEntity outPointEntity = (HAPDataTunnelEndPointEntity)outDataPointsIt.next().getData();
			getOutput(processEntitys, processedIds, contextData, outPointEntity);
			HAPData d = contextData.get(outPointEntity.getEndPointID());
			if(d!=null)			out.put(outPointEntity.getEndPointID(), d);
		}		
		
		return out;
	}
	
	public void getOutput(List<HAPEntityWraper> processEntitys, Set<String> processedIds, Map<String, HAPData> contextData, HAPDataTunnelEndPointEntity outPoint){
		String id = outPoint.getEndPointID();
		for(HAPEntityWraper processEntity : processEntitys){
			HAPDataTunnelEndPointEntity outPointEntity = (HAPDataTunnelEndPointEntity)processEntity.getChildWraperByPath("out").getData();
			if(id.equals(outPointEntity.getEndPointID())){
				
				HAPDataTunnelEndPointEntity inPointEntity = (HAPDataTunnelEndPointEntity)processEntity.getChildWraperByPath("in").getData();
				if(inPointEntity!=null){
					String inId = inPointEntity.getEndPointID();
					if(!processedIds.contains(inId)){
						getOutput(processEntitys, processedIds, contextData, inPointEntity);
						processedIds.add(inId);
					}
				}
				process(processEntity, contextData);
			}
		}
	}
	
	public HAPData process(HAPEntityWraper processor, Map<String, HAPData> contextData){
		HAPData out = null;
		if(processor.getChildWraperByPath("processor")!=null){
			HAPDataTunnelEndPointEntity inPointEntity = (HAPDataTunnelEndPointEntity)processor.getChildWraperByPath("in").getData();
			HAPDataTunnelEndPointEntity outPointEntity = (HAPDataTunnelEndPointEntity)processor.getChildWraperByPath("out").getData();
			HAPData inData = null;
			if(inPointEntity!=null)  inData = contextData.get(inPointEntity.getEndPointID());
			
			HAPReferenceWraper processorRefWraper = (HAPReferenceWraper)processor.getChildWraperByPath("processor");
			ADKDataSourcePoint dataSource = (ADKDataSourcePoint)processorRefWraper.getReferenceWraper().getData();
			out = dataSource.process(inData);
			contextData.put(outPointEntity.getEndPointID(), out);
		}
		else{
			HAPDataTunnelEndPointEntity inPointEntity = (HAPDataTunnelEndPointEntity)processor.getChildWraperByPath("in").getData();
			HAPData inData = contextData.get(inPointEntity.getEndPointID());
			HAPDataTunnelEntity dataTunnelEntity = (HAPDataTunnelEntity)processor.getData();
			out = dataTunnelEntity.process(contextData);
		}
		return out;
	}
}
