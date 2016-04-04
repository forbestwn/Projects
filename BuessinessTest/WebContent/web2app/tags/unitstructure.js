
	var m_createUnitStructureUITag = function(id, uiTag, uiResourceView){
		var m_view;
		var m_paper;
		var m_facePath;
		
		var m_lineHeight = 20;
		var m_lineLength = 100;
		var m_heightSpace = 120;
		var m_lineXShift = 30;

		var m_nodeSize = 70;
		var m_menuItemSize = m_nodeSize/4;
		
		var m_pageWidth = 750;
		var m_pageHeight = 400;
		
		
		var m_getTemplateEntity = function(templateID){
    		var templateEntity = NosliwEntityManager.getEntity(templateID);
			return templateEntity;
		};
		
		var m_drawUnUnitChildren = function(uiUnit, element, unitPath){
	    	var comBox = element.getBBox();
	    	var startX = comBox.x2;
	    	var startY = (comBox.y+comBox.y2)/2;
	    	var lastY = startY;

			var templateEntity = m_getTemplateEntity(uiUnit.template.data);
			var templateUnit = templateEntity.data.info.data;
			
			var components = templateUnit.components.data;
			var coms = uiUnit.configure.data.components.data;
			var componentcontainers = templateUnit.componentcontainers.data;

			if(components.length==0&&componentcontainers.length==0)  return;
	    	m_paper.rect(startX, lastY-m_lineHeight/2, m_lineXShift, m_lineHeight).attr({ fill: '#00EE00', stroke: '#000000', 'stroke-width': 0});
	    	startX = startX + m_lineXShift;
	    	
			var i = 0;
			for (var id in components) {
			    if (components.hasOwnProperty(id)) {
			    	var component = components[id].data;
			    	var name = component.name.data;
			    	var path = unitPath + '.component|'+name;
			    	if(i!=0){
				    	m_paper.rect(startX, lastY+m_lineHeight/2, m_lineHeight, m_heightSpace).attr({ fill: '#00EE00', stroke: '#000000', 'stroke-width': 0});
				    	lastY = lastY + m_heightSpace;
			    	}
			    	m_paper.rect(startX, lastY-m_lineHeight/2, m_lineLength, m_lineHeight).attr({ fill: '#00EE00', stroke: '#000000', 'stroke-width': 0});
			    	m_paper.text(startX+m_lineLength/2, lastY, name);

			    	var com = undefined;
					for (var cName in coms) {
					    if (coms.hasOwnProperty(cName)) {
					    	if(name==coms[cName].data.name.data){
					    		com = coms[cName].data.uiunit.data;
					    		break;
					    	}
					    }
					}
			    	if(com!=undefined){
				    	var unitEle = m_drawComponent(com, startX+m_lineLength, lastY, path); 
				    	lastY = m_drawUnUnitChildren(com, unitEle, path);
			    	}
			    	else{
				    	var unitEle = m_drawTemplateComponent(startX+m_lineLength, lastY, path); 
			    	}
			    	
			    	i++;
			    }
			}
			
			return lastY;
		};
		
		var m_drawComponent = function(com, x, y, path){
			var that = m_uiTagView;
			var componentEle = m_paper.rect(x, y-m_nodeSize/2, m_nodeSize, m_nodeSize).attr({ fill: '#3D6AA2'});
			
			var i = 0;
			var componentInfoEle = m_paper.rect(x+m_nodeSize-m_menuItemSize, y-m_nodeSize/2+(m_menuItemSize+10)*i, m_menuItemSize, m_menuItemSize).attr({ fill: '#DDDDDD'});
			componentInfoEle.click(function(){
	        	that.trigger("unitInfo", {
	        		unitPath : path,
	        	});
			});

			i = 1;
			var componentEmulEle = m_paper.rect(x+m_nodeSize-m_menuItemSize, y-m_nodeSize/2+(m_menuItemSize+10)*i, m_menuItemSize, m_menuItemSize).attr({ fill: '#DDDDDD'});

			i = 2;
			var componentXXXEle = m_paper.rect(x+m_nodeSize-m_menuItemSize, y-m_nodeSize/2+(m_menuItemSize+10)*i, m_menuItemSize, m_menuItemSize).attr({ fill: '#DDDDDD'});
			
			return componentEle;
		}

		var m_drawTemplateComponent = function(x, y, path){
    		return m_paper.circle(x+ m_nodeSize/2, y, m_nodeSize/2).attr({ fill: '#FF0000'});
		}
		
		var m_uiTagView = new NosliwUITag(id, uiTag, uiResourceView, 
		{
			tag_preInit : function(){
				this.m_processBodyResourceView();
			},

			tag_initViews : function(){
				m_view = $('<div id="unitstructure" style="position: relative;width: '+m_pageWidth+'px;height: '+m_pageHeight+'px;border: 1px solid #000000;"/>');
		        m_paper = Raphael(m_view[0], m_pageWidth, m_pageHeight);	
				return m_view;
			},
				
			tag_updateContext : function(){
				var unitContextPath = this.getAttribute('unit');
		 		m_uiUnit = getContextPathWraper(this.context, unitContextPath).data;

		 		m_facePath = getContextPathWraper(this.context, 'facePath').data.data;
		 		
		    	var unitEle = m_drawComponent(m_uiUnit, 10, 70); 
				m_drawUnUnitChildren(m_uiUnit, unitEle, m_facePath);
			},
		});

		_.extend(m_uiTagView, Backbone.Events);
		return m_uiTagView;
	};

	