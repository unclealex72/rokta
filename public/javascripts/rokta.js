var rokta = angular.module(
  'rokta', 
  ['rokta.players', 'rokta.game', 'rokta.graph', 'rokta.league', 'rokta.events', 'rokta.streaks', 'ui.bootstrap', 'ngRoute']);

rokta.config(['$routeProvider',
function($routeProvider) {
  $routeProvider.when('/league/:filter?', {
    templateUrl : 'assets/angular/league/partials/league.html',
    controller : 'LeagueCtrl'
  }).when('/graph/:filter?', {
    templateUrl : 'assets/angular/graph/partials/graph.html',
    controller : 'GraphCtrl'
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
      "name" : "Statistics",
      "icon" : "edit",
      "submenu" : [{
        "name" : "Winning Streaks",
        "link" : "winningstreaks"
      }, {
        "name" : "Losing Streaks",
        "link" : "losingstreaks"
      }]
    }, {
      "name" : "Players",
      "icon" : "edit",
      "submenu" : players
    }];
  });
}]);
