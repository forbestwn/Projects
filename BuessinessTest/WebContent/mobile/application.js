var appContext = {};

var getApplicationUnit = function(){
	return getContextPathWraper(appContext, {name:'application', path:'appui'}).data;
};

var getApplication = function(){
	return getContextPathWraper(appContext, {name:'application'}).data;
};

var createMobileApplicationFunction = function(parms, parent){
		
		var m_applicationInfo = {};
		var m_faceUIResource;
		
		var m_application;
		
		var that = new NosliwAppFunction('MobileApplication', parms, parent, 
		{
			m_doInit : function(){
				var that = this;
				
				Nosliw.init();

				m_application = MobileServiceManager.getApplication("default#application.application:appgood");
				processApplicationData(m_application);
				
				var requestGroup = NosliwRequestManager.createRequestGroup();

				requestGroup.addRequest(NosliwUIResourceManager.getRequestInfoCreateUIResourceView('application', {
					success : function(requestInfo, data){
						that.setData('applicationUI', data);
					},
				}));
				
				requestGroup.setHandler({
					endHandler : function(){
				 		var appResourceView = that.getData('applicationUI');
				 		$( "body" ).append( appResourceView.getViews() );		 		

				 		appContext = {};
				 		setContext(appContext, 'application', createObjectWraper(m_application));
				 		setContext(appContext, 'AppConfigure', createObjectWraper(AppConfigure));
				 		appResourceView.setContext(appContext);

				 		var startPointID = m_application.startpoint;
				 		var faceData = NavigateManager.navigation(startPointID, {}).data;
				 		setUnitDataWithEvent(m_application.appui, faceData, appContext);

				 		
						that.finishInit();
					},
				});	
				
				NosliwRequestManager.startRequestGroup(requestGroup);
			},
		
			m_postInit : function(){
			    $.mobile.initializePage();
			},
			
			m_doDestroy : function(){
			},
			
			m_doCommand : function(name, data){
				var that = this;
			},

			getStartPoint : function(){
				return m_applicationInfo.startPoint;
			},
			
			createFaceUIResourceView : function(){
				return  m_createUIResourceView(m_faceUIResource); 
			},
		});

		return that;
	};
	

