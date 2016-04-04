driverCheckApp.controller('loginController', ['$scope', '$http', 'navigationService', 'webServices',
  function ($scope, $http, navigationService, webServices) {
	$scope.userName = undefined;
	$scope.passWord = undefined;
	$scope.valid = true;
	
	$scope.login = function(){
		webServices.login($scope.userName, $scope.passWord).then(
			function(result){
				//successfull login
				$scope.valid = true;
				navigationService.forward('/clients');
			}, 
			function(reason){
				$scope.valid = false;
			}
		);
	};
}]);

