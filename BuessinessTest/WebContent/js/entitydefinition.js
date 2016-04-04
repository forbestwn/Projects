var NosliwEntityDefinitionManager = function(){

	var m_entityDefinitions = {};

	var m_requester = {
		sourceType : 'system',
		sourceId : 'entitydefinition',
	};
		
	var m_createInitRequestInfo = function(){
		var reqInfo = {
			id : Nosliw.generateId(),
			requester : m_requester,
			service : {
				command : 'getAllEntityDefinitions',
				data : {},
			},
			success : m_initHandler,
			error : m_initHandler,
			exception : m_initHandler,
		};
		return reqInfo;
	};
	
	var m_initHandler = function(data, requestInfo){
		m_entityDefinitions = data;
	};
	
	var manager = {
		init : function(entityDefs){
			m_entityDefinitions = entityDefs;
		},	
		
		requestInit : function(){
			m_entityDefinitions = {};
			var request = NosliwServiceManager.getServiceRequest(m_createInitRequestInfo());
			return request;
		},
			
		getAllEntityDefinitions : function(){
			return m_entityDefinitions;
		},
		
		getEntityDefinitionsByGroup : function(group){
			
		},

		getEntityDefinitionByName : function(name){
			return m_entityDefinitions[name];
		},
	};
	return manager;
}();
