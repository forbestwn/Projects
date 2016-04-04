/**
 * 
 */

var NavigateManager = function(){
	
	var m_commandNavMap = {};
	
	var manager = {
		init : function(map){
			m_commandNavMap = map;
		},
			
		getNavigationByCommand : function(commandID){
			return m_commandNavMap[commandID];
		},
		
		navigation : function(startPointID, commandData){
			return MobileServiceManager.navigation(startPointID, commandData);
			
		},		

		navigationTo : function(startPointID, commandData, extra){
			var faceData = MobileServiceManager.navigation(startPointID, commandData, extra);
	 		setUnitDataWithEvent(getApplicationUnit(), faceData.data, appContext);
	 		var faceUnitID = faceData.face.faceUnitID;
	 		$.mobile.changePage($(document.getElementById(faceUnitID)));
		},		
		
		navigate : function(commandID, commandData){
			var navigationID = NavigateManager.getNavigationByCommand(commandID);
			
			var data = {
					navigateID : navigationID,
					commandID : commandID,
					cmmandData : commandData,
			};
			var dataStr = JSON.stringify(data);
			var serviceData = {'command':"navigate", 'parms':dataStr};
			
			$.mobile.changePage('mobileService', {
				method : 'post',
				type : 'post',
				data : serviceData,
			});
		},
	};
	return manager;
}();

