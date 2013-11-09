var app = angular.module('rokta', ['ui.bootstrap', 'ngRoute']);

app.config(['$routeProvider', function($routeProvider) {
  $routeProvider.
    when('/league/:filter?', {templateUrl: 'assets/partials/league.html', controller: 'LeagueCtrl'}).
    when('/winningstreaks/:filter?', {templateUrl: 'assets/partials/streaks.html'}).
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

app.service('Events', ['$log', '$rootScope', '$http', function($log, $rootScope, $http) {
  var service = {
    refresh: function(event, url, success) {
      $log.info("Calling " + url)
      $http.get(url).
        success(function(data, status) {
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

app.service('Players', ['Events', function(Events) {
  var service = {
    initialised: false,
    name: 'players.update',
    players: [],
    refresh: function() {
      Events.refresh(service, 'players', function(players) {
	    service.players = players.players;
      });
    }
  }
  return service;
}]);

app.service('Stats', ['Events', '$routeParams', function(Events, $routeParams) {
  var service = {
	initialised: false,
	name: 'stats.update',
    stats: {},
    anyGamesPlayedToday: false,
    refresh: function() {
      var filter = $routeParams.filter;
      alert(filter);
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

app.controller('RoktaCtrl', ['Players', 'Stats', '$routeParams', function(Players, Stats, $routeParams) {
  Players.refresh();
}]);

app.controller('NavCtrl', ['$scope', 'Events', 'Players', function($scope, Events, Players) {
  Events.listenTo($scope, Players, function() {
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

app.controller('LeagueCtrl', ['$scope', 'Events', 'Stats', function($scope, Events, Stats) {
  Stats.refresh();
  Events.listenTo($scope, Stats, function() {
	var stats = Stats.stats;
    $scope.current = Stats.stats.current;
    $scope.league = Stats.stats.league;
    $scope.anyGamesPlayedToday = Stats.anyGamesPlayedToday;
  });
}]);

app.controller('LastGameCtrl', ['$scope', 'Events', 'Stats', function($scope, Events, Stats) {
  Events.listenTo($scope, Stats, function() {
	$scope.lastGame = Stats.stats.lastGame;
  });
}]);

app.controller('TodayCtrl', ['$scope', 'Events', 'Stats', 'ResultGrouper', function($scope, Events, Stats, ResultGrouper) {
  Events.listenTo($scope, Stats, function() {
	var stats = Stats.stats;
	$scope.numberOfGamesToday = stats.numberOfGamesToday;
	$scope.gamesWon = ResultGrouper.group(stats.currentResults, "gamesWon");
	$scope.gamesLost = ResultGrouper.group(stats.currentResults, "gamesLost");
  });
}]);

app.controller('CurrentStreaksCtrl', ['$scope', 'Events', 'Stats', 'StreaksGrouper', function($scope, Events, Stats, StreaksGrouper) {
  Events.listenTo($scope, Stats, function() {
	var streaks = Stats.stats.streaks;
	var groupCurrent = function(streaks) {
	  var currentStreaks = _.filter(streaks, function(streak){ return streak.current; });
	  return StreaksGrouper.group(currentStreaks);
	}
	$scope.winningStreakGroups = groupCurrent(streaks.winningStreaks);
	$scope.losingStreakGroups = groupCurrent(streaks.losingStreaks);
  });
}]);
