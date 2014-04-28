/**
 * A small wrapper for the HTML Notification API
 */

var notify = angular.module('rokta.common.notify', []);

notify.service('Notify', [
function() {
  var service = {
    show : function(text) {
      // Let's check if the browser supports notifications
      if (!("Notification" in window)) {
        // No it doesn't so do nothing
      }

      // Let's check if the user is okay to get some notification
      else if (Notification.permission === "granted") {
        // If it's okay let's create a notification
        var notification = new Notification("Hi there!");
      }

      // Otherwise, we need to ask the user for permission
      // Note, Chrome does not implement the permission static property
      // So we have to check for NOT 'denied' instead of 'default'
      else if (Notification.permission !== 'denied') {
        Notification.requestPermission(function (permission) {

          // Whatever the user answers, we make sure we store the information
          if(!('permission' in Notification)) {
            Notification.permission = permission;
          }

          // If the user is okay, let's create a notification
          if (permission === "granted") {
            var notification = new Notification(text);
          }
        });
      }

      // At last, if the user already denied any notification, and you
      // want to be respectful there is no need to bother him any more.
    }
  };
  return service;
}]);
