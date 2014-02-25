var players = angular.module('rokta.common.players', ['rokta.common.events']);


players.service('Players', ['Events',
function(Events) {
  var service = {
    initialised : false,
    name : 'players.update',
    players : [],
    refresh : function() {
      Events.refresh(service, 'players', function(players) {
        service.players = players.players;
      });
    }
  };
  return service;
}]);

