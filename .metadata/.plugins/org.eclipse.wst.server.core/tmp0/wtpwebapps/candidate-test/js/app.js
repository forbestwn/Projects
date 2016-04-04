/**
 * 
 */
var driverCheckApp = angular.module('driverCheckModule', [
  'ngRoute',
  'angular-toArrayFilter',
]);


driverCheckApp.config(['$routeProvider',
      function($routeProvider) {
          $routeProvider.
               when('/', {
                     templateUrl: 'js/login/login.html',
                     controller: 'loginController'
               }).
               when('/clients', {
                   templateUrl: 'js/clients/clients.html',
                   controller: 'clientsController'
               }).
               when('/newclient', {
                   templateUrl: 'js/newclient/newclient.html',
                   controller: 'newClientController'
               }).
               when('/client/:client', {
                   templateUrl: 'js/clientinfo/clientinfo.html',
                   controller: 'clientInfoController'
               }).
               when('/newemployee', {
                   templateUrl: 'js/newemployee/newemployee.html',
                   controller: 'newEmployeeController'
               }).
               when('/employee/:employee', {
                   templateUrl: 'js/employeeinfo/employeeinfo.html',
                   controller: 'employeeInfoController'
               }).
               when('/newtest', {
                   templateUrl: 'js/newtest/newtest.html',
                   controller: 'newTestController'
               }).
               otherwise({
                     redirectTo: '/phones'
               });
}]);

