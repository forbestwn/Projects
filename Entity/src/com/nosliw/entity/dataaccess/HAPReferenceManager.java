package com.nosliw.entity.dataaccess;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.nosliw.common.serialization.HAPStringable;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.entity.data.HAPEntityID;
import com.nosliw.entity.data.HAPEntityWraper;
import com.nosliw.entity.data.HAPReferencePath;
import com.nosliw.entity.data.HAPReferenceWraper;
import com.nosliw.entity.utils.HAPEntityUtility;

public class HAPReferenceManager implements HAPStringable{

	Map<String, Set<HAPReferencePath>> m_parentRefs;
	HAPEntityDataAccess m_dataAccess;
	
	public HAPReferenceManager(HAPEntityDataAccess dataAccess) {
		m_parentRefs = new LinkedHashMap<String, Set<HAPReferencePath>>();
		this.m_dataAccess = dataAccess;
	}

	private HAPReferenceManager getUnderReferenceManager(){
		if(this.m_dataAccess.getUnderDataAccess()==null)  return null;
		return this.m_dataAccess.getUnderDataAccess().getReferenceManager();
	}
	
	public Set<HAPReferencePath> getParentReferences(HAPEntityID entityID){
		Set<HAPReferencePath> out = this.m_parentRefs.get(entityID.toString());
		if(out==null && this.getUnderReferenceManager()!=null){
			out = this.getUnderReferenceManager().getParentReferences(entityID);
		}
		if(out==null) out = new HashSet<HAPReferencePath>();
		return out;
	}
	
	public Set<HAPEntityWraper> searchByReverseAttribute(HAPEntityID entityID, String reverseAttr){
		Set<HAPEntityWraper> out = new HashSet<HAPEntityWraper>();
		Set<HAPReferencePath> paths = this.getReferenceSetByChildID(entityID);
		for(HAPReferencePath path : paths){
			HAPEntityID a = new HAPEntityID(reverseAttr);
			if(path.getEntityID().getEntityType().equals(a.getEntityType())){
				if(Pattern.matches(HAPBasicUtility.wildcardToRegex(a.getAttributePath()), path.getAttrPath())){
					out.add((HAPEntityWraper)this.m_dataAccess.useEntityByID(path.getEntityID()).getData());
				}
			}
		}
		return out;
	}
	
	public void removeAllParentReferences(HAPEntityID entityID){
		this.m_parentRefs.put(entityID.toString(), new HashSet<HAPReferencePath>());
	}
	
	public void mergeWith(HAPReferenceManager refMan){
		for(String ID : refMan.m_parentRefs.keySet()){
			Set<HAPReferencePath> refs = refMan.m_parentRefs.get(ID);
			if(refs!=null){
				this.m_parentRefs.put(ID, refs);
			}
		}
	}
	
	private Set<HAPReferencePath> getReferenceSetByChildID(HAPEntityID childID){
		Set<HAPReferencePath> childRefs = this.m_parentRefs.get(childID.toString());
		if(childRefs==null){
			childRefs = new HashSet<HAPReferencePath>();
			if(this.getUnderReferenceManager()!=null){
				Set<HAPReferencePath> underRefs = this.getUnderReferenceManager().getParentReferences(childID);
				if(underRefs!=null){
					for(HAPReferencePath p : underRefs){
						childRefs.add(p);
					}
				}
			}
			this.m_parentRefs.put(childID.toString(), childRefs);
		}
		return childRefs;
	}
	
	public void removeParentReference(HAPEntityID childID, HAPEntityID parentID, String parAttrPath){
		Set<HAPReferencePath> refPaths = this.getReferenceSetByChildID(childID);
		if(refPaths!=null){
			refPaths.remove(new HAPReferencePath(parentID, parAttrPath));
		}
	}
	
	public void addParentReference(HAPEntityID childID, HAPEntityID parentID, String parAttrPath){
		Set<HAPReferencePath> refPaths = this.getReferenceSetByChildID(childID);
		refPaths.add(new HAPReferencePath(parentID, parAttrPath));
	}

	public void addParentReference(HAPReferenceWraper refWraper){
		HAPEntityID childID = refWraper.getIDData();
		if(childID!=null){
			HAPEntityID parentID = refWraper.getRootEntityWraper().getID();
			String parAttrPath = refWraper.getRootEntityAttributePath();
			this.addParentReference(childID, parentID, parAttrPath);
		}
	}

	@Override
	public String toStringValue(String format) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
