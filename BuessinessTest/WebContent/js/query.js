var NosliwQueryManager = function(){
	var m_querys = {};
	
	var m_requester = {
			sourceType : 'system',
			sourceId : 'query',
	};
	
	var m_addQuery = function(queryContainer){
		_.extend(queryContainer, Backbone.Events);
		_.extend(queryContainer.container, Backbone.Events);
		m_querys[queryContainer.query.id] = queryContainer; 
		return queryContainer;
	};
	
	var manager = {
			getRequestInfoQuery : function(query, handler){
				var reqInfo = new NosliwServiceRequestInfo(m_requester, handler, NosliwServiceManager.getQueryService(query));
				reqInfo.setRequestExecuteInfo({
					method : this.requestInfoQuery,
					context : this,
				});
				reqInfo.setMetaData('query', query);
				return reqInfo;
			},	
			
			requestQuery : function(query, handlers){
				var requestInfo = this.getRequestInfoQuery(query, handlers);
				return this.requestInfoQuery(requestInfo);
			},

			requestInfoQuery : function(requestInfo){
				var query = requestInfo.getMetaData('query');

				requestInfo.setMetaHandler(mergeHandler(requestInfo.handler, {
					success : createRequestProcessorHandlerFunction(requestInfo.handler.success, function(queryContainerWraper, reqInfo){
						queryContainerWraper.container = new NosliwOrderedContainer(queryContainerWraper.data, {categary:"entity"});
						var entitys = queryContainerWraper.data;
						for (var key in entitys) {
						    if (entitys.hasOwnProperty(key)) {
								_.extend(entitys[key], Backbone.Events);
						    }
						}
						m_addQuery(queryContainerWraper);
						return queryContainerWraper;
					}), 	
				}));
				
				var task = NosliwServiceManager.getQueryServiceTask(query, requestInfo); 
				NosliwRemoteServiceTaskManager.addServiceTask(task);
			},
			
		addQueryEntity : function(query, ID, entity, request){
			if(m_querys[query]!=undefined){
				_.extend(entity, Backbone.Events);
				m_querys[query].container.addData(entity);
				m_querys[query].trigger(EVENT_DATA_CHANGE+':'+EVENT_ELEMENT_ADD, {ID:ID, data:entity}, request);
			}
		},
		
		removeQueryEntity : function(query, ID, request){
			if(m_querys[query]!=undefined){
				m_querys[query].container.removeData(ID);
				m_querys[query].trigger(EVENT_DATA_CHANGE+':'+EVENT_ELEMENT_REMOVE, {ID:ID, data:ID}, request);
			}
		},

		modifyQueryEntity : function(query, ID, entity, request){
			if(m_querys[query]!=undefined){
				var queryEntityWraper = m_querys[query].container.updateData(entity, ID);
				queryEntityWraper.trigger(EVENT_DATA_CHANGE+':'+EVENT_REFRESH, {}, request);
			}
		},
	};
	return manager;
}();


function getQueryEntityAttributeWraperByPath(wraper, path) {
	return wraper.data[path];
}

function registerQueryContextEvent(context, contextPath, action, code){
	var contextValue = context[contextPath.name];
	var contextPathEventInfo = {
		'data' : contextValue,
		'expectPath' : contextPath, 
	};
	
	_.extend(contextPathEventInfo, Backbone.Events);
	contextPathEventInfo.listenTo(contextValue.data, EVENT_DATA_CHANGE+':'+EVENT_REFRESH, function(eventData, requestInfo){
		action.call(this, EVENT_REFRESH, contextPath, eventData, requestInfo);
	}); 
	return contextPathEventInfo;
};

var unregisterQueryContextEvent = function(contextPathEventInfo){
	contextPathEventInfo.stopListening();
};

