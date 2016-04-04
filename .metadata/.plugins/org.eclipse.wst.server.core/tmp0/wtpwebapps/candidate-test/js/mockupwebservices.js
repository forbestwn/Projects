
var serviceModule = angular.module('serviceModule', []);
serviceModule.factory('webServices', ['$q', '$http', function($q, $http) {
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
			var defer = loc_q.defer();
			var promise = defer.promise;
			
			var clientsArray = [
			     {id:100, name:'name100', address:'address100', phone:'100'},
			     {id:105, name:'name105', address:'address105', phone:'105'},
			     {id:107, name:'name107', address:'address107', phone:'107'},
			     {id:108, name:'name108', address:'address108', phone:'108'},
			     {id:106, name:'name106', address:'address106', phone:'106'},
			     {id:102, name:'name102', address:'address102', phone:'102'},
			     {id:103, name:'name103', address:'address103', phone:'103'},
			     {id:109, name:'name109', address:'address109', phone:'109'},
			     {id:101, name:'name101', address:'address101', phone:'101'},
			     {id:104, name:'name104', address:'address104', phone:'104'},
			];
			var clients = {};
			for(var i=0; i<clientsArray.length; i++){
				var client = clientsArray[i];
				clients[client.id+""] = client;
			}
			defer.resolve(clients);
			return promise;
		},
	};
	
	return loc_out;
}]);

