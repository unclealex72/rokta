var rokta = angular.module(
  'rokta', 
  ['rokta.players', 'rokta.game', 'rokta.graph', 'rokta.league', 'rokta.stats', 'rokta.colours',
   'rokta.headtoheads', 'rokta.events', 'rokta.streaks', 'rokta.player', 'ui.bootstrap', 'ngRoute', 'ngAnimate']);

rokta.config(['$routeProvider',
function($routeProvider) {
  $routeProvider.when('/league/:filter?', {
    templateUrl : 'assets/angular/league/partials/league.html',
    controller : 'LeagueCtrl'
  }).when('/graph/:filter?', {
    templateUrl : 'assets/angular/graph/partials/graph.html',
    controller : 'GraphCtrl'
  }).when('/filters/:filter?', {
    templateUrl : 'assets/angular/filters/partials/filters.html'
  }).when('/headtoheads/:filter?', {
    templateUrl : 'assets/angular/headtoheads/partials/headtoheads.html',
    controller : 'HeadToHeadsCtrl'
  }).when('/winningstreaks/:filter?', {
    templateUrl : 'assets/angular/streaks/partials/streaks.html',
    controller : 'WinningStreaksCtrl'
  }).when('/losingstreaks/:filter?', {
    templateUrl : 'assets/angular/streaks/partials/streaks.html',
    controller : 'LosingStreaksCtrl'
  }).when('/winningstreaks/page/:page/:filter?', {
    templateUrl : 'assets/angular/streaks/partials/streaks.html',
    controller : 'WinningStreaksCtrl'
  }).when('/losingstreaks/page/:page/:filter?', {
    templateUrl : 'assets/angular/streaks/partials/streaks.html',
    controller : 'LosingStreaksCtrl'
  }).when('/players/:filter?', {
    templateUrl : 'assets/angular/players/partials/players.html',
    controller : 'AllPlayersCtrl'
  }).when('/game/:game?', {
    templateUrl : 'assets/angular/game/partials/game.html',
    controller : 'GameCtrl'
  }).otherwise({
    redirectTo : '/league/'
  });
}]);

rokta.controller('RoktaCtrl', ['Players',
function(Players) {
  Players.refresh();
}]);

rokta.controller('NavCtrl', ['$scope', 'Events', 'Players', '$routeParams', '$location',
function($scope, Events, Players, $routeParams, $location) {
  $scope.navigateTo = function(location) {
    if ($routeParams.filter) {
      location += '/' + $routeParams.filter;
    }
    $location.path(location);
    return false;
  };
  Events.listenTo($scope, Players, function() {
    var playersHeader = [{"name" : "Everybody", "link" : "players"}, {"divider" : true}];
    var players = _(Players.players).sortBy('name').map('name').map(function(name) {
      return {
        "name" : name,
        "link" : "player/" + name
      };
    }).toArray().value();
    $scope.nav = [{
      "name" : "League",
      "icon" : "trophy",
      "link" : "league"
    }, {
      "name" : "Graph",
      "icon" : "bar-chart-o",
      "link" : "graph"
    }, {
      "name" : "Filters",
      "icon" : "adjust",
      "link" : "filters"
    }, {
      "name" : "Statistics",
      "icon" : "edit",
      "submenu" : [{
        "name" : "Winning Streaks",
        "link" : "winningstreaks"
      }, {
        "name" : "Losing Streaks",
        "link" : "losingstreaks"
      }, {
        "name" : "Head to Heads",
        "link" : "headtoheads"
      }]
    }, {
      "name" : "Players",
      "icon" : "edit",
      "submenu" : _.union(playersHeader, players)
    }];
  });
}]);

rokta.controller('GameFilterCtrl', ['$scope', 'Events', 'Stats', function($scope, Events, Stats) {
  Stats.refresh();
  Events.listenTo($scope, Stats, function() {
    var stats = Stats.stats;
    $scope.contiguousGameFilter = stats.contiguousGameFilter;
  });
}]);
