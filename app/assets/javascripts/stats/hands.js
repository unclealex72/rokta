var hands = angular.module(
  'rokta.stats.hands',
  ['rokta.common.events', 'rokta.common.players', 'rokta.common.colours',
  'rokta.stats.stats', 'rokta.stats.filters']);

hands.constant('COLOURS', ['#397ed7', '#f0854e', '#d94f79']);

hands.service('Hands', [function() {
  var service = function(countType) {
    return function(stats) {
      var mostRecentSnapshot = _.last(_.last(stats.snapshots));
      var playerNames = _(stats.league).map('player');
      var totalHandsPlayed = {};
      playerNames.forEach(function (playerName) {
        totalHandsPlayed[playerName] =
          _(mostRecentSnapshot[playerName].handCount[countType]).reduce(function (sum, num) {
            return sum + num;
          });
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

hands.directive('roktaHands', ['COLOURS',
function(COLOURS) {
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
            colors: COLOURS,
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

hands.controller('HandsCtrl', ['$scope', 'Events', 'Stats', 'Hands',
function($scope, Events, Stats, Hands) {
  Stats.refresh();
  Events.listenTo($scope, [Stats], function() {
    $scope.firstHands = Hands.firstRoundCounts(Stats.stats);
    $scope.allHands = Hands.allRoundCounts(Stats.stats);
    $scope.ready = true;
  });
}]);
