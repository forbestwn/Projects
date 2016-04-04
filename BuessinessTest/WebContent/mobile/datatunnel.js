	function getDataFromContext(ID, inDataContext){
		if(_.isArray(inDataContext)==true){
			for(var index in inDataContext){
				var context = inDataContext[index];
				var data = context[ID];
				if(data!=undefined)   return data;
			}
		}
		else{
			return inDataContext[ID];
		}
	}

	function handleDataTunnel(dataTunnel, inDataContext, outDataContext){
		if(outDataContext==undefined)   outDataContext={};
		
		var fromEndPoint = dataTunnel["in"];
		var fromEndPointType = fromEndPoint.type;
		var fromData;
		if(fromEndPointType=='ref'){
			var fromDataID = fromEndPoint.point.data;
			fromData = getDataFromContext(fromDataID, inDataContext); 
		}
		else if(fromEndPointType=='refport'){
			var fromEndPointPath = fromEndPoint.point.data; 
			var fromEndPointID = cascadePath(fromEndPointPath.parent.data, fromEndPointPath.path.data);
			fromData = getDataFromContext(fromEndPointID, inDataContext);
		}
		else if(fromEndPointType=='valueport'){
			fromData = getDataFromContext(fromEndPoint.pointId.data, inDataContext);
		}
		else if(fromEndPointType=='static'){
			fromData = fromEndPoint.data;
		}
		else if(fromEndPointType=='uipath'){
			var fromDataID = fromEndPoint.ID;
			fromData = getDataFromContext(fromDataID, inDataContext); 
			if(fromData==undefined){
				fromData = getDataByUIPath(fromDataID);
			}
		}
		

		var toEndPoint = dataTunnel["out"];
		var toEndPointType = toEndPoint.type;
		var toDataID;
		if(toEndPointType=='ref'){
			toDataID = fromEndPoint.point.data;
		}
		else if(toEndPointType=='refport'){
			var toEndPointPath = toEndPoint.point.data; 
			toDataID = cascadePath(toEndPointPath.parent.data, toEndPointPath.path.data);
		}
		else if(toEndPointType=='valueport'){
			toDataID = toEndPoint.pointId.data;
		}
		else if(toEndPointType=='uipath'){
			toDataID = toEndPoint.ID;
		}

		var outTunnelData = handleDataTunnelData(dataTunnel, fromData, outDataContext[toDataID]);
		outDataContext[toDataID] = outTunnelData;
		return outDataContext;
	}

	function handleDataTunnelData(dataTunnel, indata, outdata){
		var fromData = indata;
		var toData = outdata;
		
		var spottunnels = dataTunnel.spottunnels;
		for (var key in spottunnels) {
	   		if (spottunnels.hasOwnProperty(key)) {
	   			var spottunnel = spottunnels[key];
				var fromPath = spottunnel.inpath;
				var inSpotData = getDataElement(fromData, fromPath);
				
				var toPath = spottunnel.outpath;
				toData = createData(inSpotData, toPath, toData);
	   		}
		}
		return toData;
	}
	
	function getDataElement(data, path){
		if(path==undefined)  return data;
		
		var segInfo = getCurrentInfo(path);
		var type = segInfo.type;
		if(type=="map"){
			var subSegInfo = getCurrentInfo(segInfo.rest);
			var subdata = data.data[subSegInfo.info];
			return getDataElement(subdata, segInfo.rest);
		}
		if(type=="tablerow"){
			var subSegInfo = getCurrentInfo(segInfo.rest);
			var subdata = data.data[subSegInfo.info];
			return getDataElement(subdata, segInfo.rest);
		}
		else if(type=="table"){
			var subSegInfo = getCurrentInfo(segInfo.rest);
			
			var outList = [];
			for(var j=0; j<data.data.length; j++){
				var tableRow = data.data[j];
				outList[j] = getDataElement(tableRow.data[subSegInfo.info], segInfo.rest);
			}
			return outList;
		}
		else if(type=="list"){
			var outList = [];
			for(var j=0; j<data.data.length; j++){
				outList[j] = getDataElement(data.data[j], segInfo.rest);
			}
			return outList;
		}				
		else{
			return data;
		}
	}
	
	function createData(indata, path, data){
		var segInfo = getCurrentInfo(path);
		var type = segInfo.type;
		if(type=="map"){
			var nextSeg = getCurrentInfo(segInfo.rest);
			if(data==undefined)  data = getEmpty(type);
			var mapEle = data.data[nextSeg.info];
			data.data[nextSeg.info] = createData(indata, segInfo.rest, mapEle);
			return data;
		}		
		else if(type=="list"){
			if(data==undefined)  data = getEmpty(type);
			for(var j=0; j<indata.data.length; j++){
				var ind = indata.data[j];
				data.push(createData(ind, segInfo.rest));
			}
			return data;
		}
		else if(type=="table"){
			if(data==undefined)  data = getEmpty(type);
			
			for(var j=0; j<indata.length; j++){
				var ind = indata[j];
				var dataRow = data.data[j];
				if(dataRow==undefined){
					dataRow = getEmpty('tablerow');
					data.data.push(dataRow);
				}
				var nextSeg = getCurrentInfo(segInfo.rest);
				dataRow.data[nextSeg.info]=createData(ind, segInfo.rest);
			}
			return data;
		}
		else{
			return indata;
		}		
	}
	
	function getEmpty(type){
		var empty;
		if(type=="list") {
			empty = {
				dataType : {
					categary : 'container',
					type : type,
				},
				data : [],
			};
		}
		else if(type=="table"){
		 	empty = {
				dataType : {
					categary : 'container',
			 		type : type,
				},
		 		data : [],
		 	};
		}
		else if(type=="map"){
		 	empty = {
				dataType : {
					categary : 'container',
			 		type : type,
				},
		 		data : {},
		 	};
		}
		else if(type=="tablerow"){
		 	empty = {
				dataType : {
					categary : 'container',
			 		type : type,
				},
		 		data : {},
		 	};
		}
		else{
			empty = {
				data : {},
			};
		}
		return empty;
	}
	
	function getCurrentInfo(path){
		var info = {};
		
		var i = path.indexOf(":");
		if(i!=-1){
			var seg = path.substring(0, i);
			info.seg = seg;
			info.rest = path.substring(i+1);
		
			var parts = seg.split("?");
			info.info = parts[0];
			info.type = parts[1];
		}
		else{
			info.seg = path;
			info.rest = "";
		
			var parts = path.split("?");
			info.info = parts[0];
			info.type = parts[1];
			
			var sub = info.type.split("-");
			if(sub.length>2){
				info.categary = sub[0];
				info.type = sub[1];
			}
		}
		return info;
	}

	
