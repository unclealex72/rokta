var game = angular.module('rokta.game', []);

game.constant('ROCK', 'ROCK');
game.constant('SCISSORS', 'SCISSORS');
game.constant('PAPER', 'PAPER');

game.service('LoserService', ['ROCK', 'SCISSORS', 'PAPER',
function(rock, scissors, paper) {
  var service = {
    losers : function(plays) {
      var hands = {};
      _.forOwn(plays, function(hand, player) {
        var knownHands = hands[hand];
        if (!knownHands) {
          knownHands = [];
          hands[hand] = knownHands;
        }
        knownHands.push(player);
      });
      if (_.keys(hands).length != 2) {
        return _.keys(plays);
      } else {
        var losingKey;
        if (hands[rock] && hands[paper]) {
          losingKey = rock;
        } else if (hands[rock] && hands[scissors]) {
          losingKey = scissors;
        } else {
          losingKey = paper;
        }
        return hands[losingKey];
      }
    }
  };
  return service;
}]);

// A factory to create a new Game object.
game.factory('Game', ['LoserService', 'ROCK', 'SCISSORS', 'PAPER',
function(LoserService, rock, scissors, paper) {

  function Game(instigator, participants) {
    this.instigator = instigator;
    this.participants = participants;
    this.rounds = [];
  };

  Game.prototype = {
    currentPlayers : function() {
      if (_.isEmpty(this.rounds)) {
        return this.participants;
      } else {
        return LoserService.losers(_.last(this.rounds));
      }
    },
    isFinished : function() {
      return this.currentPlayers().length == 1;
    },
    addRound : function(plays) {
      this.rounds.push(plays);
    }
  };
  return Game;
}]);

game.service('RoundCodec', ['ROCK', 'SCISSORS', 'PAPER',
function(rock, scissors, paper) {
  var handDeserialisers = {
    'r' : rock,
    's' : scissors,
    'p' : paper
  };
  var handSerialisers = _.reduce(handDeserialisers, function(result, hand, serialisation) {
    result[hand] = serialisation;
    return result;
  }, {});

  var service = {
    serialise : function(participants, plays) {
      return _.reduce(participants, function(str, participant) {
        var play = plays[participant];
        return play ? (str + handSerialisers[plays[participant]]) : str;
      }, "");
    },
    deserialise : function(participants, str) {
      var hands = _.map(str.split(''), function(ch) {
        return handDeserialisers[ch];
      });
      return _.zipObject(participants, hands);
    }
  };
  return service;
}]);

game.service('GameCodec', ['Game', 'RoundCodec',
function(Game, RoundCodec) {
  var service = {
    serialise : function(game) {
      var strings = [];
      strings.push(game.instigator);
      strings.push(game.participants.join(';'));
      _.forOwn(game.rounds, function(plays) {
        strings.push(RoundCodec.serialise(game.participants, plays));
      });
      return strings.join(',');
    },
    deserialise : function(str) {
      var strings = str.split(',');
      var instigator = _.head(strings);
      strings = _.tail(strings);
      var participants = _.head(strings).split(';');
      return _.reduce(_.tail(strings), function(game, round) {
        game.addRound(RoundCodec.deserialise(game.currentPlayers(), round));
        return game;
      }, new Game(instigator, participants));
    }
  };
  return service;
}]);

game.controller('GameCtrl', ['GameCodec', '$routeParams', '$scope', '$http', '$location',
function(GameCodec, $routeParams, $scope, $http, $location) {
  $http.get('game').success(function(data, status) {
    $scope.authenticated = true;
    var serialisedGame = $routeParams.game;
    if (serialisedGame) {
      $scope.game = GameCodec.deserialise(serialisedGame);
      $scope.finished = $scope.game.isFinished();
      if ($scope.finished) {
        $scope.loser = $scope.game.currentPlayers()[0];        
        $scope.next = function() {
          $http.post('game', $scope.game).success(function(data, status) {
            $location.path("");
          }).error(function(data, status) {
            alert(status + ": " + angular.toJson(data));
          });
        };
      }
      else {
        $scope.currentPlayers = $scope.game.currentPlayers();
        $scope.round = $scope.game.rounds.length + 1;
        $scope.plays = {};
        $scope.registerHand = function(name, hand) {
          $scope.plays[name] = hand;
        };
        $scope.next = function() {
          $scope.game.addRound($scope.plays);
          $location.path("game/" + GameCodec.serialise($scope.game));
        };
      }
    }
    else {
      $http.get('game/availablePlayers').success(function(data, status) {
        $scope.players = _.sortBy(data.availablePlayers);
        $scope.next = function(game) {
          $location.path("game/" + GameCodec.serialise(game));
        };
      });
    }
  }).error(function(data, status) {
    if (status == 401) {
      // REDIRECT TO LOGIN PAGE
      $scope.authenticated = false;
    }
    else if (status == 403) {
      $scope.authenticated = false;
    }
  });

}]); 