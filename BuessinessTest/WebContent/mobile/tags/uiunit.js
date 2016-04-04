	var m_createUIUnitUITag = function(id, uiTag, uiResourceView){
		
		var m_uiUnit;
		var m_templates;
		var m_template;
		var m_datas;
		
		var m_getObjectPathByUIPath = function(contextPath, uiPath){
			var out = contextPath;
			if(!isStringEmpty(uiPath)){
				var uiPathSegs = uiPath.split(".");
				for(var i in uiPathSegs){
					var uiPathSeg = uiPathSegs[i];
						var segs = uiPathSeg.split("|");
						var segType = segs[0];
						if(segType=="component"){
							out = out + ".configure.components."+segs[1]+".uiunit";
						}
						else if(segType=="componentcontainer"){
							out = out + ".configure.componentcontainers."+segs[1]+".uiunits."+segs[2]+".uiunit";
						}
				}
			}
			return out;
		};
		
		var m_uiTag = new NosliwUITag(id, uiTag, uiResourceView, 
		{
			m_getParentUIUnit : function(){
				var resourceView = this.parentResourceView;
				var tagInfo = resourceView.getParentTagInfo();
				while(tagInfo!=undefined && tagInfo.tagName!="uiunit"){
					resourceView = resourceView.getParentResourceView();
					tagInfo = resourceView.getParentTagInfo();
				}
				if(tagInfo==undefined)  return;
				return resourceView;
			},
			
			m_getUIUnitByPath : function(uipath, base){
				var path = uipath.substring(base.length+1);
				if(isStringEmpty(path)){
					return this;
				}
				else{
					var pathSegs = path.split(".");
					var tag = this;
					for(var i in pathSegs){
						tag = getTagsByNameAttribute("uiunit", "subpath", pathSegs[i]);
					}
					return tag;
				}
			},
			
			
			processInputDatas : function(){
				
			},
			
			tag_preInit : function(){
				var that = this;
			},

			tag_updateContext : function(){

				var unitContextPath = this.getAttribute('unit');
				if(isStringEmpty(unitContextPath)){
					unitContextPath = "unit"
				}
				var unitPath = this.getAttribute('path');
				
		 		var uiunitContextPath = m_getObjectPathByUIPath(unitContextPath, unitPath);
		 		var templateContextPath = this.getAttribute('templates');
		 		if(isStringEmpty(templateContextPath)==true){
		 			templateContextPath = "templates";
		 		}
				var dataContextPath = this.getAttribute('datas');
		 		if(isStringEmpty(dataContextPath)==true){
		 			dataContextPath = "unit.configure.datas";
		 		}
		 		
		 		var tagContext = {};
		 		tagContext.unit = createSubContextElement(this.context, uiunitContextPath);
		 		tagContext.templates = createSubContextElement(this.context, templateContextPath);
		 		tagContext.datas = createSubContextElement(this.context, dataContextPath);
		 		tagContext.AppConfigure = createSubContextElement(this.context, "AppConfigure");
		 		
		 		m_uiUnit = getContextPathWraper(tagContext, {name:"unit"}).data;
		 		if(m_uiUnit!=undefined){
		 			this.setAttribute("uipath", m_uiUnit.uipath);
		 			this.setAttribute("subpath", m_uiUnit.subpath);
		 			
			 		m_templates = getContextPathWraper(tagContext, {name:"templates"}).data;
			 		m_template = m_templates[m_uiUnit.template];
			 		
					this.m_processBodyResourceView(m_template.uiresource);
					this.startEle.after(this.bodyResourceView.getViews());
					this.bodyResourceView.setContext(tagContext);
					
					var that = this;
					this.bodyResourceView.on('faceevent', function(eventData){
						var unitPath = m_uiUnit.uipath;
						if(isStringEmpty(eventData.path)){
							eventData.path = unitPath;
						}
						
						var eventtunnels = m_uiUnit.configure.eventtunnels;
						var eventtunnel = eventtunnels[eventData.path+".event|"+eventData.event];
						if(eventtunnel!=undefined){
							var inDataContext = eventData.contextData;
							if(inDataContext==undefined)  inDataContext={};
							inDataContext[eventData.path+".event|"+eventData.event+"|data"] = eventData.data;
							
							var outDataContext;
							for(var k in eventtunnel.datatunnels){
								var datatunnel = eventtunnel.datatunnels[k];
								outDataContext = handleDataTunnel(datatunnel, inDataContext, outDataContext);
							}
							
							var commandPath = eventtunnel.commandpath;
							var k = commandPath.lastIndexOf(".");
							var commandInfos = commandPath.substring(k+1).split("|");
							var commandUnitPath = commandPath.substring(0, k);
							
							var commandUIUnit = that.m_getUIUnitByPath(commandUnitPath, unitPath);
							var commandDatas = {};
							for (var id in outDataContext) {
							    if (outDataContext.hasOwnProperty(id)) {
							    	var commandParmData = outDataContext[id];
									var n = id.lastIndexOf(".");
									var parmName = id.substring(n+1).split("|")[2];
									commandDatas[parmName] = commandParmData;
							    }
							}
							
							var extraData = {};
							var extraEndPoints = eventtunnel.extra;
							for(var m in extraEndPoints){
								extraData[extraEndPoints[m].ID] = inDataContext[extraEndPoints[m].ID];
							}
							
							commandUIUnit.setAttribute("command", {command: commandInfos[1], data:commandDatas, extra:extraData});
						}
						
						var parentUiUnitResource = that.m_getParentUIUnit();
						if(parentUiUnitResource!=undefined){
							parentUiUnitResource.trigueEvent('faceevent', eventData);
						}
					});
		 		}
			},
			
			tag_processAttribute : function(name, data){
				if(name=='command'){
					this.bodyResourceView.callFunction('command', data.command, data.data, data.extra, this.bodyResourceView);
				}
			},
		});

		return m_uiTag;
	};
	
