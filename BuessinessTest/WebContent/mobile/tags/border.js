/**
 * 
 */
	var m_createBorderUITag = function(id, uiTag, uiResourceView){
		
		var m_uiTag = new NosliwUITag(id, uiTag, uiResourceView, 
		{
			tag_clearOldContext : function(){
			},
			
			tag_updateContext : function(){
				var name = this.getAttribute('name');
				var resourceWraper = getContextPathWraper(this.context, {'name':'resource'}); 
				
				if(resourceWraper!=undefined){
					this.bodyResourceView = createUIResourceView(resourceWraper.data, this.m_id, this.parentResourceView);
					this.bodyResourceView.setContext(this.context);
					this.m_listenToBodyResourceViewEvent();
			 		this.startEle.after(this.bodyResourceView.getViews());
				}
			},
			
			tag_processAttribute : function(name, data){
				if(name=='command'){
					this.bodyResourceView.callFunction('command', data.name, data.data, this.bodyResourceView);
				}
			},

		});

		return m_uiTag;
	};
	
