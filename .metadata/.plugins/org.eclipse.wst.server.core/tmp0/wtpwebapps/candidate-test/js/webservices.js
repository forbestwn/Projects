driverCheckApp.factory('webServices', ['$q', '$http', function($q, $http) {
	var loc_q = $q;
	var loc_http = $http;
	var loc_out = {
		login : function(userName, passWord){
			var defer = loc_q.defer();
			var promise = defer.promise;
			
			if(userName===passWord){
				defer.resolve();
			}
			else{
				defer.reject();
			}
			return promise;
		},
		
		getClients : function(){
			return $http({
				  method: 'GET',
				  url: 'rest/clients'
			}).then(function(response){
				return response.data;
			}, function(response){
				return "";
			});
		},

		getClient : function(id){
			return $http({
				  method: 'GET',
				  url: 'rest/client/'+id
			}).then(function(response){
				return response.data;
			}, function(response){
				return "";
			});
		},
		
		newClient : function(clientEntity){
			return $http({
				  method: 'POST',
				  url: 'rest/client',
				  data: clientEntity,   
				  headers: {'Content-Type': 'application/json'}				  
			}).then(function(response){
				return response.data;
			}, function(response){
				return "";
			});
		},
		
		deleteClient : function(clientId){
			return $http({
				  method: 'DELETE',
				  url: 'rest/client/'+clientId,
			}).then(function(response){
				return response.data;
			}, function(response){
				return "";
			});
		},

		newEmployee : function(employeeEntity){
			return $http({
				  method: 'POST',
				  url: 'rest/employee',
				  data: employeeEntity,   
				  headers: {'Content-Type': 'application/json'}				  
			}).then(function(response){
				return response.data;
			}, function(response){
				return "";
			});
		},
		
		getEmployee : function(id){
			return $http({
				  method: 'GET',
				  url: 'rest/employee/'+id
			}).then(function(response){
				return response.data;
			}, function(response){
				return "";
			});
		},
		
		deleteEmployee : function(employeeId){
			return $http({
				  method: 'DELETE',
				  url: 'rest/employee/'+employeeId,
			}).then(function(response){
				return response.data;
			}, function(response){
				return "";
			});
		},
		
		newTest : function(testEntity){
			return $http({
				  method: 'POST',
				  url: 'rest/test',
				  data: testEntity,   
				  headers: {'Content-Type': 'application/json'}				  
			}).then(function(response){
				return response.data;
			}, function(response){
				return "";
			});
		},
		
		deleteTest : function(testId){
			return $http({
				  method: 'DELETE',
				  url: 'rest/test/'+testId,
			}).then(function(response){
				return response.data;
			}, function(response){
				return "";
			});
		},
	};
	
	return loc_out;
}]);

