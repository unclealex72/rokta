var players = angular.module('rokta.players', ['rokta.events']);


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

