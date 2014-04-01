var avatarApp = angular.module(
  'rokta.common.avatar', ['rokta.common.players', 'rokta.common.routing', 'ui.bootstrap', 'ngAnimate']);

avatarApp.directive('roktaAvatar', ['ROUTES', function(ROUTES) {
  return {
    restrict : 'AE',
    templateUrl : '/assets/angular/directives/avatar.html',
    scope : {
      avatarUrl: '='
    },
    link : function($scope, element, attrs) {
      $scope.url = $scope.avatarUrl || ROUTES.anon;
    }
  };
}]);
