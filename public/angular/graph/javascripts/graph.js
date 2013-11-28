var graph = angular.module('rokta.graph', ['rokta.events', 'rokta.players', 'rokta.stats', 'rokta.filters']);

// A factory to create a Graph object used to create the graph series..
graph.factory('Graph', [
function() {

  function Graph(players) {
    this.players = _.indexBy(players, 'name');
    this.series = [];
    this.colours = [];
  };

  Graph.prototype = {
    addPoint : function(gameDate, player, percentage) {
      var playersSeries = _.find(this.series, function(series) {
        return series.name == player;
      });
      if (!playersSeries) {
        playersSeries = {
          name : player,
          data : []
        };
        this.series.push(playersSeries);
        this.colours.push(this.players[player].colour.rgb);
      }
      var dt = new Date(gameDate);
      var x = Date.UTC(dt.getUTCFullYear(), dt.getUTCMonth(), dt.getUTCDate(), dt.getUTCHours(), dt.getUTCMinutes(), dt.getUTCSeconds());
      var y = parseFloat(percentage.toFixed(2));
      if (this.yAxisMin === undefined || y < this.yAxisMin) {
        this.yAxisMin = y;
      }
      if (this.yAxisMax === undefined || y > this.yAxisMax) {
        this.yAxisMax = y;
      }
      playersSeries.data.push([x, y]);
    },
    populate : function(games) {
      _.forEach(games, function(snapshots, gameDate) {
        this.addGame(gameDate, snapshots);
      }, this);
    },
    addGame : function(gameDate, snapshots) {
      _.forEach(snapshots, function(snapshot, player) {
        this.addSnapshot(gameDate, player, snapshot);
      }, this);
    },
    addSnapshot : function(gameDate, player, snapshot) {
      var percentage = (snapshot.gamesLost) / (snapshot.gamesWon + snapshot.gamesLost);
      this.addPoint(gameDate, player, percentage * 100);
    }
  };
  return Graph;
}]);

graph.directive('roktaGraph', ['$log',
function($log) {
  return {
    restrict : 'A',
    scope : {
      roktaGraph : '=',
      roktaTitle : '@'
    },
    link : function($scope, elem, attrs) {
      var drawGraph = function() {
        elem.highcharts({
          chart : {
            zoomType : 'xy',
            type : 'spline'
          },
          colors : $scope.roktaGraph.colours,
          title : {
            text : $scope.roktaTitle
          },
          xAxis : {
            type : 'datetime',
            dateTimeLabelFormats : {// don't display the dummy year
              month : '%e. %b',
              year : '%b'
            }
          },
          yAxis : {
            title : {
              text : 'Losses (%)'
            },
            min : $scope.roktaGraph.min,
            max : $scope.roktaGraph.max
          },
          tooltip : {
            formatter : function() {
              return Highcharts.dateFormat('%a, %e %B %Y, %H:%M', this.x) + '<br/>' + '<b>' + this.series.name + '</b> ' + this.y.toPrecision(4) + '%';
            }
          },
          plotOptions : {
            series : {
              marker : {
                enabled : false,
                states : {
                  hover : {
                    enabled : true
                  }
                }
              }
            }
          },
          series : $scope.roktaGraph.series
        });
      };
      $scope.$watch('roktaGraph', function(roktaGraph) {
        if ( typeof roktaGraph !== "undefined") {
          drawGraph();
        }
      });
    }
  };
}]);

graph.controller('GraphCtrl', ['$log', '$scope', 'Events', 'Stats', 'Players', 'Graph',
function($log, $scope, Events, Stats, Players, Graph) {
  Stats.refresh();
  Events.listenTo($scope, [Stats, Players], function() {
    var stats = Stats.stats;
    var snapshots = stats.snapshots;
    var graph = new Graph(Players.players);
    graph.populate(snapshots);
    $scope.graph = {
      colours : graph.colours,
      series : graph.series,
      max : graph.yAxisMax,
      min : graph.yAxisMin
    };
  });
}]);
