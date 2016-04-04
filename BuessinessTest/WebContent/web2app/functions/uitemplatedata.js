	var createUITemplateDataFunction = function(parms, parent){
		
		var m_uiTemplateDataView;
		var m_parentEle;
		
		var that = new NosliwAppFunction('UITemplateData', parms, parent, 
		{
			m_doInit : function(){
				var that = this;
				
				m_parentEle = this.getParm('parentEle');
				
				var requestGroup = NosliwRequestManager.createRequestGroup();
				requestGroup.setHandler({
					endHandler : function(){
						that.finishInit();
					},
				});		
				
				requestGroup.addRequest(NosliwUIResourceManager.getRequestInfoCreateUIResourceView('uitemplatedata', {
					success : function(requestInfo, data){
						m_uiTemplateDataView = data;
					},
				}));									

				NosliwRequestManager.startRequestGroup(requestGroup);
			},
		
			m_postInit : function(){
				var that = this;

				m_parentEle.append(m_uiTemplateDataView.getViews());

				m_uiTemplateDataView.on('refreshPage', function(){
					var templateInfo = that.getData('templateInfo');

					var outDatas = {};
					var templateDatas = templateInfo.data;
					for (var name in templateDatas) {
					    if (templateDatas.hasOwnProperty(name)) {
					    	var eleData = templateDatas[name].datatype;
					    	outDatas[name] = getTemplateData(eleData);
					    }
					}					
					document.getElementById('templateSrc').contentWindow.setDatas(outDatas);					
				});
				
				m_uiTemplateDataView.on('newTableRow', function(dataTypeContext){
					var newRow = {};
					var tableData = getObjectContextEleData(dataTypeContext);
					var columns = tableData.datatype.columns;
					for (var columnName in columns) {
					    if (columns.hasOwnProperty(columnName)) {
					    	var columnDataType = columns[columnName].datatype;
					    	var columnData = getDefaultDataFromDataType(columnDataType);
					    	newRow[columnName] = columnData;
					    }
					}					
					tableData.data.push(newRow);
					var index = tableData.data.length-1;
					
					dataTypeContext.data.data.trigger(EVENT_DATA_CHANGE+':'+EVENT_ELEMENT_ADD+':'+dataTypeContext.path+'.data', {ID:index, data:createObjectWraper(dataTypeContext.data.data, dataTypeContext.path+'.data.'+index)});
				});

				m_uiTemplateDataView.on('deleteTableRow', function(delInfo){
					var index = delInfo.index;
					var dataTypeContext = delInfo.tableEle; 
					
					var tableData = getObjectContextEleData(dataTypeContext).data;
					delete tableData[index];

					dataTypeContext.data.data.trigger(EVENT_DATA_CHANGE+':'+EVENT_ELEMENT_REMOVE+':'+dataTypeContext.path+'.data', {ID:index});
				});
			
			},
			
			m_doDestroy : function(){
			},
			
			m_doCommand : function(name, data){
				var that = this;
				
				if(name=='setUITemplateId'){
					var uiTemplateInfo = data;
					this.setData('templateInfo', uiTemplateInfo);
			 		var templateContext = {};
			 		setContext(templateContext, 'templateinfo', createObjectWraper(uiTemplateInfo));
			 		m_uiTemplateDataView.setContext(templateContext);
				}
			},
		});
		return that;
	};
	
