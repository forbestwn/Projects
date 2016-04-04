
	var m_createUIFaceUITag = function(id, uiTag, uiResourceView){
		
		var m_uiTagView = new NosliwUITag(id, uiTag, uiResourceView, 
		{
				m_view : {},
				m_faceView : {},
				m_facePath : '',
			
				tag_initViews : function(){
					this.m_view = $('<div style="top:0px; left:0px; position:absolute;width: 100px;height: 200px;border: 1px solid #000000;"/>');
					return this.m_view;
				},
				
				tag_updateContext : function(){
			        var that = this;

			        var faceUI = this.getDataBindingValue();
					this.m_facePath = getFacePathByFaceID(faceUI.face.data);
					jsPlumbInstance.setId(this.m_view[0], this.m_facePath);					
					
					var x = faceUI.x.data;
					var y = faceUI.y.data;
					this.m_view.css({top: y, left: x});
					
			        var paper = Raphael(this.m_view[0]);	
			        this.m_infoView = paper.rect(50, 50, 50, 50).attr({ fill: '#0000FF', stroke: '#000000', 'stroke-width': 8 });
			        this.m_emulView = paper.rect(50, 100, 50, 50).attr({ fill: '#FF0000', stroke: '#000000', 'stroke-width': 8 });
			        this.m_deleteView = paper.rect(50, 150, 50, 50).attr({ fill: '#00FF00', stroke: '#000000', 'stroke-width': 8 });
			        this.m_infoView.click(function(){
			        	that.m_triggerEvent("faceInfo", {faceUI:faceUI, facePath:that.m_facePath});
			        });
			        this.m_emulView.click(function(){
			        	that.m_triggerEvent("emulateFace", {faceUI:faceUI, facePath:that.m_facePath});
			        });
			        
			        jsPlumbInstance.draggable(this.m_view[0], {
			        	  start:function(params) {
			        	  },
			        	  drag:function(params) {
			        	  },
			        	  stop:function(params) {
			        		  	var x = that.m_view.css("left");
			        		  	var y = that.m_view.css("top");
			        		  	
								var requestInfo = that.m_startRequest();
								requestInfo.addRequestOperation({
									'operation' : 'set',
									'context' : that.context,
									'contextPath' : {name:'face', path:'x'},
									'data' : x,
								});						
								requestInfo.addRequestOperation({
									'operation' : 'set',
									'context' : that.context,
									'contextPath' : {name:'face', path:'y'},
									'data' : y,
								});						
								that.m_submitRequest(requestInfo);
			        	  },
			        });
			        
				},
		});

		return m_uiTagView;
	};

	