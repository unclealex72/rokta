var interactiveApp = angular.module(
  'interactive',
  ['rokta.common.players', 'rokta.common.panel', 'rokta.common.avatar',
   'rokta.common.routing', 'rokta.common.interactive', 'rokta.common.auth',
   'restangular', 'ui.bootstrap', 'ngRoute', 'ngAnimate']);

interactiveApp.config(['$routeProvider',
function($routeProvider) {
  $routeProvider.when('/coyi', {
    templateUrl : 'assets/angular/interactive/partials/game.html',
    controller : 'GameCtrl'
  }).when('/join', {
    templateUrl : 'assets/angular/interactive/partials/game.html',
    controller : 'JoinCtrl'
  }).when('/undo', {
    templateUrl : 'assets/angular/interactive/partials/game.html',
    controller : 'UndoCtrl'
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
    }, {
      "name": "Undo",
      "icon": "backward",
      "link": "undo"
  }];
}]);

interactiveApp.service('PlayerStatus', [function() {
  var service = function(originalPlayers, currentPlayers, loser) {
    var decorator;
    if (loser) {
      decorator = function(player) {
        return player == loser ? "LOSER" : "WINNING";
      }
    }
    else if (currentPlayers.length == 0 || originalPlayers.length == currentPlayers.length) {
      decorator = function(player) {
        return "ALL_PLAYING";
      }
    }
    else {
      decorator = function(player) {
        if (_.contains(currentPlayers, player)) {
          return currentPlayers.length == 2 ? "LAST_TWO" : "LOSING";
        }
        else {
          return "WINNING";
        }
      }
    }
    return function(players) {
      return _.map(players, function(player) {
        return { name: player, status: decorator(player) };
      });
    };
  };
  return service;
}]);

interactiveApp.service('GameBuilder', ['PlayerStatus', function(PlayerStatus) {
  var service = function(players, previousRounds, currentPlayers, currentRound, loser) {
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
    return { players: PlayerStatus(players, currentPlayers, loser)(players), rounds: rounds };
  };
  return service;
}]);

interactiveApp.service('InteractiveGame', ['GameBuilder', 'AUTH', function(GameBuilder, AUTH) {
  var service = {
    waitingForPlayers: function(state) {
      var players = _.sortBy(state.players);
      return {
        game: GameBuilder(players, [], [], false, false),
        joined: _.contains(state.players, AUTH.name),
        startable: players.length >= 2
      };
    },
    gameInProgress: function(state) {
      var players = _.sortBy(state.originalPlayers);
      return {
        game: GameBuilder(players, state.previousRounds, state.currentPlayers, state.currentRound, false),
        round: state.previousRounds.length + 1,
        progressMax: state.currentPlayers.length,
        progressValue: state.currentPlayers.length - _.keys(state.currentRound).length,
        playing: _.contains(state.currentPlayers, AUTH.name),
        myHand: state.currentRound[AUTH.name]
      };
    },
    gameOver: function(state) {
      return {
        game: GameBuilder(state.players, state.rounds, [], false, state.loser),
        loser: state.loser
      };
    }
  };
  return service;
}]);

interactiveApp.controller('InteractiveCtrl',
['$scope', '$window', 'Restangular', 'Interactive', 'InteractiveGame', 'ROUTES', 'AUTH',
function($scope, $window, Restangular, Interactive, InteractiveGame, ROUTES, AUTH) {
  $scope.state = { type: false };
  var onSuccess = function(exemptPlayer) {
    $scope.exemptPlayer = exemptPlayer;
    $scope.exempt = AUTH.name && (exemptPlayer.name == AUTH.name);
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
['$scope', '$location', '$window', 'Interactive', 'ROUTES',
function($scope, $location, $window, Interactive, ROUTES) {
  $scope.instigate = Interactive.instigate;
  $scope.join = Interactive.join;
  $scope.start = Interactive.start;
  $scope.play = Interactive.play;
  $scope.accept = function() {
    Interactive.accept();
    $window.location.href = ROUTES.index;
  };
}]);

interactiveApp.controller('CancelCtrl', ['$window', 'Interactive', 'ROUTES',
function ($window, Interactive, ROUTES) {
  Interactive.cancel();
  $window.location.href = ROUTES.index;
}]);

interactiveApp.controller('UndoCtrl', ['$location', 'Interactive',
function ($location, Interactive) {
  Interactive.undo();
  $location.path('/coyi');
}]);

interactiveApp.controller('JoinCtrl', ['$window', 'Interactive', 'ROUTES',
function ($window, Interactive, ROUTES) {
  Interactive.onStateChange(function(state) {
    Interactive.join();
    $window.location.href = ROUTES.interactiveGame;
  });
}]);

interactiveApp.directive('roktaIcon', function() {
  return {
    restrict : 'AE',
    templateUrl : '/assets/angular/interactive/directives/icon.html',
    scope : {
      'if': '=',
      icon: '@'
    }
  };
});

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
