var streaks = angular.module('rokta.stats.streaks',
  ['rokta.stats.stats', 'rokta.common.events', 'rokta.common.panel']);

streaks.service('Streaks', ['Events', 'Stats',
function(Events, Stats) {
  var service = {
    initialise : function($scope, type, filter) {
      Stats.refresh();
      Events.listenTo($scope, Stats, function() {
        var currentSize = null;
        var currentIndex = 1;
        var view = function(streak) {
          var streakSize = streak.dateTimes.length;
          var index;
          if (streakSize == currentSize) {
            index = null;
          } else {
            index = currentIndex;
          }
          var streakView = {
            index : index,
            size : streakSize,
            player : streak.player,
            from : _.first(streak.dateTimes),
            to : _.last(streak.dateTimes)
          };
          currentSize = streakSize;
          currentIndex++;
          return streakView;
        };
        var streaks = _(Stats.stats.streaks[filter]).map(view).filter(function(streak, idx) {
          return idx < 10;          
        }).value();
        $scope.streaksCount = streaks.length;
        $scope.type = type;
        $scope.streaks = streaks;
        $scope.ready = true;
      });
    }
  };
  return service;
}]);

streaks.controller('WinningStreaksCtrl', ['$scope', 'Streaks',
function($scope, Streaks) {
  Streaks.initialise($scope, 'winning', 'winningStreaks');
}]);

streaks.controller('LosingStreaksCtrl', ['$scope', 'Streaks',
function($scope, Streaks) {
  Streaks.initialise($scope, 'losing', 'losingStreaks');
}]);
