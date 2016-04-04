var AppManager = function(){
	var m_applicationEntity = {};

	
	var manager = {
		getApplicationEntity : function(){
			return m_applicationEntity;
		},
		
		setApplicationEntity : function(app){
			m_applicationEntity = app;
		},
	};
	return manager;
}();

