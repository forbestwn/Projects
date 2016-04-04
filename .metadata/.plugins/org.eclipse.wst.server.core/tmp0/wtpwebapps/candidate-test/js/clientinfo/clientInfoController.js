driverCheckApp.controller('clientInfoController', ['$scope', '$http', '$routeParams', 'navigationService', 'webServices',
  function ($scope, $http, $routeParams, navigationService, webServices) {
	var loc_id = $routeParams.client;
	
	webServices.getClient(loc_id).then(
			function(client){
				$scope.client = client;
			}, 
			function(reason){
			}
	);
	
	$scope.query = "";
	$scope.ifSearch = false;
	
	$scope.backward = function(){
		navigationService.backward();
	};
	
	$scope.newEmployee = function(){
		navigationService.forward('newemployee', loc_id);
	};
	
	$scope.employeeInfo = function(id){
		navigationService.forward('/employee/'+id);
	};
	
	$scope.deleteEmployee = function(id){
		webServices.deleteEmployee(id).then(
				function(employee){
					delete $scope.client.employees[id];
				}, 
				function(reason){
				}
		);
	};	
	
	$scope.filter = function(value, index, array){
		if($scope.ifSearch==false)   return true;
		else{
			if(value.name.indexOf($scope.query)==-1){
				return false;
			}
			else{
				return true;
			}
		}
	};
	
  }]);

