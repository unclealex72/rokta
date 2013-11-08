var app = angular.module('rokta', []);

/*
app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.
    when('/season', {templateUrl: 'assets/partials/season.html',   controller: MainCtrl}).
    when('/season/:season/tickets/:ticketType', {templateUrl: 'assets/partials/season.html', controller: MainCtrl}).
    otherwise({redirectTo: '/season'});
}]);
*/

app.directive('roktaJoin', function () {
  return {
    restrict: 'A',
    template: '{{text}}',
    scope: {
      roktaJoinItems: '=',
      roktaJoinOrdered: '='
    },
    link: function ($scope, elem, attrs) {
      var text;
      var items = $scope.roktaJoinItems;
      if ($scope.roktaJoinOrdered) {
    	items = _.sortBy(items);
      }
      if (items.length == 1) {
    	text = items[0]
      }
      else {
    	text = _(items).take(items.length - 1).reduce(function(str, item) {
    		  return str + ", " + item;
    	});
    	text += " and " + items[items.length - 1];
      }
      $scope.text = text;
    }
  }
});

app.directive('roktaLeague', function() {
  return {
    restrict: 'E',
    templateUrl: '/assets/directives/league.html',
    scope: {
      league: '=',
      current: '='
    }};
});

app.directive('roktaToday', function() {
  return {
    restrict: 'E',
    templateUrl: '/assets/directives/today.html',
    scope: {
      results: '=',
      verb: '@'
    }};
});

app.directive('roktaCurrentStreaks', function() {
  return {
    restrict: 'E',
    templateUrl: '/assets/directives/current-streaks.html',
    scope: {
      streakGroups: '=',
      type: '@'
    }};
});

app.service('Stats', ['$rootScope', '$http', function($rootScope, $http) {
  var service = {
    stats: {},
    anyGamesPlayedToday: false,
    refresh: function(filter) {
      $http.get('stats').success(function(stats, status) {
    	service.stats = stats;
    	service.anyGamesPlayedToday = !_.isEmpty(stats.currentResults)
    	$rootScope.$broadcast('stats.update');
      });
    }
  }
  return service;
}]);

app.service('ResultGrouper', function() {
  var service = {
    group: function(currentResultsByPlayer, countExtractor) {
      var valueExtractor = function(value, key) {
        return {"name": key, "value": value[countExtractor]};
      };
      var resultsByCount = _(currentResultsByPlayer).map(valueExtractor).groupBy("value");
      var namesByCount = [];
      resultsByCount.forIn(function(value, key) { 
        namesByCount.push({"count": key, "names": _.map(value, "name")});
      });
      return _.sortBy(namesByCount, function(v) { return -v.count });
    }
  }
  return service;
});

app.service('StreaksGrouper', function() {
  var service = {
    group: function(streaks) {
      var sizedStreaks = _.map(streaks, function(streak) {
    	return { "name": streak.playerName, "size": streak.dateTimes.length };
      });
      var sizedStreaksBySize = _(sizedStreaks).groupBy("size");
      var namesBySize = [];
      sizedStreaksBySize.forIn(function(value, key) {
    	namesBySize.push({"size": key, "names": _.map(value, "name")});
      });
      return _.sortBy(namesBySize, function(v) { return -v.size});
    }
  }
  return service;
});

app.controller('StatsCtrl', ['Stats', function(Stats) {
	  Stats.refresh();
}]);

app.controller('NavCtrl', ['$scope', 'Stats', function($scope, Stats) {
  $scope.$on('stats.update', function(event) {
    var players = 
      _(Stats.stats.players).sortBy('name').map('name').
      map(function(name) {return {"name": name, "link": "#" + name }}).toArray().value();
    $scope.nav = [
      { "name": "League", "icon": "trophy", "link": "#" },
      { "name": "Statistics", "icon": "edit", "submenu": 
        [{"name": "Winning Streaks", "link": "#winningstreaks"},
         {"name": "Losing Streaks", "link": "#losingstreaks"}]},
      { "name": "Players", "icon": "edit", "submenu": players }
    ];
  });
}]);

app.controller('LeagueCtrl', ['$scope', 'Stats', function($scope, Stats) {
  $scope.$on('stats.update', function(event) {
	var stats = Stats.stats;
    $scope.current = Stats.stats.current;
    $scope.league = Stats.stats.league;
    $scope.anyGamesPlayedToday = Stats.anyGamesPlayedToday;
  });
}]);

app.controller('LastGameCtrl', ['$scope', 'Stats', function($scope, Stats) {
  $scope.$on('stats.update', function(event) {
	$scope.lastGame = Stats.stats.lastGame;
  });
}]);

app.controller('TodayCtrl', ['$scope', 'Stats', 'ResultGrouper', function($scope, Stats, ResultGrouper) {
  $scope.$on('stats.update', function(event) {
	var stats = Stats.stats;
	$scope.numberOfGamesToday = stats.numberOfGamesToday;
	$scope.gamesWon = ResultGrouper.group(stats.currentResults, "gamesWon");
	$scope.gamesLost = ResultGrouper.group(stats.currentResults, "gamesLost");
  });
}]);

app.controller('StreaksCtrl', ['$scope', 'Stats', 'StreaksGrouper', function($scope, Stats, StreaksGrouper) {
  $scope.$on('stats.update', function(event) {
	var streaks = Stats.stats.streaks;
	var groupCurrent = function(streaks) {
	  var currentStreaks = _.filter(streaks, function(streak){ return streak.current; });
	  return StreaksGrouper.group(currentStreaks);
	}
	$scope.winningStreakGroups = groupCurrent(streaks.winningStreaks);
	$scope.losingStreakGroups = groupCurrent(streaks.losingStreaks);
  });
}]);
