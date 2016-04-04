
	var m_createStoryboardUITag = function(id, uiTag, uiResourceView){
		var m_view;
		
		var m_uiTagView = new NosliwUITag(id, uiTag, uiResourceView, 
		{
			tag_preInit : function(){
				this.m_processBodyResourceView();
			},

			
			tag_initViews : function(){
					m_view = $('<div id="storyboard" style="position: relative;width: 940px;height: 300px;border: 1px solid #000000;"/>');
					m_view.append(this.bodyResourceView.getViews());
					
					
					jsPlumbInstance = jsPlumb.getInstance({
					        // default drag options
					        DragOptions: { cursor: 'pointer', zIndex: 2000 },
					        // the overlays to decorate each connection with.  note that the label overlay uses a function to generate the label text; in this
					        // case it returns the 'labelText' member that we set on each connection in the 'init' method below.
					        ConnectionOverlays: [
					            [ "Arrow", { location: 1 } ],
					            [ "Label", {
					                location: 0.1,
					                id: "label",
					                cssClass: "aLabel"
					            }]
					        ],
					        Container: "storyboard",
					   });					
					
					return m_view;
				},
				
				tag_updateContext : function(){
					var contextNew = cloneContext(this.context);
					this.bodyResourceView.setContext(contextNew);
//					jsPlumbInstance.recalculateOffsets(m_view);
				},
				
				
		});

		return m_uiTagView;
	};

	