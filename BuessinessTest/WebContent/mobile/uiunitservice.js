function processApplicationData(application){

	var uiunit = application.appui;
	initData(uiunit);
	
	var contextData = {};
	var datatunnels = [];

	processUIPath(uiunit);
	
	processStaticDataTunnel(uiunit, contextData, datatunnels);
	
	setUnitData(uiunit, contextData);
	
	setUnitDefaultData(uiunit, application.templates);
};

function initData(appunit){
	iterateUIUnit(appunit, undefined, function(){
		this.configure.datas = {};
		this.configure.configures = {};
		this.configure.styles = {};
	});
};


function processUIPath(appunit){
	iterateUIUnit(appunit, undefined, function(){
		var datainputs = this.configure.datainputs;
		for (var id in datainputs) {
		    if (datainputs.hasOwnProperty(id)) {
		    	var datatunnel = datainputs[id];
		    	var inSpot = datatunnel['in'];
		    	var outSpot = datatunnel['out'];
		    	if(inSpot.type=='uipath'){
		    		inSpot.ID = getAbsolutePath(inSpot.ID, this.uipath);
		    	}
		    	if(outSpot.type=='uipath'){
		    		outSpot.ID = getAbsolutePath(outSpot.ID, this.uipath);
		    	}
		    }
		}	
	});
};

function getAbsolutePath(relativePath, currentPath){
	if(relativePath.startsWith("\\..")){
		var n = currentPath.lastIndexOf(".");
		return getAbsolutePath(relativePath.subString(3), currentPath.subString(0, n));
	}
	else if(relativePath.startsWith("\\.")){
		return currentPath + "." + relativePath.subString(2);
	}
	else{
		return relativePath;
	}
};

function setUnitDefaultData(unit, templates){
	iterateUIUnit(unit, templates, function(templates){
		var template = templates[this.template];
		var templateDatas = template.datas;
		for (var name in templateDatas) {
		    if (templateDatas.hasOwnProperty(name)) {
		    	var templateDataInfo = templateDatas[name];
		    	var templateData = templateDataInfo.data;
		    	if(this.configure.datas[name]==undefined){
		    		this.configure.datas[name] = templateData;
		    	}
		    }
		}	
	});
};

function setUnitDataWithEvent(appunit, contextData, context){
	var contextPath = "appui";
	for (var path in contextData) {
	    if (contextData.hasOwnProperty(path)) {
	    	var data = contextData[path];
	    	var pathSegs = path.split(".");
	    	for(var i in pathSegs){
	    		var pathInfo = pathSegs[i];
	    		if(pathInfo=='application'){
	    		}
	    		else{
	    			contextPath = contextPath + ".configure";
	    			var pathInfoSegs = pathInfo.split("|");
	    			var pathInfoArea = pathInfoSegs[0];
	    			if(pathInfoArea=='data'){
	    				contextPath = contextPath + ".datas."+pathInfoSegs[1];
	    			}
	    			else if(pathInfoArea=='configure'){
	    				contextPath = contextPath + ".configures."+pathInfoSegs[1];
	    			}
	    			else if(pathInfoArea=='component'){
	    				contextPath = contextPath + ".components."+pathInfoSegs[1]+".uiunit";
	    			}
	    			else if(pathInfoArea=='components'){
	    				contextPath = contextPath + ".componentcontainers."+pathInfoSegs[1]+".uiunits."+pathInfoSegs[2];
	    			}
	    		}
	    	}
	    	
	    	requestSetObjectContextPathValue(context, {name:"application", path:contextPath}, data);
	    }
	}	
};

function setUnitData(appunit, contextData){
	for (var path in contextData) {
	    if (contextData.hasOwnProperty(path)) {
	    	var data = contextData[path];
	    	var pathSegs = path.split(".");
	    	var unit = appunit;
	    	for(var i in pathSegs){
	    		var pathInfo = pathSegs[i];
	    		if(pathInfo=='application'){
	    	    	unit = appunit;
	    		}
	    		else{
	    			var pathInfoSegs = pathInfo.split("|");
	    			var pathInfoArea = pathInfoSegs[0];
	    			if(pathInfoArea=='data'){
	    				unit.configure.datas[pathInfoSegs[1]] = data;
	    			}
	    			else if(pathInfoArea=='configure'){
	    				unit.configure.configures[pathInfoSegs[1]] = data;
	    			}
	    			else if(pathInfoArea=='component'){
	    				unit = unit.configure.components[pathInfoSegs[1]].uiunit;
	    			}
	    			else if(pathInfoArea=='components'){
	    				unit = unit.configure.componentcontainers[pathInfoSegs[1]].uiunits[pathInfoSegs[2]];
	    			}
	    		}
	    	}
	    }
	}	
};


function processStaticDataTunnel(unit, contextData, datatunnels){
	var d = {
		"contextData" : contextData,
		"datatunnels" : datatunnels,
	};
	
	iterateUIUnit(unit, d, function(data){
		var datainputs = this.configure.datainputs;
		for(var i in datainputs){
			var datatunnel = datainputs[i];
			var inSpot = datatunnel['in'];
			var type = inSpot.type;
			if(type=="static"){
				handleDataTunnel(datatunnel, data.contextData, data.contextData);
			}
			else{
				datatunnels.push(data.datatunnel);
			}
		}
	});
}

function iterateUIUnit(unit, data, handler){
	
	handler.call(unit, data);
	
	var components = unit.configure.components;
	for (var name in components) {
	    if (components.hasOwnProperty(name)) {
	    	var component = components[name];
	    	iterateUIUnit(component.uiunit, data, handler);
	    }
	}	
	
	var componentcontainers = unit.configure.componentcontainers;
	for (var name in componentcontainers) {
	    if (componentcontainers.hasOwnProperty(name)) {
	    	var componentcontainer = componentcontainers[name];
	    	var units = componentcontainer.uiunits;
	    	for (var id in units) {
	    	    if (units.hasOwnProperty(id)) {
	    	    	iterateUIUnit(units[id], data, handler);
	    	    }
	    	}
	    }
	}	
}

function getDataByUIPath(uiPath){
	appunit = getApplicationUnit();
	
	var pathSegs = path.split(".");
	var unit = appunit;
	for(var i in pathSegs){
		var pathInfo = pathSegs[i];
		if(pathInfo=='application'){
	    	unit = appunit;
		}
		else{
			var pathInfoSegs = pathInfo.split("|");
			var pathInfoArea = pathInfoSegs[0];
			if(pathInfoArea=='data'){
				return unit.configure.datas[pathInfoSegs[1]];
			}
			else if(pathInfoArea=='configure'){
				return unit.configure.configures[pathInfoSegs[1]];
			}
			else if(pathInfoArea=='component'){
				unit = unit.configure.components[pathInfoSegs[1]];
			}
			else if(pathInfoArea=='components'){
				unit = unit.configure.componentcontainers[pathInfoSegs[1]].uiunits[pathInfoSegs[2]];
			}
		}
	}
};

function createUnitDataContext(uiUnit){
	var contextData = {
	};
	
	iterateUIUnit(uiUnit, contextData, function(context){
		var datas = uiUnit.configure.datas;
		for(var name in datas){
		    if (datas.hasOwnProperty(name)) {
		    	var data = datas[name];
		    	context[uiUnit.uipath+'.data|'+name] = data;
		    }			
		}
	});
	return contextData;
};
