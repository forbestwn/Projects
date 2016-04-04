	var createNewDatasourceFunction = function(parms, parent){
		
		var that = new NosliwAppFunction('NewDatasource', parms, parent, 
		{
			m_doInit : function(){
				var that = this;
				var providerID = this.getParm("providerID");
				
				var newDataSourceRequestGroup = NosliwRequestManager.createRequestGroup();
				newDataSourceRequestGroup.setHandler({
					endHandler : function(){
						that.finishInit();
					},
				});
				
				newDataSourceRequestGroup.addRequest(NosliwEntityManager.getRequestInfoGetEntityWrapers([providerID], {
					success : function(requestInfo, data){
						var provider = data[providerID];
						that.setData('provider', provider);
						
						var entityType = getEntityAttributeWraperByPath(provider, "datasourceentity").data;
						var newUIResource = getEntityAttributeWraperByPath(provider, "newui").data;
						
						var requests = [];
						requests.push(NosliwOperationRequestManager.getRequestInfoNewEntity(entityType, {provider : providerID}, {
							success : function(requestInfo, results){
								var entity = results.results[0].extra;
								that.setData('entity', entity);
								that.setData('entityID', entity.ID);
							},
							error : function(requestInfo, serviceData){
								alert('new Entity Fail');
							},
						}, {}, {
							isAutoCommit : false,
						}));

						requests.push(NosliwUIResourceManager.getRequestInfoCreateUIResourceView(newUIResource, {
							success : function(requestInfo, data){
								that.setData('uiresource', data);
							},
						}));
						
						return requests;
					},
				}));
				
				NosliwRequestManager.startRequestGroup(newDataSourceRequestGroup);
			},
		
			m_postInit : function(){
				var that = this;
		 		var context = {};
		 		var entity = this.getData('entity');
		 		var newDataSourceResourceView = this.getData('uiresource');

		 		setContext(context, 'datasource', entity);
		 		newDataSourceResourceView.setContext(context);
		 		
		 		var parentEle = undefined; 
		 		var parentEleID = this.getParm('parentEleID');
		 		if(parentEleID!=undefined){
		 			parentEle = $("#"+parentEleID);
		 		}
		 		else{
			 		parentEle = this.getParm('parentEle');
			 		if(parentEle==undefined){
			 			parentEle = $("<div id='"+ this.ID +"'></div>");
			 			this.setData('parentEle', parentEle);
			 		}
		 		}
		 		
		 		parentEle.append(newDataSourceResourceView.getViews());
				var dialog = parentEle.dialog({
				      autoOpen: false,
				      height: 300,
				      width: 350,
				      modal: true,
				      buttons: {
				        Ok: function(){
				        	NosliwOperationRequestManager.requestCommit({}, {});
					        dialog.dialog( "close" );
				        },
				        Cancel: function() {
				          dialog.dialog( "close" );
				          NosliwOperationRequestManager.requestRollback({}, {});
				        }
				      },
				      close: function() {
				    	  that.destroy();
				      }
				 });
				 dialog.dialog( "open" );
				 this.setData('dialog', dialog);
			},
			
			m_doDestroy : function(){
				var newDataSourceResourceView = this.getData('uiresource');
				newDataSourceResourceView.destroy();

				var dialog = this.getData('dialog');
				dialog.dialog( "destroy" );

				var parentEle = this.getData('parentEle');
				if(parentEle!=undefined){
					parentEle.remove();
				}
				
			},
			
		});

		return that;
	};
	
