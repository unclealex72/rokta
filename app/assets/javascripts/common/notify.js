/**
 * A small wrapper for the HTML Notification API
 */

var notify = angular.module('rokta.common.notify', []);

notify.service('Notify', ['$window',
function($window) {
  var service;
  if ("Notification" in $window) {
    var nfn = $window.Notification;
    service = {
      permissionRequestRequired: function() {
        return !_(["granted", "denied"]).contains(nfn.permission);
      },
      requestPermission: function(callback) {
        nfn.requestPermission(function(permission) {
          nfn.permission = permission;
          callback(permission);
        });
      },
      show: function(text) {
        var showNotification = function() {
          var n = new Notification(text);
        };
        if (service.permissionRequestRequired()) {
          service.requestPermission(showNotification);
        }
        else {
          showNotification();
        }
      }
    };
  }
  else {
    service = {
      permissionRequestRequired: function() {
        return false;
      },
      requestPermission: function(callback) {
        return callback("denied");
      },
      show: function(text) {
        return;
      }
    };
  }
  return service;
}]);
