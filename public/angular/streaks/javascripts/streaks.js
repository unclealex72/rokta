var streaks = angular.module('rokta.streaks', ['rokta.stats', 'rokta.events']);

streaks.service('Streaks', ['Events', 'Stats', function(Events, Stats) {
  var service = {
    initialise: function($scope, type, filter) {
      Stats.refresh();
      Events.listenTo($scope, Stats, function() {
    	var currentSize = null;
    	var currentIndex = 1;
    	var view = function(streak) {
		  var streakSize = streak.dateTimes.length;
		  var index;
		  if (streakSize == currentSize) {
			index = null;
		  }
		  else {
			index = currentIndex;
		  }
		  var streakView = {
			index: index,
			size: streakSize,
			name: streak.playerName,
			from: _.first(streak.dateTimes),
			to: _.last(streak.dateTimes)
		  };
		  currentSize = streakSize;
		  currentIndex++;
		  return streakView;
		};
    	$scope.streaks = _.map(Stats.stats.streaks[filter], view);
	    $scope.type = type;
      });
    } 
  };
  return service;
}]);

streaks.controller('WinningStreaksCtrl', ['$scope', 'Streaks', function($scope, Streaks) {
  Streaks.initialise($scope, 'winning', 'winningStreaks');
}]);

streaks.controller('LosingStreaksCtrl', ['$scope', 'Streaks', function($scope, Streaks) {
  Streaks.initialise($scope, 'losing', 'losingStreaks');
}]);
