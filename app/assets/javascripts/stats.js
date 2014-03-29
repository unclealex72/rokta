var statsApp = angular.module(
  'stats',
  ['rokta.common.players', 'rokta.common.events', 'rokta.common.colours',
   'rokta.common.routing', 'rokta.common.interactive',
   'rokta.stats.graph', 'rokta.stats.league', 'rokta.stats.stats',
   'rokta.stats.headtoheads', 'rokta.stats.streaks', 'rokta.stats.hands',
   'ui.bootstrap', 'ngRoute', 'ngAnimate']);

statsApp.config(['$routeProvider',
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
  }).otherwise({
    redirectTo : '/league/'
  });
}]);

statsApp.controller('StatsCtrl', ['$window', '$log', '$scope', '$modal', 'Players', 'Interactive', 'ROUTES',
function($window, $log, $scope, $modal, Players, Interactive, ROUTES) {
  Interactive.onStateChange(function(state) {
    if (state.inProgress) {
      if (!$scope.modalInstance) {
        $scope.modalInstance = $modal.open({
          templateUrl: 'assets/angular/gameinprogress.html'
        });
        var gameInProgress = function(redirect) {
          if (redirect === true) {
            $window.location.href=ROUTES.interactiveGame + '#/join';
          }
        }
        $scope.modalInstance.result.then(gameInProgress, gameInProgress);
      }
    }
    else {
      if ($scope.modalInstance) {
        $scope.modalInstance.close(false);
        $scope.modalInstance = null;
      }
    }
  });
  Players.refresh();
}]);

statsApp.controller('NavCtrl', ['$scope', 'Events', 'Players', 'ROUTES', '$routeParams', '$location',
function($scope, Events, Players, ROUTES, $routeParams, $location) {
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
      "submenu": [{
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
    }, {
      "name": "New Game",
      "icon": "play-circle",
      "submenu": [{
        "name": "Interactive Game",
        "href": ROUTES.interactiveGame
      }, {
        "name": "Non-interactive Game",
        "href": ROUTES.nonInteractiveGame
      }]
    }];
  });
}]);

statsApp.controller('GameFilterCtrl', ['$scope', 'Events', 'Stats', function($scope, Events, Stats) {
  Stats.refresh();
  Events.listenTo($scope, Stats, function() {
    var stats = Stats.stats;
    $scope.contiguousGameFilter = stats.contiguousGameFilter;
  });
}]);
