// Test definitions for the two constant modules.

angular.module('rokta.common.auth', []).
constant(
  'AUTH', { name: "Freddie" }
);

angular.module('rokta.common.routing', []).constant(
  'ROUTES', {
    // Web pages
    "index": "index",
    "interactiveGame": "interactiveGame",
    "nonInteractiveGame": "nonInteractiveGame",
    // Ajax
    "stats": "stats",
    "limits": "limits",
    "players": "players",
    "exemptPlayer": "exemptPlayer",
    "colours": "colours",
    "upload": "upload",
    "availablePlayers": "availablePlayers",
    // Websockets
    "ws": "ws"
  });
