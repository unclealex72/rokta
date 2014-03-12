var interactive = angular.module('rokta.common.interactive', ['rokta.common.routing', 'rokta.common.auth']);

interactive.service('MessageQueue', ['$log', 'ROUTES', function($log, ROUTES) {
  var ws = new WebSocket(ROUTES.ws);
  return {
    onOpen: function(listener) {
      ws.onopen = function() {
        $log.info("Socket has been opened!");
        listener();
      }
    },
    onMessage: function(listener) {
      ws.onmessage = listener;
    },
    send: function(message) {
      ws.send(message);
    }
  };
}]);

interactive.service('Interactive', ['$log', '$rootScope', 'MessageQueue', 'AUTH',
function($log, $rootScope, MessageQueue, AUTH) {
  var service = {
    onStateChange: function(listener) {
      MessageQueue.onOpen(function() {
        service.send({type: "sendCurrentState"});
      });
      MessageQueue.onMessage(function(message) {
        var state = angular.fromJson(message.data).state;
        _(state).assign({inProgress: state.type != "notStarted"});
        $log.info("Received state: " + angular.toJson(state));
        $rootScope.$apply(function() { listener(state); });
      });
    },
    send: function(message) {
      var data = angular.toJson(message);
      $log.info("Sending " + data);
      MessageQueue.send(data);
    },
    instigate: function() {
      service.send({type: "instigator", instigator: AUTH.name});
    },
    join: function() {
      service.send({type: "newPlayer", player: AUTH.name});
    },
    start: function() {
      service.send({type: "startGame"});
    },
    play: function(hand) {
      service.send({type: "handPlayed", player: AUTH.name, hand: hand});
    },
    accept: function() {
      service.send({type: "acceptGame"});
    },
    undo: function() {
      service.send({type: "back"});
    },
    cancel: function() {
      service.send({type: "cancel"});
    }
  };
  return service;
}]);
