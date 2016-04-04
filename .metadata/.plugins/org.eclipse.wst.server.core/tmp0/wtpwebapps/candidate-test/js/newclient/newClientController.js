driverCheckApp.controller('newClientController', ['$scope', '$http', 'navigationService', 'webServices',
  function ($scope, $http, navigationService, webServices) {
	$scope.submit = function(){
		var client = {
			name : $scope.name,
			address : $scope.address,
			phone : $scope.phone,
		};
		webServices.newClient(client).then(
				function(client){
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

