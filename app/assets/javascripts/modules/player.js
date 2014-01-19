var player = angular.module('rokta.player', ['rokta.events', 'rokta.players', 'rokta.stats', 'rokta.filters', 'rokta.colours']);

player.service('AllPlayers', [function() {
  var service = function(countType) {
    return function(stats) {
      var mostRecentSnapshot = _.last(_.last(stats.snapshots));
      var playerNames = _(mostRecentSnapshot).keys().sortBy();
      var totalHandsPlayed = {};
      playerNames.forEach(function (playerName) {
        totalHandsPlayed[playerName] =
          _(mostRecentSnapshot[playerName].handCount[countType]).reduce(function (sum, num) { return sum + num });
      });
      var hands = ["ROCK", "SCISSORS", "PAPER"];
      var counts = {};
      _.forEach(hands, function(hand) {
        counts[hand] = [];
      });
      playerNames.forEach(function(playerName) {
        var handCount = mostRecentSnapshot[playerName].handCount;
        var result = handCount[countType];
        _.forEach(hands, function(hand) {
          var count = _.isUndefined(result[hand]) ? 0 : result[hand];
          counts[hand].push(100 * count / totalHandsPlayed[playerName]);
        });
      });
      var series = _.map(counts, function(countsForHand, hand) {
        return { name: hand, data: countsForHand };
      });
      return { categories: playerNames.value(), series: series };
    };
  };
  return { firstRoundCounts: service('countsForFirstRounds'), allRoundCounts: service('countsForAllRounds')};
}]);

player.directive('roktaHands', [
function() {
  return {
    restrict : 'A',
    scope : {
      roktaHands : '=',
      roktaTitle : '@'
    },
    link : function($scope, elem, attrs) {
      var drawChart = function() {
        var roktaHands = $scope.roktaHands;
        elem.highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: null
            },
            xAxis: {
                categories: roktaHands.categories
            },
            yAxis: {
                min: 0,
                title: {
                    text: 'Hands played'
                },
                stackLabels: {
                    enabled: false,
                    style: {
                        fontWeight: 'bold',
                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                    }
                }
            },
            legend: {
                align: 'right',
                x: 0,
                verticalAlign: 'top',
                y: 0,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
                borderColor: '#CCC',
                borderWidth: 1,
                shadow: false
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.x +'</b><br/>'+
                        this.series.name +': '+ this.y.toFixed(0) + '%';
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true,
                        formatter: function() {
                          return this.y.toFixed(0) + '%';
                        },
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
                    }
                }
            },
            series: roktaHands.series
        });
      };
      $scope.$watch('roktaHands', function(roktaHands) {
        if (roktaHands) {
          drawChart();
        }
      });
    }
  };
}]);

player.controller('AllPlayersCtrl', ['$scope', 'Events', 'Stats', 'AllPlayers',
function($scope, Events, Stats, AllPlayers) {
  Stats.refresh();
  Events.listenTo($scope, [Stats], function() {
    $scope.firstHands = AllPlayers.firstRoundCounts(Stats.stats);
    $scope.allHands = AllPlayers.allRoundCounts(Stats.stats);
  });
}]);
