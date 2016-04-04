	var createEditDatasourceFunction = function(parms, parent){
		
		var m_dataSourceViews = {};
		var m_currentSourceView;
		var m_currentSourceName;
		var m_parentEle;
		
		var that = new NosliwAppFunction('EditDatasource', parms, parent, 
		{
			m_doInit : function(){
				m_parentEle = this.getParm('parentEle');
			},
		
			m_postInit : function(){
			},
			
			m_doDestroy : function(){
			},
			
			m_doCommand : function(name, data){
				var that = this;
				
				if(name=='setDataSourceId'){
					var datasourceId = data;
					
					var dataSourceRequestGroup = NosliwRequestManager.createRequestGroup();
					dataSourceRequestGroup.setHandler({
						endHandler : function(){
					 		var datasourceContext = {};
					 		setContext(datasourceContext, 'datasource', that.getData('datasourceWraper'));
					 		
					 		var datasourceResourceView = that.getData('datasourceUiresource');
					 		if(datasourceResourceView!=undefined){
					 			if(m_currentSourceView!=undefined)   m_currentSourceView.getViews().detach();
						 		m_currentSourceView = datasourceResourceView;
						 		m_parentEle.append(m_currentSourceView.getViews());
						 		m_currentSourceView.setContext(datasourceContext);
					 		}
					 		else{
						 		m_currentSourceView.setContext(datasourceContext);
					 		}
							m_currentSourceName = that.setData('datasourceUIName');
						}
					});		
					
					dataSourceRequestGroup.addRequest(NosliwEntityManager.getRequestInfoGetEntityWrapers([datasourceId], {
						success : function(requestInfo, data){
							var datasource = data[datasourceId];
							that.setData('datasourceWraper', datasource);
							var providerID = getEntityAttributeWraperByPath(datasource, 'provider').data;
						
							return NosliwEntityManager.getRequestInfoGetEntityWrapers([providerID], {
								success : function(requestInfo, data){
									var provider = data[providerID];
									that.setData('datasourceProviderWraper', provider);
									var editui = getEntityAttributeWraperByPath(provider, 'editui').data;
									if(m_dataSourceViews[editui]==undefined){
										return NosliwUIResourceManager.getRequestInfoCreateUIResourceView(editui, {
											success : function(requestInfo, data){
												that.setData('datasourceUiresource', data);
												that.setData('datasourceUIName', editui);
											},
										});									
									}
									else{
										if(m_currentSourceName==editui){
											that.clearData('datasourceUiresource');
										}
										else{
											that.setData('datasourceUiresource', m_dataSourceViews[editui]);
										}
										that.setData('datasourceUIName', editui);
									}
								},
							});							
						},
					}));
					NosliwRequestManager.startRequestGroup(dataSourceRequestGroup);
				}
			},
			
		});

		return that;
	};
	
