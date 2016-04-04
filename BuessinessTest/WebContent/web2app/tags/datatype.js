
	var m_createDatatypeUITag = function(id, uiTag, uiResourceView){
		var m_paper;
		var m_parentEle;
		
		var m_width = 50;
		var m_height = 50;
		var m_gapX = 150;
		var m_gapY = 150;
		
		var m_drawDataType1 = function(datatypeEntity, x, y){
			var edgeY = 0;
			var type = datatypeEntity.type.data;
			if(type=='map'){
				var ele = $("<div style='position: absolute; background-color:red; width:50px; height:50px; visibility:visible;'/>");
				ele.css({left:x+'px', top:y+'px'});
				m_parentEle.append(ele);
				
				edgeY = y;
				var mapData = datatypeEntity.childtypes.data;
				for (var property in mapData) {
				    if (mapData.hasOwnProperty(property)) {
				    	var childMapData = mapData[property].data.datatypeinfo.data.datatype.data;
				    	var childEntity = m_drawDataType1(childMapData, x+m_width+m_gapX, edgeY);
				    	edgeY = childEntity.edgeY;
					   jsPlumb.connect({
						   source:ele[0], 
						   target:childEntity.ele[0],
						   connector : "Straight",
						   overlays: [
						       "Arrow",  [ "Label", { label:property, location:50, id:"myLabel" } ],
						   ],
						   anchors:["Right", "Left" ],
					   });
				    }
				}
				return {
					edgeY : edgeY,
					ele : ele,
				};
			}
			else if(type=='table'){
				
			}
			else if(type=='atom'){
//				m_paper.rect(x, y, m_width, m_height).attr('fill', 'green');
				var ele = $("<div style='position: absolute; background-color:red; width:50px; height:50px; visibility:visible;'/>");
				ele.css({left:x+'px', top:y+'px'});
				m_parentEle.append(ele);
				return {
					edgeY : y+m_height+m_gapY,
					ele : ele,
				};
			}
			else{
				var ele = $("<div style='position: absolute; background-color:red; width:50px; height:50px; visibility:visible;'/>");
				ele.css({left:x+'px', top:y+'px'});
				m_parentEle.append(ele);
				return {
					edgeY : y+m_height+m_gapY,
					ele : ele,
				};
			}
		};
		
		var m_drawDataType = function(datatypeEntity, x, y){
			var edgeY = 0;
			var type = datatypeEntity.type.data;
			if(type=='map'){
				m_paper.rect(x, y, m_width, m_height).attr('fill', 'red');
				edgeY = y;
				var mapData = datatypeEntity.childtypes.data;
				for (var property in mapData) {
				    if (mapData.hasOwnProperty(property)) {
				    	var childMapData = mapData[property].data.datatypeinfo.data.datatype.data;
				    	edgeY = m_drawDataType(childMapData, x+m_width+m_gapX, edgeY);
				    }
				}
				return edgeY;
			}
			else if(type=='table'){
				
			}
			else if(type=='atom'){
				m_paper.rect(x, y, m_width, m_height).attr('fill', 'green');
				return y+m_height+m_gapY;
			}
			else{
				m_paper.rect(x, y, m_width, m_height).attr('fill', 'green');
				return y+m_height+m_gapY;
			}
		};
		
		var m_uiTagView = new NosliwUITag(id, uiTag, uiResourceView, 
		{
			tag_preInit : function(){
			},
			
			tag_initViews : function(){
					var parentid = this.getAttribute('parentid');
					var conEle = this.parentResourceView.getDomElementByAttributeValue("id", parentid);
					
					var width = 300;
					var height = 300;

					m_parentEle = $("<div id='22222' style='position: relative; background-color:green; width:500px; height:500px'/>");
					$(conEle).append(m_parentEle);
					
//					m_paper = Raphael(parentEle, width, height);
//					m_paper.rect(0, 0, width, height);
			},

			tag_updateContext : function(){
				var datatypeEntity = this.getDataBindingValue();
				
				jsPlumb.setSuspendDrawing(true);
				m_drawDataType1(datatypeEntity, 0, 0);
				
				jsPlumb.setSuspendDrawing(false, true);
				
//				m_drawDataType(datatypeEntity, 0, 0);
			},
		});

		return m_uiTagView;
	};

