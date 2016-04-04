	var m_createFaceUITag = function(id, uiTag, uiResourceView){
		
		var m_uiTag = new NosliwUITag(id, uiTag, uiResourceView, 
		{
			tag_clearOldContext : function(){
			},
			
			tag_updateContext : function(){
				var face = getContextPathWraper(this.context, {name:'face'}).data;

				this.m_processBodyResourceView(face.layout.uiresource);

		 		var context = {};
				setContext(context, "face", face.face);
		 		
				var borderTags = this.bodyResourceView.getTagsAttrMapByName('uicomponent', 'name');
				var faceComponents = face.components;
				var components = {};
				_.each(faceComponents, function(faceComponent, uiid, list){
					
			 		var componentContext = {};
					setContext(componentContext, "face", face.face);
		   			setContext(componentContext, 'resource', createObjectWraper(faceComponent.uiresource));
		   			
					var componentTag = borderTags[faceComponent.name];
					componentTag.setAttribute('uiid', uiid);
					
					component = {};
					component.name = faceComponent.name;
					component.uiid = faceComponent.uiid;
					component.tag = componentTag;
		   			component.context = componentContext;
		   			components[component.uiid] = component;
				}, this);
				
				var inputDataIDMap = face.inputDataIDMap;
				_.each(face.data, function(inputData, ID, list){
					var inputDataComponentInfo = inputDataIDMap[ID];
					var c = components[inputDataComponentInfo.parentID];
					setContext(c.context, 'data_'+inputDataComponentInfo.name, createDataWraper(inputData));
				}, this);

				_.each(components, function(component, uiid, list){
					component.tag.setContext(component.context);
				}, this);
				
				this.m_listenToBodyResourceViewEvent();
		 		this.startEle.after(this.bodyResourceView.getViews());
				
		 		that = this;
				this.bodyResourceView.on('face', function(eData){
					var eventBodyId = eData.uiid;
					var eventName = eData.event;
					var eventData = eData.data;
					var eventID = face.eventIDMap[eventBodyId][eventName];
					
					var faceEvents = face.eventChannelMap[eventID];
					for(var index in faceEvents){
						var faceEvent = faceEvents[index];

						var dataTunnels = faceEvent.data.datatunnels.data;
						var commandData = {};
						var eventDatas = [];
						var eventDataID = eventID+'.datatype';
						var aa = {};
						aa[eventID+'.datatype'] = eventData;
						eventDatas.push(aa);
						eventDatas.push(face.data);
						
						_.each(dataTunnels, function(dataTunnel, id, list){
							commandData = handleDataTunnel(dataTunnel, eventDatas, commandData);
						}, this);
						
						var commandID = faceEvent.data.command.data;
						var commandBodyInfo = face.commandIDMap[commandID];
						if(commandBodyInfo!=undefined){
							var commandTags = that.bodyResourceView.getTagsByNameAttribute('uicomponent', 'uiid', commandBodyInfo.parentID);
							_.each(commandTags, function(commandTag, index, list){
								var commandInput = {
									parms : {},
									extra : {},
								};

								_.each(commandBodyInfo.parms, function(dataTypeID, parm, list){
									var data = commandData[dataTypeID];
									if(data!=undefined){
										commandInput.parms[parm] = data;
										delete commandData[dataTypeID];
									}
								}, this);
								
								commandInput.extra = commandData;
								
								commandTag.setAttribute('command', {
									name : commandBodyInfo.name,
									data : commandInput,
								});
							}, this);
						}
						else{
							var nextFace = NavigateManager.navigate (commandID, commandData);
//							var nextFace = navigate (commandBodyId, commandCommandId, commandName, commandData[commandCommandId+'.datatype']);
//							goNextFace(nextFace);
						}
					}
				});
			},
			
			tag_handleBodyResourceView : function(event, data){
			},
			
			tag_postInit : function(){
			},

		});

		return m_uiTag;
	};
