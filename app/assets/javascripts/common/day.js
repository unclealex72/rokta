var day = angular.module('rokta.common.day', ['ngSanitize']);

day.factory('Now', function() { return new Date(); });

day.service('Today', ['Now', function(Now) {
  var service = {
    get: function() {
      return { year: Now.getFullYear(), month: Now.getMonth() + 1, day: Now.getDate() };
    }
  };
  return service;
}]);

day.service('DateSerialiser', function() {
  var zeroPad = function(num, numZeros) {
    var n = Math.abs(num);
    var zeros = Math.max(0, numZeros - Math.floor(n).toString().length);
    var zeroString = Math.pow(10, zeros).toString().substr(1);
    if (num < 0) {
      zeroString = '-' + zeroString;
    }
    return zeroString + n;
  };
  var service = {
    serialise : function(date) {
      var args = [{
        length : 4,
        value : date.year
      }, {
        length : 2,
        value : date.month
      }, {
        length : 2,
        value : date.day
      }];
      return _.reduce(args, function(str, arg) {
        if (!_.isNumber(arg.value)) {
          return str;
        } else {
          return str + zeroPad(arg.value, arg.length);
        }
      }, "");
    },
    deserialise : function(str) {
      var matches = str.match(/^([0-9]{4})([0-9]{2})?([0-9]{2})?$/);
      if (matches) {
        return _.reduce(["year", "month", "day"], function(result, key, index) {
          var match = matches[index + 1];
          if (match) {
            result[key] = parseInt(match);
          }
          return result;
        }, {});
      } else {
        return null;
      }
    }
  };
  return service;
});

day.service('DateSuffix', function() {
  var suffices = ['th', 'st', 'nd', 'rd', 'th', 'th', 'th', 'th', 'th', 'th'];
  return {
    suffixFor : function(dayOfMonth) {
      dayOfMonth = parseInt(dayOfMonth);
      var suffixIndex;
      if (dayOfMonth >= 10 && dayOfMonth < 20) {
        suffixIndex = 0;
      } else {
        suffixIndex = dayOfMonth % 10;
      }
      return suffices[suffixIndex];
    }
  };
});

day.service('Sanitiser', function() {
  return {
    sanitise : function(obj) {
      return _.omit(obj, function(value) {
        return !_.isNumber(value);
      });
    }
  };
});

day.filter('roktaYear', ['Sanitiser',
function(Sanitiser) {
  return function(day) {
    if (!day) {
      return;
    }
    return "" + Sanitiser.sanitise(day).year;
  };
}]);

day.filter('roktaMonth', ['Sanitiser', '$filter',
function(Sanitiser, $filter) {
  var dateFilter = $filter("date");
  return function(day, excludeYear) {
    if (!day) {
      return;
    }
    day = Sanitiser.sanitise(day);
    var templateDay = new Date(day.year, day.month - 1, 1);
    return excludeYear ? dateFilter(templateDay, 'MMMM') : dateFilter(templateDay, 'MMMM yyyy');
  };
}]);

day.filter('roktaDay', ['DateSuffix', 'Sanitiser', '$sce', '$filter',
function(DateSuffix, Sanitiser, $sce, $filter) {
  var monthFilter = $filter("roktaMonth");
  return function(day, excludeMonth, excludeYear) {
    if (_.isString(day)) {
      day = new Date(day);
    }
    if (_.isDate(day)) {
      day = {
        year : day.getFullYear(),
        month : day.getMonth() + 1,
        day : day.getDate()
      };
    }
    day = Sanitiser.sanitise(day);
    var output = "the " + day.day + "<span class='day-suffix'>" + DateSuffix.suffixFor(day.day) + "</span>";
    if (!excludeMonth) {
      output += " of " + monthFilter(day, excludeYear);
    }
    return $sce.trustAsHtml(output);
  };
}]); 