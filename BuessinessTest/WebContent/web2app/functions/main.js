	var createMainFunction = function(parms, parent){
		
		var that = new NosliwAppFunction('Main', parms, parent, 
		{
			m_doInit : function(){

				Nosliw.requestInit({
					success:function(serviceTask, data){
						var dataSourceProviderFun = createDataSourceProviderFunction();
						var dataSourceListFun = createDataSourceListFunction();
						var storyboardFun = createStoryboardFunction();
						that.addSubFunction(dataSourceProviderFun);
						that.addSubFunction(dataSourceListFun);
						that.addSubFunction(storyboardFun);
					},
					fail : function(serviceTask, serviceData){
						alert(serviceData.code);
					},
					exception : function(serviceTask, serviceData){
						alert(serviceData.code);
					},
				});
			
			}
		});

		return that;
	};
	
