var app = angular.module('rokta', ['ui.bootstrap', 'ngRoute']);

app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.
    when('/league/:filter?', {templateUrl: 'assets/partials/league.html', controller: 'StatsCtrl'}).
    when('/winningstreaks/:filter?', {templateUrl: 'assets/partials/winningstreaks.html', controller: 'StatsCtrl'}).
    otherwise({redirectTo: '/league/'});
}]);

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

app.service('Players', ['$rootScope', '$http', function($rootScope, $http) {
	  var service = {
	    players: [],
	    refresh: function() {
	      $http.get('players').success(function(players, status) {
	    	service.players = players.players;
	    	$rootScope.$broadcast('players.update');
	      });
	    }
	  }
	  return service;
	}]);

app.service('Stats', ['$rootScope', '$http', function($rootScope, $http) {
  var service = {
    stats: {},
    anyGamesPlayedToday: false,
    refresh: function(filter) {
      var statsPath = 'stats';
      if (!(filter === undefined)) {
    	  statsPath += '/' + filter
      }
      $http.get(statsPath).
        success(function(stats, status) {
	      service.stats = stats;
	      service.anyGamesPlayedToday = !_.isEmpty(stats.currentResults)
	      $rootScope.$broadcast('stats.update');
        }).
        error(function(data, status) {
    	  alert(status);
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

app.controller('RoktaCtrl', ['Players', function(Players) {
  Players.refresh();
}]);

app.controller('StatsCtrl', ['Stats', '$routeParams', function(Stats, $routeParams) {
  Stats.refresh($routeParams.filter);
}]);

app.controller('NavCtrl', ['$scope', 'Players', function($scope, Players) {
  $scope.$on('players.update', function(event) {
    var players = 
      _(Players.players).sortBy('name').map('name').
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
