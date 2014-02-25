var colours = angular.module('rokta.common.colours', ['rokta.common.events']);


colours.service('Colours', ['Events',
function(Events) {
  var service = {
    initialised : false,
    name : 'colours.update',
    colours : [],
    colourMap: {},
    refresh : function() {
      Events.refresh(service, 'colours', function(colours) {
        service.colours = colours.colours;
        service.colourMap = _.indexBy(colours.colours, 'token')
      });
    }
  };
  return service;
}]);

