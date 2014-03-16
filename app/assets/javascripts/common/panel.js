var panel = angular.module('rokta.common.panel', []);

/**
 * A simple directive to create a bootstap panel.
 */
panel.directive('roktaPanel', [
function() {
  return {
    restrict : 'A',
    transclude : true,
    replace: true,
    scope : {
      header: '@',
      footer: '@',
      type: '@'
    },
    template : 
      '<div class="panel rokta-panel" ng-class="panelClass">' +
        '<div class="panel-heading" ng-if="header">{{header}}</div>' +
        '<div class="panel-body" ng-transclude></div>' +
        '<div class="panel-footer" ng-if="footer">{{footer}}</div>' +
      '</div>',
    link : function($scope, element, attrs) {
      $scope.panelClass = "panel-" + ($scope.type ? $scope.type : "default");
    }
  };
}]);
