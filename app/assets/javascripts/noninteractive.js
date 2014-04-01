var nonInteractiveApp = angular.module(
  'nonInteractive',
  ['rokta.noninteractive.game', 'rokta.common.avatar', 'ui.bootstrap', 'ngRoute', 'ngAnimate']);

nonInteractiveApp.config(['$routeProvider',
function($routeProvider) {
  $routeProvider.when('/game/:game?', {
    templateUrl : 'assets/angular/game/partials/game.html',
    controller : 'GameCtrl'
  }).otherwise({
    redirectTo : '/game/'
  });
}]);

nonInteractiveApp.controller('NonInteractiveCtrl', ['$scope', function($scope) {
  //Do nothing.
}]);

nonInteractiveApp.controller('NavCtrl', ['$scope', function($scope) {
  $scope.nav = [];
}]);
