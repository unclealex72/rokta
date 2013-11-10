var stats = angular.module('rokta.stats', ['rokta.events']);

stats.service('Stats', ['Events', '$routeParams', function(Events, $routeParams) {
  var service = {
	initialised: false,
	name: 'stats.update',
    stats: {},
    anyGamesPlayedToday: false,
    refresh: function() {
      var filter = $routeParams.filter;
      var statsPath = 'stats';
      if (!(filter === undefined)) {
    	  statsPath += '/' + filter
      }
      Events.refresh(service, statsPath, function(stats) {
        service.stats = stats;
        service.anyGamesPlayedToday = !_.isEmpty(stats.currentResults)
      });
    }
  }
  return service;
}]);
