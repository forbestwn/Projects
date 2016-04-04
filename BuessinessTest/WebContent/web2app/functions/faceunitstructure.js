	var createFaceUnitStructureFunction = function(parms, parent){
		
		var m_faceInfoResourceView;
		
		var that = new NosliwAppFunction('FaceUnitStructure', parms, parent, 
		{
			m_doInit : function(){
				var requestGroup = NosliwRequestManager.createRequestGroup();
				requestGroup.setHandler({
					endHandler : function(){
				 		m_faceInfoResourceView = this.getRequestGroupMetaData('uifaceunitstructure');
						$("#unitstructure").append(m_faceInfoResourceView.getViews());
				 		m_faceInfoResourceView.on('unitInfo', function(unitInfo){
				 			that.trigueEvent("unitInfo", unitInfo);
						});
						
					},
				});	
				
				requestGroup.addRequest(NosliwUIResourceManager.getRequestInfoCreateUIResourceView('uifaceunitstructure', {
					success : function(requestInfo, data){
						this.setRequestGroupMetaData('uifaceunitstructure', data);
					},
				}));
				
				NosliwRequestManager.startRequestGroup(requestGroup);
			},

			m_doCommand : function(name, data){
				if(name=="setFaceInfo"){
//					m_faceInfoResourceView.setAttribute("facePath", data.facePath);
					
					var facePathContextEle = createContextElement(createObjectWraper({data:data.facePath}));
					
		    		var faceEntity = data.faceEntity;
					faceContextEle = createContextElementEntity(faceEntity);
					
					var con = {};
					con.face = faceContextEle;
					con.facePath = facePathContextEle;
					m_faceInfoResourceView.setContext(con);
				}
			},				
		});

		return that;
	};
