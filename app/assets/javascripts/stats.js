var app = angular.module(
  'stats',
  ['rokta.common.players', 'rokta.common.events', 'rokta.common.colours',
   'rokta.stats.graph', 'rokta.stats.league', 'rokta.stats.stats',
   'rokta.stats.headtoheads', 'rokta.stats.streaks', 'rokta.stats.hands',
   'ui.bootstrap', 'ngRoute', 'ngAnimate']);

app.config(['$routeProvider',
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
  }).when('/hands/:filter?', {
    templateUrl : 'assets/angular/hands/partials/hands.html',
    controller : 'HandsCtrl'
  }).when('/game/:game?', {
    templateUrl : 'assets/angular/game/partials/game.html',
    controller : 'GameCtrl'
  }).otherwise({
    redirectTo : '/league/'
  });
}]);

app.controller('StatsCtrl', ['Players',
function(Players) {
  Players.refresh();
}]);

app.controller('NavCtrl', ['$scope', 'Events', 'Players', '$routeParams', '$location',
function($scope, Events, Players, $routeParams, $location) {
  $scope.navigateTo = function(location) {
    if ($routeParams.filter) {
      location += '/' + $routeParams.filter;
    }
    $location.path(location);
    return false;
  };
  Events.listenTo($scope, Players, function() {
    $scope.nav = [{
      "name": "League",
      "icon": "trophy",
      "link": "league"
    }, {
      "name": "Graph",
      "icon": "bar-chart-o",
      "link": "graph"
    }, {
      "name": "Filters",
      "icon": "adjust",
      "link": "filters"
    }, {
      "name": "Statistics",
      "icon": "edit",
      "submenu" : [{
        "name": "Winning Streaks",
        "link": "winningstreaks"
      }, {
        "name": "Losing Streaks",
        "link": "losingstreaks"
      }, {
        "name": "Head to Heads",
        "link": "headtoheads"
      }, {
        "name": "Hand Counts",
        "link": "hands"
      }]
    }];
  });
}]);

app.controller('GameFilterCtrl', ['$scope', 'Events', 'Stats', function($scope, Events, Stats) {
  Stats.refresh();
  Events.listenTo($scope, Stats, function() {
    var stats = Stats.stats;
    $scope.contiguousGameFilter = stats.contiguousGameFilter;
  });
}]);
