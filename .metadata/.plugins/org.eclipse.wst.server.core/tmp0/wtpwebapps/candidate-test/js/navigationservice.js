driverCheckApp.factory('navigationService', ['$location', function($location) {
	var loc_currentPage = undefined;
	var loc_historyPages = [];
	var loc_data = undefined;
	
	var loc_out = {
		forward : function(url, data){
			loc_data = data;
			if(loc_currentPage!=undefined)  loc_historyPages.push(loc_currentPage);
			loc_currentPage = url;
			$location.url(loc_currentPage);
		},
		
		backward : function(data){
			if(this.hasHistory()==false){
				loc_data = data;
				loc_currentPage = loc_historyPages.pop();
				$location.url(loc_currentPage);
			}
		},
		
		getData : function(){
			return loc_data;
		},
	
		hasHistory : function(){
			if(loc_historyPages.length==0)  return true;
			else return false;
		},
	};
	
	return loc_out;
}]);

