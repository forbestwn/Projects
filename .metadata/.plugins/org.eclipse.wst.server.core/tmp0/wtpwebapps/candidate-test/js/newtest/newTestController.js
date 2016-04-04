driverCheckApp.controller('newTestController', ['$scope', '$http', 'navigationService', 'webServices',
  function ($scope, $http, navigationService, webServices) {
	$scope.result = "pass";
	$scope.submit = function(){
		date = new Date();
		milliseconds = date.getTime();
		var test = {
			name : $scope.name,
			result : $scope.result,
			date : new Date().getTime(),
			client : navigationService.getData().client,
			employee : navigationService.getData().employee,
		};
		webServices.newTest(test).then(
				function(test){
					navigationService.backward();
				}, 
				function(reason){
				}
		);
		
	};

	$scope.backward = function(){
		navigationService.backward();
	};
  }]);

