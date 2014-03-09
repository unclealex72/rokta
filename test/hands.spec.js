'use strict';

/* jasmine specs for controllers go here */
describe("The Hands module's", function() {


  beforeEach(module('rokta.stats.hands'));

  describe('Hands service', function(){
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
      ],
      league: [{player: "Tony"}, {player: "Andy H"}]
    };
    var allPlayers;
    beforeEach(inject(function ($injector) {
      hands = $injector.get('Hands');
    }));
  
  	it('should collate first round counts', function() {
	    var firstRoundCounts = hands.firstRoundCounts(stats);
	    expect(firstRoundCounts).toEqual({categories: ['Tony', 'Andy H'], series: [{name: 'ROCK', data: [0, 0]}, {name: 'SCISSORS', data: [0,100]}, {name: 'PAPER', data: [100, 0]}]});
    });
  	it('should collate all round counts', function() {
	    var allRoundCounts = hands.allRoundCounts(stats);
	    expect(allRoundCounts).toEqual({categories: ['Tony', 'Andy H'], series: [{name: 'ROCK', data: [100, 50]}, {name: 'SCISSORS', data: [0, 25]}, {name: 'PAPER', data: [0, 25]}]});
    });
  });
});
