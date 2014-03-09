'use strict';

var FREDDIE_RGB = '#FF0000';
var FREDDIE_COLOUR = 'RED';
var BRIAN_COLOUR = 'GREEN';
var BRIAN_RGB = '#00FF00';
var ROGER_COLOUR = 'BLUE';
var ROGER_RGB = '#0000FF';
var JOHN_COLOUR = 'BLACK';
var JOHN_RGB = '#000000';

var freddie = 'freddie';
var brian = 'brian';
var roger = 'roger';
var john = 'john';

var players = [{
  name : freddie,
  colour : FREDDIE_COLOUR
}, {
  name : brian,
  colour : BRIAN_COLOUR
}, {
  name : roger,
  colour : ROGER_COLOUR
}, {
  name : john,
  colour : JOHN_COLOUR
}];

var colours = {};
colours[FREDDIE_COLOUR] = {rgb: FREDDIE_RGB};
colours[ROGER_COLOUR] = {rgb: ROGER_RGB};
colours[BRIAN_COLOUR] = {rgb: BRIAN_RGB};
colours[JOHN_COLOUR] = {rgb: JOHN_RGB};

/* jasmine specs for controllers go here */
describe("The graph module's", function() {

  beforeEach(module('rokta.stats.graph'));

  describe('Graph object', function() {

    var graph;
    beforeEach(inject(function($injector) {
      var Graph = $injector.get('Graph');
      graph = new Graph(players, colours);
    }));

    it('should be able to track a single result', function() {
      graph.addPoint("2013-09-05T09:12:00+01:00", freddie, 50);
      expect(graph.series).toEqual([{
        name : freddie,
        data : [[Date.UTC(2013, 8, 5, 8, 12, 0), 50]]
      }]);
      expect(graph.colours).toEqual([FREDDIE_RGB]);
      expect(graph.yAxisMin).toEqual(50);
      expect(graph.yAxisMax).toEqual(50);
    });

    it('should be able to track a single snapshot', function() {
      graph.addSnapshot("2013-09-05T09:12:00+01:00", freddie, {
        gamesWon : 3,
        gamesLost : 1
      });
      expect(graph.series).toEqual([{
        name : freddie,
        data : [[Date.UTC(2013, 8, 5, 8, 12, 0), 25]]
      }]);
      expect(graph.colours).toEqual([FREDDIE_RGB]);
      expect(graph.yAxisMin).toEqual(25);
      expect(graph.yAxisMax).toEqual(25);
    });

    it('should be able to track a single game', function() {
      graph.addGame("2013-09-05T09:12:00+01:00", {
        freddie : {
          gamesWon : 3,
          gamesLost : 1
        },
        roger : {
          gamesWon : 1,
          gamesLost : 1
        }
      });
      expect(graph.series).toEqual([{
        name : freddie,
        data : [[Date.UTC(2013, 8, 5, 8, 12, 0), 25]]
      }, {
        name : roger,
        data : [[Date.UTC(2013, 8, 5, 8, 12, 0), 50]]
      }]);
      expect(graph.colours).toEqual([FREDDIE_RGB, ROGER_RGB]);
      expect(graph.yAxisMin).toEqual(25);
      expect(graph.yAxisMax).toEqual(50);
    });

    it('should be able to track multiple games', function() {
      var games = [[
        "2013-09-05T09:12:00+01:00", {
          freddie : {
            gamesWon : 3,
            gamesLost : 1
          },
          roger : {
            gamesWon : 1,
            gamesLost : 1
          }
        }],
        ["2013-09-05T10:12:00+01:00", {
          freddie : {
            gamesWon : 4,
            gamesLost : 1
          },
          roger : {
            gamesWon : 2,
            gamesLost : 1
          },
          brian : {
            gamesWon : 0,
            gamesLost : 1
          }
        }]
      ];
      graph.populate(games);
      expect(graph.series).toEqual([{
        name : freddie,
        data : [[Date.UTC(2013, 8, 5, 8, 12, 0), 25], [Date.UTC(2013, 8, 5, 9, 12, 0), 20]]
      }, {
        name : roger,
        data : [[Date.UTC(2013, 8, 5, 8, 12, 0), 50], [Date.UTC(2013, 8, 5, 9, 12, 0), 33.33]]
      }, {
        name : brian,
        data : [[Date.UTC(2013, 8, 5, 9, 12, 0), 100]]
      }]);
      expect(graph.colours).toEqual([FREDDIE_RGB, ROGER_RGB, BRIAN_RGB]);
      expect(graph.yAxisMin).toEqual(20);
      expect(graph.yAxisMax).toEqual(100);
    });
  });
});
