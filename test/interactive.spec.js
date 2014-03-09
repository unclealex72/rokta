'use strict';

/* jasmine specs for controllers go here */
describe("The interactive games module's", function() {

  var messageQueue;
  beforeEach(module('interactive', function ($provide) {
    messageQueue = {
      message: null,
      listener: null,
      send: function(message) {
        messageQueue.message = message;
      },
      onmessage: function(listener) {
        messageQueue.listener = listener;
      },
      receive: function(message) {
        listener(message);
      }
    };
    $provide.value('messageQueue', messageQueue);
  }));

  describe('GameBuilder service', function() {

    var GameBuilder;
    beforeEach(inject(function($injector) {
      GameBuilder = $injector.get('GameBuilder');
    }));

    it('should build an empty game when the game has just started', function() {
      var game = GameBuilder([], [], [], false);
      expect(game.players).toEqual([]);
      expect(game.rounds).toEqual([]);
    });

    it('should build an empty game but with players when players are joining', function() {
      var game = GameBuilder(["Freddie", "Brian"], [], [], false);
      expect(game.players).toEqual(["Brian", "Freddie"]);
      expect(game.rounds).toEqual([]);
    });

    it('should keep track of who has played what in the current and previous rounds', function() {
      var game = GameBuilder(
        ["Freddie", "Roger", "Brian"],
        [{"Freddie": "ROCK", "Brian": "SCISSORS", "Roger": "SCISSORS"},
         {"Roger": "PAPER", "Brian": "PAPER"}
        ],
        ["Roger", "Brian"],
        {"Roger": "ROCK"});
      expect(game.players).toEqual(["Brian", "Freddie", "Roger"]);
      expect(game.rounds).toEqual([
        ["SCISSORS", "ROCK", "SCISSORS"],
        ["PAPER", false, "PAPER"],
        ["WAITING", false, "PLAYED"]
      ]);
    });
  });

  describe("InteractiveGame service", function() {
    var Game, result, state;
    beforeEach(inject(function($injector) {
      Game = $injector.get('InteractiveGame');
    }));

    describe("whilst waiting for players", function() {

      describe("when there are no players", function() {
        beforeEach(inject(function($injector) {
          state = { type: "waitingForPlayers", players: [] };
          result = Game[state.type](state);
        }));
        it("should not let the game start", function() {
          expect(result.startable).toEqual(false);
        });
        it("should indicate the current player has not joined", function() {
          expect(result.joined).toEqual(false);
        });
        it("should return an empty game", function() {
          expect(result.game).toEqual({ players: [], rounds: []});
        });
      });

      describe("when there is just one player", function() {
        beforeEach(inject(function($injector) {
          state = { type: "waitingForPlayers", players: ["Brian"] };
          result = Game[state.type](state);
        }));
        it("should not let the game start", function() {
          expect(result.startable).toEqual(false);
        });
        it("should indicate the current player has not joined", function() {
          expect(result.joined).toEqual(false);
        });
        it("should return an empty game", function() {
          expect(result.game).toEqual({ players: ["Brian"], rounds: [] });
        });
      })

      describe("when there is more than one player", function() {
        beforeEach(inject(function($injector) {
          state = { type: "waitingForPlayers", players: ["Freddie", "Brian"] };
          result = Game[state.type](state);
        }));
        it("should let the game start", function() {
          expect(result.startable).toEqual(true);
        });
        it("should indicate the current player has joined", function() {
          expect(result.joined).toEqual(true);
        });
        it("should return an empty game", function() {
          expect(result.game).toEqual({ players: ["Brian", "Freddie"], rounds: [] });
        });
      })
    });

    describe("when the game is in progress", function() {
      it("should accurately transpose the game", function() {
        state = {
          type: "gameInProgress",
          instigator: "Freddie",
          originalPlayers: ["Brian", "Roger", "Freddie"],
          currentPlayers: ["Brian", "Roger"],
          currentRound: {"Brian": "ROCK"},
          previousRounds: [{"Brian": "PAPER", "Roger": "PAPER", "Freddie": "SCISSORS"}]};
        result = Game[state.type](state);
        expect(result.game).toEqual({
          players: ["Brian", "Freddie", "Roger"],
          rounds: [["PAPER", "SCISSORS", "PAPER"], ["PLAYED", false, "WAITING"]]
        });
        expect(result.round).toEqual(2);
      });
      it("should indicate if the logged in player is still playing", function() {
        state = {
          type: "gameInProgress",
          instigator: "Freddie",
          originalPlayers: ["Brian", "Roger", "Freddie"],
          currentPlayers: ["Brian", "Freddie"],
          currentRound: {"Brian": "ROCK"},
          previousRounds: [{"Brian": "PAPER", "Freddie": "PAPER", "Roger": "SCISSORS"}]};
        result = Game[state.type](state);
        expect(result.playing).toEqual(true);
        expect(result.myHand).toBeUndefined();
      });
      it("should indicate if the logged in player is not playing", function() {
        state = {
          type: "gameInProgress",
          instigator: "Freddie",
          originalPlayers: ["Brian", "Roger", "Freddie"],
          currentPlayers: ["Brian", "Roger"],
          currentRound: {"Brian": "ROCK"},
          previousRounds: [{"Brian": "PAPER", "Roger": "PAPER", "Freddie": "SCISSORS"}]};
        result = Game[state.type](state);
        expect(result.playing).toEqual(false);
        expect(result.myHand).toBeUndefined();
      });
      it("should tell me which hand I played", function() {
        state = {
          type: "gameInProgress",
          instigator: "Freddie",
          originalPlayers: ["Brian", "Roger", "Freddie"],
          currentPlayers: ["Brian", "Freddie"],
          currentRound: {"Freddie": "ROCK"},
          previousRounds: [{"Brian": "PAPER", "Freddie": "PAPER", "Roger": "SCISSORS"}]};
        result = Game[state.type](state);
        expect(result.myHand).toEqual("ROCK");
      });
      it("should count the number of players yet to play", function() {
        state = {
          type: "gameInProgress",
          instigator: "Freddie",
          originalPlayers: ["Brian", "Roger", "Freddie"],
          currentPlayers: ["Brian", "Roger"],
          currentRound: {"Brian": "ROCK"},
          previousRounds: [{"Brian": "PAPER", "Roger": "PAPER", "Freddie": "SCISSORS"}]};
        result = Game[state.type](state);
        expect(result.progressMax).toEqual(2);
        expect(result.progressValue).toEqual(1);
      });
    });
  });
});
