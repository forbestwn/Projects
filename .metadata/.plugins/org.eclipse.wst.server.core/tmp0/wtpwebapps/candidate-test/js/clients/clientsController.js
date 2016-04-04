driverCheckApp.controller('clientsController', ['$scope', '$http', 'navigationService', 'webServices',
  function ($scope, $http, navigationService, webServices) {
	
	webServices.getClients().then(
			function(clients){
				$scope.clients = clients;
			}, 
			function(reason){
			}
	);
	
	$scope.newClient = function(){
		navigationService.forward('/newclient');
	};
	
	$scope.clientInfo = function(id){
		navigationService.forward('/client/'+id);
	};
	
	$scope.deleteClient = function(id){
		webServices.deleteClient(id).then(
				function(clients){
					delete $scope.clients[id];
				}, 
				function(reason){
				}
		);
	};
	
  }]);

