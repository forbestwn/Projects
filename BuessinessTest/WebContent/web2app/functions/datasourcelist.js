/**
 * 
 */
	var createDataSourceListFunction = function(parms, parent){
		
		var m_editDataSourceFunction = createEditDatasourceFunction({parentEle:$('#datasource')});

		var that = new NosliwAppFunction('DataSourceList', parms, parent, 
		{
			m_doInit : function(){
				var requestGroup = NosliwRequestManager.createRequestGroup();
				requestGroup.setHandler({
					endHandler : function(){
				 		var datasourceListContext = {};
				 		var datasourceQuery = this.getRequestGroupMetaData('datasourceQuery');
				 		if(datasourceQuery!=undefined){
					 		setContext(datasourceListContext, 'datasources', datasourceQuery);
					 		var datasourceListResourceView = this.getRequestGroupMetaData('uidatasourcelist');
					 		datasourceListResourceView.setContext(datasourceListContext);
							$("#datasourcelist").append(datasourceListResourceView.getViews());
				 		
							datasourceListResourceView.on('selectDataSource', function(datasourceId){
								m_editDataSourceFunction.command('setDataSourceId', datasourceId);
							});
				 		}
					},
				});	
				
				var datasourceQuery = {
						attributes : ["name"],
						entityTypes : [
						               {
						            	   categary : 'entity',
						            	   entityGroup : 'group.datasource',
						               }
						               ],

				};
				requestGroup.addRequest(NosliwQueryManager.getRequestInfoQuery(datasourceQuery, {
					success : function(requestInfo, data){
						this.setRequestGroupMetaData('datasourceQuery', data);
					},
				}));

				requestGroup.addRequest(NosliwUIResourceManager.getRequestInfoCreateUIResourceView('datasourcelists', {
					success : function(requestInfo, data){
						this.setRequestGroupMetaData('uidatasourcelist', data);
					},
				}));
				
				NosliwRequestManager.startRequestGroup(requestGroup);
				
			},
		});

		return that;
	};



