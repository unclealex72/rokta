var interactiveApp = angular.module(
  'interactive',
  ['rokta.common.players', 'rokta.common.panel',
   'rokta.common.routing', 'rokta.common.interactive', 'rokta.common.auth',
   'restangular', 'ui.bootstrap', 'ngRoute', 'ngAnimate']);

interactiveApp.config(['$routeProvider',
function($routeProvider) {
  $routeProvider.when('/coyi', {
    templateUrl : 'assets/angular/interactive/partials/game.html',
    controller : 'GameCtrl'
  }).when('/cancel', {
    templateUrl : 'assets/angular/interactive/partials/game.html',
    controller : 'CancelCtrl'
  }).otherwise({
    redirectTo : '/coyi'
  });
}]);

interactiveApp.controller('NavCtrl', ['$scope', '$location',
function($scope, $location) {
  $scope.navigateTo = function(location) {
    $location.path(location);
    return false;
  };
  $scope.nav = [{
    "name": "Cancel",
    "icon": "times",
    "link": "cancel"
  }];
}]);

interactiveApp.controller('InteractiveCtrl',
['$scope', '$window', 'Restangular', 'Interactive', 'ROUTES', 'AUTH',
function($scope, $window, Restangular, Interactive, ROUTES, AUTH) {
  var onSuccess = function(exemptPlayer) {
    $scope.exemptPlayer = exemptPlayer;
    $scope.exempt = AUTH.name && (exemptPlayer == AUTH.name);
    Interactive.onStateChange(function(state) {
      $scope.state = state;
      if (state.type == 'waitingForPlayers') {
        $scope.startable = state.players.length >= 2;
        $scope.joined = _.contains(state.players, AUTH.name);
      }
      if (state.type == 'gameInProgress') {
        $scope.round = state.previousRounds.length + 1;
        $scope.currentPlayers = state.currentPlayers;
        $scope.alreadyPlayed = _.keys(state.currentRound);
        $scope.yetToPlay = _.difference($scope.currentPlayers, $scope.alreadyPlayed);
        $scope.progressMax = $scope.currentPlayers.length;
        $scope.progressValue = $scope.progressMax - $scope.alreadyPlayed.length;
        $scope.myHand = state.currentRound[AUTH.name];
      }
      $scope.$apply();
    });
  }
  var onFailure = function() {
    $window.alert("Cannot find who the exempt player is");
  }
  Restangular.oneUrl('exemptPlayer', ROUTES.exemptPlayer).get().then(onSuccess, onFailure);
}]);

interactiveApp.controller('GameCtrl',
['$scope', '$location', '$window', 'Interactive',
function($scope, $location, $window, Interactive) {
  $scope.instigate = Interactive.instigate;
  $scope.join = Interactive.join;
  $scope.start = Interactive.start;
  $scope.play = Interactive.play;
  $scope.accept = Interactive.accept;
}]);

interactiveApp.controller('CancelCtrl', ['$window', 'Interactive', 'ROUTES',
function ($window, Interactive, ROUTES) {
  Interactive.cancel();
  $window.location.href = ROUTES.index;
}]);

interactiveApp.directive('roktaInteractiveButton', function() {
  return {
    restrict : 'AE',
    templateUrl : '/assets/angular/interactive/directives/button.html',
    scope : {
      busy : '=',
      waiting : '=',
      text : '@',
      ngClick : '&'
    }
  };
});
