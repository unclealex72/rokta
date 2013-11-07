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

app.service('ResultGrouper', [function() {
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
}]);

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

app.controller('TodayCtrl', ['$scope', 'Stats', 'ResultGrouper', function($scope, Stats, ResultGrouper) {
  $scope.$on('stats.update', function(event) {
	var stats = Stats.stats;
	$scope.numberOfGamesToday = stats.numberOfGamesToday;
	$scope.gamesWon = ResultGrouper.group(stats.currentResults, "gamesWon");
	$scope.gamesLost = ResultGrouper.group(stats.currentResults, "gamesLost");
  });
}]);
