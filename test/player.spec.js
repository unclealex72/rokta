'use strict';

/* jasmine specs for controllers go here */
describe("The day module's", function() {


  beforeEach(module('rokta.player'));

  describe('AllPlayers service', function(){
    var stats = {
      snapshots: [
        [0, {
          "Andy H": {
            roundsDuringLosingGames: 0,
            roundsDuringWinningGames: 4,
            handCount: {
              countsForAllRounds: {
                SCISSORS: 1,
                ROCK: 2,
                PAPER: 1,
              },
              countsForFirstRounds: {
                SCISSORS: 1
              },
            },
            gamesLost: 0,
            gamesWon: 1,
          },
          "Tony": {
            roundsDuringLosingGames: 0,
            roundsDuringWinningGames: 1,
            handCount: {
              countsForAllRounds: {
                ROCK: 1
              },
              countsForFirstRounds: {
                PAPER: 1
              },
            },
            gamesLost: 0,
            gamesWon: 1
         }}
        ]
      ]
    };
    var allPlayers;
    beforeEach(inject(function ($injector) {
      allPlayers = $injector.get('AllPlayers');
    }));
  
  	it('should collate first round counts', function() {
	    var firstRoundCounts = allPlayers.firstRoundCounts(stats);
	    expect(firstRoundCounts).toEqual({ categories : [ 'Andy H', 'Tony' ], series : [ { name : 'ROCK', data : [ 0, 0 ] }, { name : 'SCISSORS', data : [ 100, 0 ] }, { name : 'PAPER', data : [ 0, 100 ] } ] });
    });
  	it('should collate all round counts', function() {
	    var allRoundCounts = allPlayers.allRoundCounts(stats);
	    expect(allRoundCounts).toEqual({ categories : [ 'Andy H', 'Tony' ], series : [ { name : 'ROCK', data : [ 50, 100 ] }, { name : 'SCISSORS', data : [ 25, 0 ] }, { name : 'PAPER', data : [ 25, 0 ] } ] });
    });
  });
});
