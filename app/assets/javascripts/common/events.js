var events = angular.module('rokta.common.events', ['restangular']);

events.service('Events', ['$log', '$rootScope', 'Restangular',
function($log, $rootScope, Restangular) {
  var service = {
    refresh : function(event, url, success) {
      $log.info("Calling " + url);
      $rootScope['_eventCount_'] = $rootScope['_eventCount_'] ? $rootScope['_eventCount_'] + 1 : 1;
      var onSuccess = function(data) {
        success(data);
        event.initialised = true;
        $rootScope['_eventCount_'] = $rootScope['_eventCount_'] - 1;
        $rootScope.$broadcast(event.name);
      }
      var onFailure = function(response) {
        $rootScope['_eventCount_'] = $rootScope['_eventCount_'] - 1;
        $log.info(event.name + ": " + response.status);
        alert(event.name + ": " + response.status);
      };
      Restangular.oneUrl(event.name, url).get().then(onSuccess, onFailure);
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
      });
    }
  };
  return service;
}]);
