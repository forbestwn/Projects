	var createDataSourceProviderFunction = function(parms, parent){
		
		var that = new NosliwAppFunction('DataSourceProvider', parms, parent, 
		{
			m_doInit : function(){
				var that = this;
				
				var requestGroup = NosliwRequestManager.createRequestGroup();
				requestGroup.setHandler({
					endHandler : function(){
				 		var providerListContext = {};
				 		var providerQuery = that.getData('providerQuery');
				 		if(providerQuery!=undefined){
					 		setContext(providerListContext, 'providers', providerQuery);
					 		var providerListResourceView = that.getData('uiproviderlist');
					 		providerListResourceView.setContext(providerListContext);
							$("#datasourceproviderlist").append(providerListResourceView.getViews());
							
							providerListResourceView.on('newDataSource', function(provider){
								createNewDatasourceFunction({'providerID' : provider.ID});
							});
				 		}
					},
				});	
				
				
				requestGroup.addRequest(NosliwUIResourceManager.getRequestInfoCreateUIResourceView('datasourcetypelists', {
					success : function(requestInfo, data){
						that.setData('uiproviderlist', data);
					},
				}));

				var providerQuery = {
						attributes : ["name", "description", "datasourceentity", "newui"],
						entityTypes : [
						               {
						            	   categary : 'entity',
						            	   type : 'datasource.provider',
						               }
						               ],

				};
				requestGroup.addRequest(NosliwQueryManager.getRequestInfoQuery(providerQuery, {
					success : function(requestInfo, data){
						that.setData('providerQuery', data);
					},
				}));

				NosliwRequestManager.startRequestGroup(requestGroup);
				
			},
		});

		return that;
	};
