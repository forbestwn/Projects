	function getTemplateData(templateData){
		var out;
		var dataType = templateData.datatype;
		var data = templateData.data;
		if(dataType.categary=='complex'){
			if(dataType.type=='map'){
				out = {};
				for (var mapAttr in data) {
				    if (data.hasOwnProperty(mapAttr)) {
				    	out[mapAttr] = getTemplateData(data[mapAttr].datatype);
				    }
				}					
			}
			else if(dataType.type=='table'){
				out = [];
				for(var index in data){
					var row = data[index];
					var rowData = {};
					for (var rowAttr in row) {
					    if (row.hasOwnProperty(rowAttr)) {
					    	rowData[rowAttr] = getTemplateData(row[rowAttr]);
					    }
					}
					out.push(rowData);
				}
			}
		}
		else{
			out = data;
		}
		return out;
	};
	

	function getDataTypeResourceData(mobileData, datatype){
		var out;
		
		if(datatype.datatype.categary=='simple'){
			out = {
				datatype : datatype.datatype,
				data : mobileData,
			};
		}
		else{
			var type = datatype.datatype.type;
			if(type=='map' || type=='tablerow'){
				out = {
					datatype : datatype.datatype,
					data : {},
				};
				
				var mapData = datatype.data;
				for (var mapAttr in mapData) {
				    if (mapData.hasOwnProperty(mapAttr)) {
				    	var attrData = mapData[mapAttr];
				    	var attrDataTypeData = getDataTypeResourceData(mobileData[mapAttr], attrData.datatype);
				    	out.data[mapAttr] = {};
				    	out.data[mapAttr].name = attrData.name;
				    	out.data[mapAttr].description = attrData.description;
				    	out.data[mapAttr].datatype = attrDataTypeData;
				    }
				}					
			}
			else if(type=='table'){
				
			}
		}
		return out;
	};

	function getDefaultDataFromDataType(datatype){
		
		var out;
		
		if(datatype.datatype.categary=='simple'){
			out = {
				datatype : datatype.datatype,
				data : datatype.datatype['default'],
			};
		}
		else{
			var type = datatype.datatype.type;
			if(type=='map'){
				out = {
					datatype : datatype.datatype,
					data : {},
				};
				
				var mapData = datatype.data;
				for (var mapAttr in mapData) {
				    if (mapData.hasOwnProperty(mapAttr)) {
				    	var attrData = mapData[mapAttr];
				    	var attrDataTypeData = getDataTypeResourceData(mobileData[mapAttr], attrData.datatype);
				    	out.data[mapAttr].name = attrData.name;
				    	out.data[mapAttr].description = attrData.description;
				    	out.data[mapAttr].datatype = attrDataTypeData;
				    }
				}					
			}
			else if(type=='table'){
				
			}
		}
		return out;
	};
