package com.nosliw.entity.imp.datadefinition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nosliw.entity.data.HAPEntityContainerAttributeWraper;
import com.nosliw.entity.data.HAPDataWraper;

public class HAPDataTypeAtomWraperEntity extends HAPDataTypeEntity{

	private static final String ATTRIBUTE_ATOMTYPE = "atomtype";
	private static final String ATTRIBUTE_ATOMTYPES = "atomtypes";
	
	private HAPDataTypeAtomEntity getAtomType(){
		return (HAPDataTypeAtomEntity)this.getAttributeEntityValue(ATTRIBUTE_ATOMTYPE);
	}

	private List<HAPDataTypeAtomEntity> getAtomTypes(){
		List<HAPDataTypeAtomEntity> out = new ArrayList<HAPDataTypeAtomEntity>();
		HAPEntityContainerAttributeWraper wraper = (HAPEntityContainerAttributeWraper)this.getAttributeValueWraper(ATTRIBUTE_ATOMTYPES);
		Iterator<HAPDataWraper> it = wraper.iterate();
		while(it.hasNext()){
			HAPDataTypeAtomEntity entity = (HAPDataTypeAtomEntity)it.next().getData();
			out.add(entity);
		}
		return out;
	}
	
	@Override
	public void createPath(String path, String rootId){
		path = path;
		List<HAPDataTypeAtomEntity> atoms = getAtomTypes();
		
		HAPDataTypeAtomEntity atom = this.getAtomType();
		if(atom != null){
			atoms.add(atom);
		}
		
		for(HAPDataTypeAtomEntity a : atoms){
			a.setDataPath(path);
			a.setDataRootId(rootId);
		}
	}	
	
}
