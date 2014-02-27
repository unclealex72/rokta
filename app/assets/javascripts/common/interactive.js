var interactive = angular.module('rokta.common.interactive', ['rokta.common.routing', 'rokta.common.auth']);

interactive.service('Interactive', ['$log', 'ROUTES', 'AUTH',
function($log, ROUTES, AUTH) {
  var ws = new WebSocket(ROUTES.ws);
  ws.onopen = function(){
    $log.info("Socket has been opened!");
  };
  var service = {
    onStateChange: function(listener) {
      ws.onmessage = function(message) {
        var state = angular.fromJson(message.data).state;
        _(state).assign({inProgress: state.type != "notStarted"});
        $log.info("Received state: " + angular.toJson(state));
        listener(state);
      };
    },
    send: function(message) {
      var data = angular.toJson(message);
      $log.info("Sending " + data);
      ws.send(data);
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
    cancel: function() {
      service.send({type: "cancel"});
    }
  };
  return service;
}]);
