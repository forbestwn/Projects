	var m_createSwitchUITag = function(id, uiTag, uiResourceView){
		var m_uiTag = new NosliwUITag(id, uiTag, uiResourceView, 
		{
			tag_preInit : function(){
				this.m_processBodyResourceView();
			},

			tag_initViews : function(){
				return this.bodyResourceView.getViews();
			},
			
			tag_processAttribute : function(name, value){
				if(name=='evaluate'){
					var context = this.bodyResourceView.getContext();
					if(context!=undefined){
						requestSetContextPathValue(context, {name:'mylocal_variables', path:'matched'}, 'false');
						requestSetContextPathValue(context, {name:'mylocal_variables', path:'evaluate'}, value);
					}
				}
			},
			
			tag_updateContext : function(){
				var contextNew = cloneContext(this.context);
				
				setContext(contextNew, 'mylocal_variables', createObjectWraper({
					evaluate : this.getAttribute('evaluate'),
					matched : 'false',
				}));
				
				this.bodyResourceView.setContext(contextNew);
			},
		});
		return m_uiTag;
	};
	
	var m_createCaseUITag = function(id, uiTag, uiResourceView){
		var m_uiTag = new NosliwUITag(id, uiTag, uiResourceView, 
				{
					tag_updateView : function(){
						if(this.isEqualValue()){
							if(this.bodyResourceView==undefined){
								this.m_processBodyResourceView();
								var contextNew = cloneContext(this.context);
								this.bodyResourceView.setContext(contextNew);
							}
							this.startEle.after(this.bodyResourceView.getViews());
						}
						else{
							if(this.bodyResourceView!=undefined)		this.bodyResourceView.destroy();
							this.bodyResourceView = undefined;
						}
					},

					tag_preInit : function(){
						this.m_addDataBinding("evaluate", {name:'mylocal_variables', path:'evaluate'});
					},

					tag_handleDataEvent : function(code, contextPath, eventData){
						if(code==EVENT_DATA_CHANGE && contextPath.path=='evaluate'){
							this.m_updateView();
							if(this.isEqualValue()){
								requestSetContextPathValue(this.context, {name:'mylocal_variables', path:'matched'}, 'true');
							}
						}
					},
					
					tag_updateContext : function(){
						if(this.isEqualValue()){
							var contextNew = cloneContext(this.context);
							if(this.bodyResourceView!=undefined)		this.bodyResourceView.setContext(contextNew);
							requestSetContextPathValue(this.context, {name:'mylocal_variables', path:'matched'}, 'true');
						}
					},
					
					isEqualValue : function(){
						var caseValue = this.getAttribute('value');
						var evalValue = this.getDataBindingValue('evaluate');
						if(caseValue==evalValue)  return true;
						else return false;
					},
					
				});
		return m_uiTag;
	};
	
	var m_createDefaultUITag = function(id, uiTag, uiResourceView){
		var m_uiTag = new NosliwUITag(id, uiTag, uiResourceView, 
				{
					tag_updateView : function(){
						if(this.isMatched()){
							this.bodyResourceView.removeViews();
						}
						else{
							this.startEle.after(this.bodyResourceView.getViews());
						}
					},

					tag_preInit : function(){
						this.m_processBodyResourceView();
						
						this.m_addDataBinding("evaluate", {name:'mylocal_variables', path:'evaluate'});
						this.m_addDataBinding("matched", {name:'mylocal_variables', path:'matched'});
					},

					tag_handleDataEvent : function(code, contextPath, eventData){
						if(code==EVENT_DATA_CHANGE && contextPath.path=='evaluate'){
							this.m_updateView();
						}
					},
					
					tag_updateContext : function(){
						var contextNew = cloneContext(this.context);
						this.bodyResourceView.setContext(contextNew);
					},
					
					isMatched : function(){
						var matched = this.getDataBindingValue('matched');
						if(matched=='true')  return true;
						else return false;
					},
					
				});
		return m_uiTag;
	};
