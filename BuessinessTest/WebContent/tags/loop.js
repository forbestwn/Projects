	var m_createContainerUITag = function(id, uiTag, uiResourceView){
		
		var m_childResourceViews = {};
		
		var m_uiTag = new NosliwUITag(id, uiTag, uiResourceView, 
		{
			tag_clearOldContext : function(){
				for (var key in m_childResourceViews) {
				    if (m_childResourceViews.hasOwnProperty(key)) {
				    	m_removeEle(key);
				    }
				}
			},
			
			tag_updateContext : function(){
				var context1 = this.context;
				handleContextContainerEachElement(this.context, this.dataBindings.data, function(key, eleWraper, eleContext){
					m_addEle.call(this, key, eleWraper, eleContext);
				}, this);
			},

			tag_handleDataEvent : function(code, contextPath, eventData){
				if(code==EVENT_ELEMENT_ADD){
			    	var contextEle = createContextElement(eventData.data); 
					m_addEle.call(this, eventData.ID, eventData.data, contextEle);
				}
				else if(code==EVENT_ELEMENT_REMOVE){
					m_removeEle.call(this, eventData.ID);
				}
				else if(code==EVENT_DATA_CHANGE){
					handleContextContainerEachElement(this.context, this.dataBindings.data, function(key, eleWraper, eleContext){
						m_addEle.call(this, key, eleWraper, eleContext);
					}, this);
				}
			},
		});


		var m_addEle = function(key, dataWraper, eleContext){
			
	    	var eleResourceView = this.m_createUIResourceView(m_uiTag.parentResourceView.getIdNameSpace()+"."+key); 
//	    		createUIResourceView(m_uiTag.uiTag, m_uiTag.parentResourceView.getIdNameSpace()+"."+key, m_uiTag.parentResourceView);
	    	eleResourceView.key = key;
	    	m_childResourceViews[key] = eleResourceView;
	    	
	 		m_uiTag.startEle.after(eleResourceView.getViews());
	    	
	    	var eContext;
	    	
	 		var contextEleName = m_uiTag.getAttribute('context');
	 		var context1 = {};
	 		context1[contextEleName] = eleContext;

	 		context1['index'] = createContextElementObject(createObjectWraper(key+''));

	 		var otherContextInfosAttr = m_uiTag.getAttribute('othercontext');
	 		if(!isStringEmpty(otherContextInfosAttr)){
		 		var otherContextInfos = otherContextInfosAttr.split(";");
		 		for(var i in otherContextInfos){
		 			var infoSegs = otherContextInfos[i].split("@");
		 			var contextName = infoSegs[0];
		 			var contextPath = contextName;
		 			var clone = false;
		 			if(!isStringEmpty(infoSegs[1])){
		 				contextPath = infoSegs[1];
		 			}
		 			if(!isStringEmpty(infoSegs[2])){
		 				var type = infoSegs[2];
		 				if(type=="clone")  clone=true;
		 				else clone = false;
		 			}
			 		context1[contextName] = createSubContextElement(this.context, contextPath, clone);
		 		}
		 		eContext = context1;
	 		}
	 		else{
		 		eContext = mergeContext(this.context, context1);
	 		}
	 		eleResourceView.setContext(eContext);
	 		
			this.m_triggerEvent("addElement", {
				key : key,
				context : eContext,
			});
		};
		
		
		var m_removeEle = function(key){
			m_childResourceViews[key].destroy();
			delete m_childResourceViews[key];
		};

		return m_uiTag;
	};
	