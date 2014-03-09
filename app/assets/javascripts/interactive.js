var interactiveApp = angular.module(
  'interactive',
  ['rokta.common.players', 'rokta.common.panel',
   'rokta.common.routing', 'rokta.common.interactive', 'rokta.common.auth',
   'restangular', 'ui.bootstrap', 'ngRoute', 'ngAnimate']);

interactiveApp.config(['$routeProvider',
function($routeProvider) {
  $routeProvider.when('/coyi', {
    templateUrl : 'assets/angular/interactive/partials/game.html',
    controller : 'GameCtrl'
  }).when('/cancel', {
    templateUrl : 'assets/angular/interactive/partials/game.html',
    controller : 'CancelCtrl'
  }).otherwise({
    redirectTo : '/coyi'
  });
}]);

interactiveApp.controller('NavCtrl', ['$scope', '$location',
function($scope, $location) {
  $scope.navigateTo = function(location) {
    $location.path(location);
    return false;
  };
  $scope.nav = [{
    "name": "Cancel",
    "icon": "times",
    "link": "cancel"
  }];
}]);

interactiveApp.service('GameBuilder', [function() {
  var service = function(players, previousRounds, currentPlayers, currentRound) {
    players = _.sortBy(players);
    var rounds = _.map(previousRounds, function(previousRound) {
      return _.map(players, function(player) {
        return previousRound[player] || false;
      });
    });
    if (_.isObject(currentRound)) {
      rounds.push(_.map(players, function(player) {
        return _.contains(currentPlayers, player) ? (currentRound[player] ? "PLAYED" : "WAITING") : false;
      }));
    }
    return { players: players, rounds: rounds };
  };
  return service;
}]);

interactiveApp.service('InteractiveGame', ['GameBuilder', 'AUTH', function(GameBuilder, AUTH) {
  var service = {
    waitingForPlayers: function(state) {
      var players = _.sortBy(state.players);
      return {
        game: GameBuilder(players, [], [], false),
        joined: _.contains(state.players, AUTH.name),
        startable: players.length >= 2
      };
    },
    gameInProgress: function(state) {
      var players = _.sortBy(state.originalPlayers);
      return {
        game: GameBuilder(players, state.previousRounds, state.currentPlayers, state.currentRound),
        round: state.previousRounds.length + 1,
        progressMax: state.currentPlayers.length,
        progressValue: state.currentPlayers.length - _.keys(state.currentRound).length,
        playing: _.contains(state.currentPlayers, AUTH.name),
        myHand: state.currentRound[AUTH.name]
      };
    },
    gameOver: function(state) {
      return {
        game: GameBuilder(state.players, state.rounds, [], false),
        loser: state.loser
      };
    }
  };
  return service;
}]);

interactiveApp.controller('InteractiveCtrl',
['$scope', '$window', 'Restangular', 'Interactive', 'InteractiveGame', 'ROUTES', 'AUTH',
function($scope, $window, Restangular, Interactive, InteractiveGame, ROUTES, AUTH) {
  var onSuccess = function(exemptPlayer) {
    $scope.exemptPlayer = exemptPlayer;
    $scope.exempt = AUTH.name && (exemptPlayer == AUTH.name);
    Interactive.onStateChange(function(state) {
      $scope.state = state;
      if (InteractiveGame[state.type]) {
        _.assign($scope, InteractiveGame[state.type](state));
      }
    });
  }
  var onFailure = function() {
    $window.alert("Cannot find who the exempt player is");
  }
  Restangular.oneUrl('exemptPlayer', ROUTES.exemptPlayer).get().then(onSuccess, onFailure);
}]);

interactiveApp.controller('GameCtrl',
['$scope', '$location', '$window', 'Interactive',
function($scope, $location, $window, Interactive) {
  $scope.instigate = Interactive.instigate;
  $scope.join = Interactive.join;
  $scope.start = Interactive.start;
  $scope.play = Interactive.play;
  $scope.accept = Interactive.accept;
}]);

interactiveApp.controller('CancelCtrl', ['$scope', '$window', 'Interactive', 'ROUTES',
function ($window, Interactive, ROUTES) {
  Interactive.cancel();
  $window.location.href = ROUTES.index;
}]);

interactiveApp.directive('roktaInteractiveButton', function() {
  return {
    restrict : 'AE',
    templateUrl : '/assets/angular/interactive/directives/button.html',
    scope : {
      busy : '=',
      waiting : '=',
      text : '@',
      ngClick : '&'
    }
  };
});

interactiveApp.directive('roktaInteractiveGame', function() {
  return {
    restrict : 'AE',
    templateUrl : '/assets/angular/interactive/directives/state.html',
    scope : {
      players: '=',
      rounds: '='
    }
  };
});
