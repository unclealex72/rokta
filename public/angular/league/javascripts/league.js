var league = angular.module('rokta.league', ['rokta.events', 'rokta.stats', 'rokta.filters']);

league.directive('roktaJoin', function() {
  return {
    restrict : 'A',
    template : '{{text}}',
    scope : {
      roktaJoinItems : '=',
      roktaJoinOrdered : '='
    },
    link : function($scope, elem, attrs) {
      var text;
      var items = $scope.roktaJoinItems;
      if ($scope.roktaJoinOrdered) {
        items = _.sortBy(items);
      }
      if (items.length == 1) {
        text = items[0];
      } else {
        text = _(items).take(items.length - 1).reduce(function(str, item) {
          return str + ", " + item;
        });
        text += " and " + items[items.length - 1];
      }
      $scope.text = text;
    }
  };
});

league.directive('roktaLeague', function() {
  return {
    restrict : 'AE',
    templateUrl : '/assets/angular/league/directives/league.html',
    scope : {
      league : '=',
      current : '='
    }
  };
});

league.directive('roktaToday', function() {
  return {
    restrict : 'AE',
    templateUrl : '/assets/angular/league/directives/today.html',
    scope : {
      results : '=',
      verb : '@'
    }
  };
});

league.directive('roktaCurrentStreaks', function() {
  return {
    restrict : 'AE',
    templateUrl : '/assets/angular/league/directives/current-streaks.html',
    scope : {
      streakGroups : '=',
      type : '@'
    }
  };
});

league.service('ResultGrouper', function() {
  var service = {
    group : function(currentResultsByPlayer, countExtractor) {
      var valueExtractor = function(value, key) {
        return {
          "name" : key,
          "value" : value[countExtractor]
        };
      };
      var resultsByCount = _(currentResultsByPlayer).map(valueExtractor).groupBy("value");
      var namesByCount = [];
      resultsByCount.forIn(function(value, key) {
        namesByCount.push({
          "count" : key,
          "names" : _.map(value, "name")
        });
      });
      return _.sortBy(namesByCount, function(v) {
        return -v.count;
      });
    }
  };
  return service;
});

league.service('StreaksGrouper', function() {
  var service = {
    group : function(streaks) {
      var sizedStreaks = _.map(streaks, function(streak) {
        return {
          "name" : streak.playerName,
          "size" : streak.dateTimes.length
        };
      });
      var sizedStreaksBySize = _(sizedStreaks).groupBy("size");
      var namesBySize = [];
      sizedStreaksBySize.forIn(function(value, key) {
        namesBySize.push({
          "size" : key,
          "names" : _.map(value, "name")
        });
      });
      return _.sortBy(namesBySize, function(v) {
        return -v.size;
      });
    }
  };
  return service;
});

league.controller('LeagueCtrl', ['$scope', 'Events', 'Stats',
function($scope, Events, Stats) {
  Stats.refresh();
  Events.listenTo($scope, Stats, function() {
    var stats = Stats.stats;
    $scope.contiguousGameFilter = stats.contiguousGameFilter;
    $scope.current = stats.current;
    $scope.league = stats.league;
    $scope.anyGamesPlayedToday = Stats.anyGamesPlayedToday;
  });
}]);

league.controller('LastGameCtrl', ['$scope', 'Events', 'Stats',
function($scope, Events, Stats) {
  Events.listenTo($scope, Stats, function() {
    $scope.lastGame = Stats.stats.lastGame;
  });
}]);

league.controller('TodayCtrl', ['$scope', 'Events', 'Stats', 'ResultGrouper',
function($scope, Events, Stats, ResultGrouper) {
  Events.listenTo($scope, Stats, function() {
    var stats = Stats.stats;
    $scope.numberOfGamesToday = stats.numberOfGamesToday;
    $scope.gamesWon = ResultGrouper.group(stats.currentResults, "gamesWon");
    $scope.gamesLost = ResultGrouper.group(stats.currentResults, "gamesLost");
  });
}]);

league.controller('CurrentStreaksCtrl', ['$scope', 'Events', 'Stats', 'StreaksGrouper',
function($scope, Events, Stats, StreaksGrouper) {
  Events.listenTo($scope, Stats, function() {
    var streaks = Stats.stats.streaks;
    var groupCurrent = function(streaks) {
      var currentStreaks = _.filter(streaks, function(streak) {
        return streak.current;
      });
      return StreaksGrouper.group(currentStreaks);
    };
    $scope.winningStreakGroups = groupCurrent(streaks.winningStreaks);
    $scope.losingStreakGroups = groupCurrent(streaks.losingStreaks);
  });
}]);
