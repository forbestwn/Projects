	
	
	function handleDataTunnel(dataTunnel, datacontext){
			var from = dataTunnel.from;
			var to = dataTunnel.to;
			
			var fromData = datacontext[from.datarootid];
			var fromPath = from.datapath;
			var inData = getDataElement(fromData, fromPath);
			
			var toData = datacontext[to.datarootid];
			var toPath = to.datapath;
			
			toData = createData(inData, toPath, toData);
			datacontext[to.datarootid] = toData;			
	}
	
	function getDataElement(data, path){
		if(path==undefined)  return data;
	
		var segInfo = getCurrentInfo(path);
		var type = segInfo.type;
		if(type=="map"){
			var subSegInfo = getCurrentInfo(segInfo.rest);
			var subdata = data.data[subSegInfo.info];
			return getDataElement(subdata, path.rest);
		}
		else if(type=="table"){
			var subSegInfo = getCurrentInfo(segInfo.rest);
			
			var outList = [];
			for(var j=0; j<data.data.length; j++){
				var tableRow = data.data[j];
				outList[j] = getDataElement(tableRow[subSegInfo.info], segInfo.rest);
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
				data.push(createData(ind, restpath));
			}
			return data;
		}
		else if(type=="table"){
			if(data==undefined)  data = getEmpty(type);
			
			for(var j=0; j<indata.length; j++){
				var ind = indata[j];
				var dataRow = data.data[j];
				if(dataRow==undefined){
					dataRow = {};
					data.data.push(dataRow);
				}
				var nextSeg = getCurrentInfo(segInfo.rest);
				dataRow[nextSeg.info]=createData(ind, segInfo.rest);
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
				categary : 'container',
				type : type,
				data : [],
			};
		}
		else if(type=="table"){
		 	empty = {
				categary : 'container',
		 		type : type,
		 		data : [],
		 	};
		}
		else if(type=="map"){
		 	empty = {
				categary : 'container',
		 		type : type,
		 		data : {},
		 	};
		}
		else{
			empty = {
				type : "",
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

	
