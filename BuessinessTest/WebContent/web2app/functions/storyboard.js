	var createStoryboardFunction = function(parms, parent){
		
		var m_faceInfoFunction = createFaceInfoFunction({parentEle:$('#faceinfo')});
		
		var that = new NosliwAppFunction('Storyboard', parms, parent, 
		{
			m_doInit : function(){
				var that = this;
				var requestGroup = NosliwRequestManager.createRequestGroup();
				requestGroup.setHandler({
					startHandler : function(){
					},
					endHandler : function(){
				 		var storyboardContext = {};
				 		var applicationData = this.getRequestGroupMetaData('application')['default#application.application:appgood'];
				 		AppManager.setApplicationEntity(applicationData);
				 		setContext(storyboardContext, 'application', applicationData);
				 		
				 		var storyboardResourceView = this.getRequestGroupMetaData('uistoryboard');
						$("#application").append(storyboardResourceView.getViews());
				 		storyboardResourceView.setContext(storyboardContext);

				 		storyboardResourceView.on('faceInfo', function(faceInfo){
				 			m_faceInfoFunction.command('setFaceInfo', {
				 				faceID : faceInfo.faceUI.face.data,
				 				facePath : faceInfo.facePath,
				 			});
						});

				 		storyboardResourceView.on('faceEmulation', function(faceInfo){
				 			faceEmulationFunction.command('showFace', {
				 				faceID : faceInfo.faceUI.face.data,
				 				facePath : faceInfo.facePath,
				 			});
						});
				 		
						that.finishInit();
					},
				});	
				
				requestGroup.addRequest(NosliwEntityManager.getRequestInfoGetEntityWrapers(['default#application.application:appgood'], {
					success : function(requestInfo, data){
						this.setRequestGroupMetaData('application', data);
					},
				}));
		 		
				requestGroup.addRequest(NosliwUIResourceManager.getRequestInfoCreateUIResourceView('storyboard', {
					success : function(requestInfo, data){
						this.setRequestGroupMetaData('uistoryboard', data);
					},
				}));
				
				NosliwRequestManager.startRequestGroup(requestGroup);
			},
		
			m_postInit : function(){
				   
//				   jsPlumb.draggable("datasourceproviderlist");
//				   jsPlumb.draggable("rect2Id");
				   
//				   jsPlumb.connect({source:"datasourceproviderlist", target:"application"});
//				   jsPlumb.connect({source:"datasourceproviderlist", target:"rect2Id"});
				
//				   var endpointOptions1 = { isSource:true };
//				   var endpoint1 = jsPlumb.addEndpoint('datasourcelist', endpointOptions1);
				
//				   var endpointOptions2 = { isTarget:true, endpoint:"Rectangle", paintStyle:{ fillStyle:"gray" } };
//				   var endpoint2 = jsPlumb.addEndpoint("datasourceproviderlist", endpointOptions2);
			}
		
		});


		return that;
	};
	
