/**
 * 
 */
var MobileServiceManager = function(){

	var successData;
	
	function serviceCall (command, data){
		if(data==undefined)  data = {};
		
		var dataStr = JSON.stringify(data);
		var serviceData = {'command':command, 'parms':dataStr};
		
		$.ajax({
			url : "mobileService",
			type : "GET",
			dataType: "json",
			data : serviceData,
			async : false,
			success : function(d, status){
				successData = d;
			},
			error: function(obj, status, data){
				successData = obj;
			}
		});		
	};
	
	var manager = {
		appInit : function(application){
			serviceCall("appInit", {});
			return successData;
		},

		getApplication : function(appID){
			var data = {
				applicationID : appID,
			};
			serviceCall("getApplication", data);
			return successData;
		},
		
		getFace : function(applicationID, facePath){
			var data = {
				applicationID : applicationID,
				facePath : facePath,
			};
			serviceCall("getFace", data);
			return successData;
		},

		getTemplate : function(templateID){
			var data = {
				uiTemplateId : templateID,
			};
			serviceCall("getUITemplate", data);
			return successData;
		},
		
		navigation : function(startPoint, startData, extra){
			var navData = jQuery.extend({}, startData, extra);
			
			var data = {
				faceStartPoint : startPoint,
				faceStartData : navData,	
			};
			serviceCall("navigation", data);
			return successData;
		},

	};
	return manager;
	
}();
