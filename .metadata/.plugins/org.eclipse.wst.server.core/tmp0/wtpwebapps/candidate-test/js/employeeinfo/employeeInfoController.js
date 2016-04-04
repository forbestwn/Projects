driverCheckApp.controller('employeeInfoController', ['$scope', '$http', '$routeParams', 'navigationService', 'webServices',
  function ($scope, $http, $routeParams, navigationService, webServices) {
	var loc_id = $routeParams.employee;
	
	webServices.getEmployee(loc_id).then(
			function(employee){
				$scope.employee = employee;
			}, 
			function(reason){
			}
	);
	
	$scope.backward = function(){
		navigationService.backward();
	};
	
	$scope.newTest = function(){
		var data = {
			employee : loc_id,
			client : $scope.employee.client,
		};
		navigationService.forward('newtest', data);
	};
	
	$scope.deleteTest = function(id){
		webServices.deleteTest(id).then(
				function(test){
					delete $scope.employee.tests[id];
				}, 
				function(reason){
				}
		);
	};	
	
	$scope.passRate = undefined;
	$scope.calculatePassRate = function(){
		var pass = 0;
		var fail = 0;
		var tests = $scope.employee.tests;
		
		for (var property in tests) {
		    if (tests.hasOwnProperty(property)) {
				var test = tests[property];
				if(test.result==="pass"){
					pass = pass + 1;
				}
				else{
					fail = fail + 1;
				}
		    }
		}
		if(pass+fail==0)  $scope.passRate = undefined;  
		else		$scope.passRate = pass * 100 / (pass+fail);
	};
	
	$scope.ifSearch = false;
	$scope.fromDate = new Date();
	$scope.toDate = new Date();
	$scope.search = function(){
		$scope.fromeDate1 = new Date($scope.fromDate).getTime();
		$scope.fromeDate1 = $scope.fromeDate1 - $scope.fromeDate1 % 86400000;
		$scope.toDate1 = new Date($scope.toDate).getTime() + 86400000; 
		$scope.toDate1 = $scope.toDate1 - $scope.toDate1 % 86400000;
		$scope.ifSearch = true;
	};
	
	$scope.filter = function(value, index, array){
		if($scope.ifSearch==false)   return true;
		else{
			if(value.date.$numberLong>=$scope.fromeDate1 && value.date.$numberLong<$scope.toDate1){
				return true;
			} 
			else{
				return false;
			}
		}
	};
	
  }]);

