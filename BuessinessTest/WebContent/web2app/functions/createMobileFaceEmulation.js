var appContext = {};

var getApplicationUnit = function(){
	return getContextPathWraper(appContext, {name:'application', path:'appui'}).data;
};

var getApplication = function(){
	return getContextPathWraper(appContext, {name:'application'}).data;
};

var createMobileFaceEmulation = function(parms, parent){
		
		var m_applicationInfo = {};
		var m_faceUIResource;
		
		var m_application;
		
		var that = new NosliwAppFunction('MobileFaceEmulation', parms, parent, 
		{
			m_doInit : function(){
				var that = this;
				
				Nosliw.init();

				if(parms.applicationID==undefined)  parms.applicationID = "default#application.application:appgood"; 
				m_application = MobileServiceManager.getFace(parms.applicationID, parms.facePath);
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
				 		setContext(appContext, 'AppConfigure', createObjectWraper({
				 			emulation : 'true',
				 		}));
				 		appResourceView.setContext(appContext);

						that.finishInit();
					},
				});	
				
				NosliwRequestManager.startRequestGroup(requestGroup);
			},
		
			m_postInit : function(){
			    $.mobile.initializePage();
			},
		});

		return that;
	};
	