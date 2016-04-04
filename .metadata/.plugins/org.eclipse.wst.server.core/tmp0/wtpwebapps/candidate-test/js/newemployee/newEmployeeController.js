driverCheckApp.controller('newEmployeeController', ['$scope', '$http', 'navigationService', 'webServices',
  function ($scope, $http, navigationService, webServices) {
	$scope.gender = "male";
	$scope.submit = function(){
		var employee = {
			name : $scope.name,
			gender : $scope.gender,
			age : $scope.age,
			client : navigationService.getData(),
		};
		webServices.newEmployee(employee).then(
				function(employee){
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

