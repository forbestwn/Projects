
	var m_createNavigationTag = function(id, uiTag, uiResourceView){
		
		var m_uiTagView = new NosliwUITag(id, uiTag, uiResourceView, 
		{
				m_view : {},
				m_faceView : {},
			
				tag_initViews : function(){
				},
				
				tag_updateContext : function(){
					var eventTunnel = this.getDataBindingValue();
					var eventPath = eventTunnel.eventpath.data;
					var dataTunnels = eventTunnel.datatunnels.data;
					for (var id in dataTunnels) {
					    if (dataTunnels.hasOwnProperty(id)) {
					    	var dataTunnel = dataTunnels[id].data;
					    	if(dataTunnel.out.data.point.data=="\.command|transfer|face|data"){
					    		var startPointId = dataTunnel["in"].data.point.data;
					    		var startPointEntity = NosliwEntityManager.getEntity(startPointId);
					    		var nextFaceID = startPointEntity.data.face.data;
					    		var nextFaceEntity = NosliwEntityManager.getEntity(nextFaceID);
					    		var nextFaceUnitEntity = nextFaceEntity.data.uiunit;
					    		
					    		var sourceID = getFacePathOfFullPath(eventPath);
					    		var targetID = getFacePathByFaceUnitID(nextFaceUnitEntity.ID);
					    		jsPlumbInstance.connect({source:sourceID, target:targetID});
					    	}
					    }
					}
				},
		});

		return m_uiTagView;
	};

	