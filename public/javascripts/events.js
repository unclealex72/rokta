var events = angular.module('rokta.events', []);

events.service('Events', ['$log', '$rootScope', '$http',
function($log, $rootScope, $http) {
  var service = {
    refresh : function(event, url, success) {
      $log.info("Calling " + url);
      $http.get(url).success(function(data, status) {
        $log.info("Url " + url + " returned status " + status);
        success(data);
        event.initialised = true;
        $rootScope.$broadcast(event.name);
      }).error(function(data, status) {
        alert(event.name + ": " + status);
      });
    },
    listenTo : function($scope, events, callback) {
      if (!_.isArray(events)) {
        events = [events];
      }
      var runCallbacks = function() {
        if (_.every(events, 'initialised')) {
          callback();
        }
      };
      _.forEach(events, function(evt) {
        $scope.$on(evt.name, function(evt) {
          runCallbacks();
        });
        runCallbacks();
      });
    }
  };
  return service;
}]);
