var Nosliw = function(){
	
	var m_status = "new";
	
	var m_transactionId;
	var m_idRoot = 1;
	
	var m_requester = {
		'sourceType' : 'system',
		'sourceId' : 'application',
	};

	var manager = {
		init : function(){
			
		},	
		
		getRequestInfoInit : function(handler){
			var reqInfo = new NosliwServiceRequestInfo(m_requester, handler, {
				command : 'init',
				data : {},
			});
			reqInfo.setRequestExecuteInfo({
				method : this.requestInfoInit,
				context : this,
			});
			return reqInfo;
		},	
		
		requestInit : function(handlers){
			var requestInfo = this.getRequestInfoInit(handlers);
			return this.requestInfoInit(requestInfo);
		},
		
		requestInfoInit : function(requestInfo){
			var request1 = NosliwServiceManager.getGetAllEntityDefinitionsServiceTask(requestInfo, {
				success : function(serviceTask, data){
					NosliwEntityDefinitionManager.init(data);
				},
			});
			
			var request2 = NosliwServiceManager.getGetAllDataTypesServiceTask(requestInfo, {
				success : function(serviceTask, data){
					NosliwDataTypeManager.initDataType(data);
				},
			});
			
			var groupTask = new NosliwRemoteServiceGroupTask(requestInfo);
			groupTask.addTask(request1);
			groupTask.addTask(request2);
			groupTask.setHandler(requestInfo.handler);
			NosliwRemoteServiceTaskManager.addServiceTask(groupTask);
			return requestInfo;
		},
		
		getTransactionId : function(){
			return m_transactionId;
		},
		
		setTransactionId : function(id){
			m_transactionId = id;
		},
		
		generateId : function(){
			return m_idRoot++;
		},
	};
	return manager;
}();
