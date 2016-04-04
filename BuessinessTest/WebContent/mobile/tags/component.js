/**
 * 
 */
	var m_createComponentUITag = function(id, uiTag, uiResourceView){
		
		var m_uiTag = new NosliwUITag(id, uiTag, uiResourceView, 
		{
			tag_clearOldContext : function(){
			},
			
			tag_updateContext : function(){
				var resourceWraper = getContextPathWraper(this.context, {'name':'resource'}); 
				if(resourceWraper!=undefined){
					this.bodyResourceView = createUIResourceView(resourceWraper.data, this.m_id, this.parentResourceView);
					this.bodyResourceView.setContext(this.context);
					this.m_listenToBodyResourceViewEvent();
			 		this.startEle.after(this.bodyResourceView.getViews());
				}
			},
			
			tag_handleBodyResourceView : function(event, data){
				if(event=='face'){
					data.uiid = this.getAttribute("uiid");
					this.m_triggerEvent(event, data);
				}
			},
			
			tag_postInit : function(){
			},

			tag_processAttribute : function(name, data){
				if(name=='command'){
					this.bodyResourceView.callFunction('command', data.name, data.data.parms, data.data.extra, this.bodyResourceView);
				}
			},
			
		});

		return m_uiTag;
	};
	
