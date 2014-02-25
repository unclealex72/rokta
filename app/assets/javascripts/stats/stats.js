var stats = angular.module('rokta.stats.stats', ['rokta.common.events', 'rokta.stats.filters']);

stats.service('Stats', ['Events', 'CurrentFilter', function(Events, CurrentFilter) {
  var service = {
	initialised: false,
	name: 'stats.update',
    stats: {},
    anyGamesPlayedToday: false,
    refresh: function() {
      var filter = CurrentFilter.asString();
      var statsPath = 'stats';
      if (!(filter === undefined)) {
    	  statsPath += '/' + filter;
      }
      Events.refresh(service, statsPath, function(stats) {
        service.stats = stats;
        service.anyGamesPlayedToday = !_.isEmpty(stats.currentResults);
      });
    }
  };
  return service;
}]);
