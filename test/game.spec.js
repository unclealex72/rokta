'use strict';

/* jasmine specs for controllers go here */
describe("The game module's", function() {

  var ROCK, SCISSORS, PAPER;
  var FREDDIE = "FREDDIE";
  var BRIAN = "BRIAN";
  var ROGER = "ROGER";
  var JOHN = "JOHN";
  
  beforeEach(module('rokta.game'));
  beforeEach(inject(function ($injector) {
	ROCK = $injector.get('ROCK');
	SCISSORS = $injector.get('SCISSORS');
	PAPER = $injector.get('PAPER');
  }));
  
  describe('Loser service', function() {
	var loserService;
	beforeEach(inject(function ($injector) {
	  loserService = $injector.get('LoserService');
	}));
    it('should return all the players for when only one hand type is played', function() {
      expect(loserService.losers({ROGER: ROCK, FREDDIE: ROCK, BRIAN: ROCK})).toEqual([ROGER, FREDDIE, BRIAN]);
    });

    it('should return all the players for when three hand types are played', function() {
      expect(loserService.losers({ROGER: ROCK, FREDDIE: SCISSORS, BRIAN: PAPER})).toEqual([ROGER, FREDDIE, BRIAN]);
    });

    it('should return those who played rock when rock and paper are played', function() {
      expect(loserService.losers({ROGER: ROCK, FREDDIE: PAPER, BRIAN: PAPER})).toEqual([ROGER]);
    });

    it('should return those who played scissors when rock and scissors are played', function() {
      expect(
    	_.sortBy(loserService.losers({ROGER: ROCK, FREDDIE: SCISSORS, BRIAN: SCISSORS}))).toEqual(
    	[BRIAN, FREDDIE]);
    });

    it('should return those who played paper when paper and scissors are played', function() {
      expect(
    	_.sortBy(loserService.losers({ROGER: PAPER, JOHN: SCISSORS, FREDDIE: SCISSORS, BRIAN: PAPER}))).toEqual(
    	[BRIAN, ROGER]);
    });
  });

  describe('Game factory', function() {
    var Game;
    beforeEach(inject(function ($injector) {
      Game = $injector.get('Game');
    }));
    
    it("should be instantiated with an instigator name, a list of players' names and an empty array of rounds", function() {
      var game = new Game(JOHN, [FREDDIE, ROGER, BRIAN]);
      expect(game.instigator).toEqual(JOHN);
      expect(game.participants).toEqual([FREDDIE, ROGER, BRIAN]);
      expect(game.rounds).toEqual([]);
      expect(game.currentPlayers()).toEqual([FREDDIE, ROGER, BRIAN]);
      expect(game.isFinished()).toEqual(false);
    });
    
    it('should keep the same players when a round is drawn.', function() {
      var game = new Game(JOHN, [FREDDIE, ROGER, BRIAN]);
      game.addRound({FREDDIE: ROCK, ROGER: SCISSORS, BRIAN: PAPER});
      expect(game.instigator).toEqual(JOHN);
      expect(game.participants).toEqual([FREDDIE, ROGER, BRIAN]);
      expect(game.rounds).toEqual([{FREDDIE: ROCK, ROGER: SCISSORS, BRIAN: PAPER}]);
      expect(game.currentPlayers()).toEqual([FREDDIE, ROGER, BRIAN]);
      expect(game.isFinished()).toEqual(false);
    });
    
    it('should discard a winner when a round is won.', function() {
      var game = new Game(JOHN, [FREDDIE, ROGER, BRIAN]);
      game.addRound({FREDDIE: ROCK, ROGER: SCISSORS, BRIAN: PAPER});
      game.addRound({FREDDIE: ROCK, ROGER: SCISSORS, BRIAN: SCISSORS});
      expect(game.instigator).toEqual(JOHN);
      expect(game.participants).toEqual([FREDDIE, ROGER, BRIAN]);
      expect(game.rounds).toEqual([
	    {FREDDIE: ROCK, ROGER: SCISSORS, BRIAN: PAPER},
	    {FREDDIE: ROCK, ROGER: SCISSORS, BRIAN: SCISSORS}]);
      expect(game.currentPlayers()).toEqual([ROGER, BRIAN]);
      expect(game.isFinished()).toEqual(false);
    });
    
    it('should declare a winner when the game is won.', function() {
      var game = new Game(JOHN, [FREDDIE, ROGER, BRIAN]);
      game.addRound({FREDDIE: ROCK, ROGER: SCISSORS, BRIAN: PAPER});
      game.addRound({FREDDIE: ROCK, ROGER: SCISSORS, BRIAN: SCISSORS});
      game.addRound({ROGER: PAPER, BRIAN: SCISSORS});
      expect(game.instigator).toEqual(JOHN);
      expect(game.participants).toEqual([FREDDIE, ROGER, BRIAN]);
      expect(game.rounds).toEqual([
  	    {FREDDIE: ROCK, ROGER: SCISSORS, BRIAN: PAPER},
  	    {FREDDIE: ROCK, ROGER: SCISSORS, BRIAN: SCISSORS},
  	    {ROGER: PAPER, BRIAN: SCISSORS}]);
      expect(game.currentPlayers()).toEqual([ROGER]);
      expect(game.isFinished()).toEqual(true);
    });
    
    it('be represented as a JSON object with participants and rounds properties.', function() {
      var game = new Game(JOHN, [FREDDIE, ROGER, BRIAN]);
      game.addRound({FREDDIE: ROCK, ROGER: SCISSORS, BRIAN: PAPER});
      game.addRound({FREDDIE: ROCK, ROGER: SCISSORS, BRIAN: SCISSORS});
      game.addRound({ROGER: PAPER, BRIAN: SCISSORS});
      expect(angular.toJson(game)).toEqual(
        '{"instigator":"JOHN",' + 
         '"participants":' + 
          '["FREDDIE","ROGER","BRIAN"],' +
         '"rounds":' +
          '[{"FREDDIE":"ROCK","ROGER":"SCISSORS","BRIAN":"PAPER"},' + 
           '{"FREDDIE":"ROCK","ROGER":"SCISSORS","BRIAN":"SCISSORS"},' + 
           '{"ROGER":"PAPER","BRIAN":"SCISSORS"}]}');
    });
});
  
  describe('Round Codec', function() {
    var roundCodec;
    beforeEach(inject(function ($injector) {
     roundCodec = $injector.get('RoundCodec');
    }));
    
    describe('serialiser', function() {
    	
      it('should serialise a round in the order of the players supplied', function() {
        expect(roundCodec.serialise([ROGER, JOHN, BRIAN], {JOHN: PAPER, ROGER: SCISSORS, BRIAN: ROCK})).toEqual('spr');
      });
      
      it('should ignore players who are currently not playing.', function() {
        expect(roundCodec.serialise([ROGER, JOHN, BRIAN, FREDDIE], {JOHN: PAPER, ROGER: SCISSORS, BRIAN: ROCK})).toEqual('spr');
      });
    });
    
    describe('deserialiser', function() {

      it('should deserialise a round using the orde order of the players supplied', function() {
        expect(roundCodec.deserialise([ROGER, JOHN, BRIAN], 'spr')).toEqual({ROGER: SCISSORS, JOHN: PAPER, BRIAN: ROCK});
      });

      it('should ignore players who are currently not playing', function() {
        expect(roundCodec.deserialise([ROGER, JOHN, BRIAN, FREDDIE], 'spr')).toEqual({ROGER: SCISSORS, JOHN: PAPER, BRIAN: ROCK});
      });
    });
  });
  
  describe('Game Codec', function() {
	var gameCodec;
	var Game;
	beforeEach(inject(function ($injector) {
	  gameCodec = $injector.get('GameCodec');
	  Game = $injector.get('Game');
	}));
	
	describe('serialiser', function() {

	  it('should serialise a new game as a list of players', function() {
		var game = new Game(JOHN, [FREDDIE, BRIAN, ROGER]);
	    expect(gameCodec.serialise(game)).toEqual("JOHN,FREDDIE;BRIAN;ROGER");
	  });

	  it('should serialise plays in the order of the original participants', function() {
		var game = new Game(JOHN, [FREDDIE, BRIAN, ROGER]);
		game.addRound({BRIAN: ROCK, FREDDIE: SCISSORS, ROGER: SCISSORS});
		game.addRound({ROGER: SCISSORS, FREDDIE:PAPER});
	    expect(gameCodec.serialise(game)).toEqual("JOHN,FREDDIE;BRIAN;ROGER,srs,ps");
	  });
	});
	
	describe('deserialiser', function() {

	  it('should deserialise a list of players as a new game', function() {
	    var game = gameCodec.deserialise("JOHN,FREDDIE;BRIAN;ROGER");
	    expect(game.instigator).toEqual(JOHN);
	    expect(game.participants).toEqual([FREDDIE, BRIAN, ROGER]);
	    expect(game.rounds).toEqual([]);
	    expect(game.currentPlayers()).toEqual([FREDDIE, BRIAN, ROGER]);
	    expect(game.isFinished()).toEqual(false);
	  });

	  it('should deserialise a list of players followed by rounds as a complete game', function() {
		var game = gameCodec.deserialise("JOHN,FREDDIE;BRIAN;ROGER,srs,ps");
	    expect(game.instigator).toEqual(JOHN);
		expect(game.participants).toEqual([FREDDIE, BRIAN, ROGER]);
		expect(game.rounds).toEqual([
		  {BRIAN: ROCK, FREDDIE: SCISSORS, ROGER: SCISSORS}, {ROGER: SCISSORS, FREDDIE:PAPER}]);
	    expect(game.currentPlayers()).toEqual([FREDDIE]);
	    expect(game.isFinished()).toEqual(true);
	  });
	});
  });
});
