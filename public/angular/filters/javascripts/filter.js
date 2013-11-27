var filter = angular.module('rokta.filter', ['rokta.day']);
	  
filter.service('Filter', ['DateSerialiser', function(DateSerialiser) {
  var service = {
    of: function(filter) {
      var match = filter.match(/^(d|s|u|b)([0-9]+)$/);
      if (match == null) {
    	return null;
      }
      var filterType = match[1];
      var dateInfo = match[2];
      var dateInfoLength = dateInfo.length;
      if (filterType == 'd') {
    	var types = [{length: 4, type: "year"}, {length: 6, type: "month"}, {length: 8, type: "day"}];
    	var type = _.find(types, function(type) { return type.length == dateInfoLength });
    	if (type) {
        	var filter = DateSerialiser.deserialise(dateInfo);
    	  _.extend(filter, { "type": type.type});
    	  return filter;
    	}
    	else {
    	  return null;
    	}
      }
      else if ("su".indexOf(filterType) >= 0 && dateInfoLength == 8) {
    	var filter = DateSerialiser.deserialise(dateInfo);
    	var type = (filterType == 's') ? "since": "until";
    	_.extend(filter, {"type": type});
    	return filter;
      }
      else if (filterType == 'b' && dateInfoLength == 16) {
    	return {
    	  "type": "between", 
    	  from: DateSerialiser.deserialise(dateInfo.substring(0, 8)),
    	  to: DateSerialiser.deserialise(dateInfo.substring(8))};
      }
      return null;
    },
    date: function(date) {
      return "d" + DateSerialiser.serialise(date);
    },
    since: function(since) {
      return "s" + DateSerialiser.serialise(since);
    },
    until: function(until) {
      return "u" + DateSerialiser.serialise(until);
    },
    between: function(from, to) {
      return "b" + DateSerialiser.serialise(from) + DateSerialiser.serialise(to);	
    }
  };
  return service;
}]);

filter.filter('roktaGame', ['$sce', '$filter', function($sce, $filter) {
  var dayFilter = $filter('roktaDay');
  var monthFilter = $filter('roktaMonth');
  var yearFilter = $filter('roktaYear');
  return function(gameFilter) {
	var output;
	if (!gameFilter) {
	  return;
	}
	var type = gameFilter.type;
	var singleDateTypes = {"day": "on", "until": "until", "since": "since"};
	if (singleDateTypes[type]) {
	  var prefix = singleDateTypes[type];
	  output = prefix + " " + dayFilter(gameFilter);
	}
	else if (type == "year") {
	  output = "in " + yearFilter(gameFilter);
	}
	else if (type == "month") {
	  output = "in " + monthFilter(gameFilter);
	}
	else if (type == "between") {
	  var from = gameFilter.from;
	  var to = gameFilter.to;
	  if (from.year != to.year) {
		output = "from " + dayFilter(from) + " until "  + dayFilter(to);
	  }
	  else {
		if (from.month != to.month) {
		  output = "from " + dayFilter(from, false, true) + " until " + dayFilter(to);
		}
		else {
		  if (from.day != to.day) {
		    output = "from " + dayFilter(from, true, true) + " until " + dayFilter(to);
		  }
		  else output = "on " + dayFilter(from);
		}
	  }
	}
    return $sce.trustAsHtml(output);
  };
}]);