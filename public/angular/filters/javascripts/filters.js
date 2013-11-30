var filter = angular.module('rokta.filters', ['rokta.day', 'rokta.events', 'ngRoute']);

filter.directive('roktaDayPicker', ['$timeout', '$filter', function($timeout, $filter) {
  return {
    restrict : 'A',
    templateUrl : '/assets/angular/filters/directives/daypicker.html',
    replace: true,
    scope : {
      min : '@',
      max : '@',
      ngModel : '=',
      ngDisabled : '='
    },
    link : function($scope, elem, attrs) {
      $scope.opened = false;
      $scope.open = function() {
        $timeout(function() {
          $scope.opened = true;
        });
      };
      $scope.$watch('ngModel', function(ngModel) {
        if (ngModel) {
          $scope.rawDate = $filter('date')(
            new Date(ngModel.year, ngModel.month - 1, ngModel.day), 'dd-MMMM-yyyy');
          $scope.$watch('rawDate', function(rawDate) {
            var date = new Date(rawDate);
            ngModel.year = date.getFullYear();
            ngModel.month = date.getMonth() + 1;
            ngModel.day = date.getDate();
          });
        }
      });
    }
   };
}]);

filter.service('CurrentFilter', ['Filter', 'Today', '$routeParams',
function(Filter, Today, $routeParams) {
  var service = {
    asString : function() {
      var filter = $routeParams.filter;
      if (!filter) {
        filter = 'd' + Today.get().year;
      }
      return filter;
    },
    asFilter : function() {
      return Filter.of(service.asString());
    }
  };
  return service;
}]);

filter.service('Filter', ['DateSerialiser',
function(DateSerialiser) {
  var service = {
    of : function(filter) {
      var match = filter.match(/^(d|s|u|b)([0-9]+)$/);
      if (match == null) {
        return null;
      }
      var filterType = match[1];
      var dateInfo = match[2];
      var dateInfoLength = dateInfo.length;
      if (filterType == 'd') {
        var types = [{
          length : 4,
          type : "year"
        }, {
          length : 6,
          type : "month"
        }, {
          length : 8,
          type : "day"
        }];
        var type = _.find(types, function(type) {
          return type.length == dateInfoLength;
        });
        if (type) {
          var filter = DateSerialiser.deserialise(dateInfo);
          _.extend(filter, {
            "type" : type.type
          });
          return filter;
        } else {
          return null;
        }
      } else if ("su".indexOf(filterType) >= 0 && dateInfoLength == 8) {
        var filter = DateSerialiser.deserialise(dateInfo);
        var type = (filterType == 's') ? "since" : "until";
        _.extend(filter, {
          "type" : type
        });
        return filter;
      } else if (filterType == 'b' && dateInfoLength == 16) {
        return {
          "type" : "between",
          from : DateSerialiser.deserialise(dateInfo.substring(0, 8)),
          to : DateSerialiser.deserialise(dateInfo.substring(8))
        };
      }
      return null;
    },
    serialise: function(filter) {
      var serialise = function(prefix, dates) {
        return _.foldl(dates, function(serialised, date) {
          return serialised + DateSerialiser.serialise(date);
        }, prefix);
      };
      switch (filter.type) {
        case 'year': return serialise('d', [filter]);
        case 'month': return serialise('d', [filter]);
        case 'day': return serialise('d', [filter]);
        case 'since': return serialise('s', [filter]);
        case 'until': return serialise('u', [filter]);
        case 'between': return serialise('b', [filter.from, filter.to]);
      }
    }
  };
  return service;
}]);

filter.filter('roktaGame', ['$sce', '$filter',
function($sce, $filter) {
  var dayFilter = $filter('roktaDay');
  var monthFilter = $filter('roktaMonth');
  var yearFilter = $filter('roktaYear');
  return function(gameFilter) {
    var output;
    if (!gameFilter) {
      return;
    }
    var type = gameFilter.type;
    var singleDateTypes = {
      "day" : "on",
      "until" : "until",
      "since" : "since"
    };
    if (singleDateTypes[type]) {
      var prefix = singleDateTypes[type];
      output = prefix + " " + dayFilter(gameFilter);
    } else if (type == "year") {
      output = "in " + yearFilter(gameFilter);
    } else if (type == "month") {
      output = "in " + monthFilter(gameFilter);
    } else if (type == "between") {
      var from = gameFilter.from;
      var to = gameFilter.to;
      if (from.year != to.year) {
        output = "from " + dayFilter(from) + " until " + dayFilter(to);
      } else {
        if (from.month != to.month) {
          output = "from " + dayFilter(from, false, true) + " until " + dayFilter(to);
        } else {
          if (from.day != to.day) {
            output = "from " + dayFilter(from, true, true) + " until " + dayFilter(to);
          } else
            output = "on " + dayFilter(from);
        }
      }
    }
    return $sce.trustAsHtml(output);
  };
}]);

filter.service('Limits', ['$filter', 'Events',
function($filter, Events) {
  var monthsByIndex = _.map(_.range(0, 12), function(index) {
    var templateDate = new Date(1970, index, 1);
    var month = $filter('date')(templateDate, 'MMMM');
    return {
      index : index + 1,
      month : month
    };
  });
  var service = {
    initialised : false,
    name : 'limits.update',
    limits : null,
    refresh : function() {
      Events.refresh(service, 'limits', function(limits) {
        service.limits = {
          first : new Date(limits.first),
          last : new Date(limits.last)
        };
      });
    },
    years : function() {
      return _.range(service.limits.first.getFullYear(), service.limits.last.getFullYear() + 1);
    },
    months : function(year) {
      var months = monthsByIndex;
      var lteq = function(i1, i2) {
        return i1 <= i2;
      };
      var gteq = function(i1, i2) {
        return i1 >= i2;
      };
      var filter = function(months, date, limiter) {
        if (year == date.getFullYear()) {
          months = _.filter(months, function(obj) {
            return limiter(obj.index, date.getMonth() + 1);
          });
        }
        return months;
      };
      months = filter(months, service.limits.first, gteq);
      months = filter(months, service.limits.last, lteq);
      return months;
    }
  };
  return service;
}]);

filter.factory('DefaultFilters', ['Today',
function(Today) {
  var defaults = {
    year : {
      year : Today.get().year
    },
    month : {
      year : Today.get().year,
      month : Today.get().month
    },
    day : Today.get(),
    since : Today.get(),
    until : Today.get(),
    between : {
      from : Today.get(),
      to : Today.get()
    }
  };
  return _.mapValues(defaults, function(filter, type) {
    return _.assign(filter, {
      type : type
    });
  });
}]);

filter.controller('FiltersCtrl', ['$timeout', '$scope', 'Events', 'Limits', 'Filter', 'CurrentFilter', 'DefaultFilters', '$location',
function($timeout, $scope, Events, Limits, Filter, CurrentFilter, DefaultFilters, location) {
  Limits.refresh();
  Events.listenTo($scope, Limits, function() {
    var limits = Limits.limits;
    $scope.firstGameDate = limits.first;
    $scope.lastGameDate = limits.last;
    var currentFilter = CurrentFilter.asFilter();
    $scope.type = currentFilter.type;
    $scope.filters = {};
    // make sure the overlying filter type is included in the generated filter.
    $scope.$watch('type', function(type) {
      if (!$scope.filters[type]) {
        $scope.filters[type] = {};
      }
      $scope.filters[type].type = type;
    });
    // Set the filters to their defaults
    _.forOwn(DefaultFilters, function(filter, type) {
      var defaultFilter = type == currentFilter.type ? currentFilter : filter;
      $scope.filters[type] = defaultFilter;
    });
    $scope.filters[currentFilter.type] = currentFilter;
    $scope.years = Limits.years();
    $scope.months = Limits.months;

    $scope.go = function() {
      var filter = Filter.serialise($scope.filters[$scope.type]);
      location.path('league/' + filter);
    };
  });
}]);
