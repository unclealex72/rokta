var events = angular.module('rokta.events', []);

events.service('Events', ['$log', '$rootScope', '$http', function($log, $rootScope, $http) {
  var service = {
    refresh: function(event, url, success) {
      $log.info("Calling " + url)
      $http.get(url).
        success(function(data, status) {
          $log.info("Url " + url + " returned status " + status);
          success(data);
          event.initialised = true;
          $rootScope.$broadcast(event.name);
        }).
        error(function(data, status) {
          alert(event.name + ": " + status);
        })},
    listenTo: function($scope, event, callback) {
      $scope.$on('stats.update', function(evt) {
    	callback();  
      });
      if (event.initialised) {
    	callback();
      }
    }  
  }
  return service;
}]);
