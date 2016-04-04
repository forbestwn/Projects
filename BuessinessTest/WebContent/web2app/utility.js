/**
 * 
 */
function getFacePathByFaceID(faceID){
	var faceEntity = NosliwEntityManager.getEntity(faceID);
	var faceUnitID = faceEntity.data.uiunit.ID;
	return getFacePathByFaceUnitID(faceUnitID);
}

function getFacePathByFaceUnitID(ID){
	var appEntity = AppManager.getApplicationEntity();
	var appuiID = appEntity.data.appui.data;
	var appUnitEntity = NosliwEntityManager.getEntity(appuiID);
	var components = appUnitEntity.data.configure.data.componentcontainers.data;
	for (var id in components) {
	    if (components.hasOwnProperty(id)) {
	    	var component = components[id];
	    	if(component.data.name.data=="faces"){
	    		var facesUnits = component.data.uiunits.data;
	    		for (var id1 in facesUnits) {
	    		    if (facesUnits.hasOwnProperty(id1)) {
	    		    	var faceUnitID = facesUnits[id1].data.configure.data;
	    		    	if(ID==faceUnitID){
	    		    		return "application.components|faces|"+id1;
	    		    	}
	    		    }
	    		}
	    	}
	    }
	}
};

function getFacePathOfFullPath(path, base){
	var fullPath = path;
	if(path.startsWith("\\..")){
	}
	else if(path.startsWith("\\.")){
		fullPath = base + path.subString(0, 1);
	}
	var segs = fullPath.split(".");
	return segs[0] + "." + segs[1];
};

function getFaceContextPathByUnitPath(unitPath, faceEntity){
	var out = "uiunit";
	var segs = unitPath.split('.');
	var i = 2;
	var unitEntity = faceEntity.data.uiunit;
	for(; i<segs.length; i++){
		out = out + '.configure';
		var seg = segs[i];
		var ts = seg.split('|');
		if(ts[0]=='component'){
			var coms = unitEntity.data.configure.data.components.data;
			var id1;
			for (var id in coms) {
			    if (coms.hasOwnProperty(id)) {
			    	var com = coms[id];
			    	if(com.data.name.data==ts[1]){
			    		id1 = id;
			    		break;
			    	}
			    }
			}
			
			out = out + '.components.'+id1+'.uiunit';
			unitEntity = coms[id1].data.uiunit;
		}
	}
	return out;
};

function getUiUnitEntityByUnitPath(unitPath, faceEntity){
	var segs = unitPath.split('.');
	var i = 2;
	var unitEntity = faceEntity.data.uiunit;
	for(; i<segs.length; i++){
		var seg = segs[i];
		var ts = seg.split('|');
		if(ts[0]=='component'){
			var coms = unitEntity.data.configure.data.components.data;
			var id1;
			for (var id in coms) {
			    if (coms.hasOwnProperty(id)) {
			    	var com = coms[id];
			    	if(com.data.name.data==ts[1]){
			    		id1 = id;
			    		break;
			    	}
			    }
			}
			unitEntity = coms[id1].data.uiunit;
		}
	}
	return unitEntity;
};
