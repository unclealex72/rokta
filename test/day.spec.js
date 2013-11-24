'use strict';

/* jasmine specs for controllers go here */
describe("The day module's", function() {

  beforeEach(module('rokta.day'));

  describe('DateSerialiser service', function(){
	var dateSerialiser;
	beforeEach(inject(function ($injector) {
	  dateSerialiser = $injector.get('DateSerialiser');
	}));
  
	it('should deserialise a 4 digit number into a year', function() {
      var date = dateSerialiser.deserialise("1983");
      expect(date.year).toEqual(1983);
      expect(date.month).toBeUndefined();
      expect(date.day).toBeUndefined();
    });

    it('should deserialise a 6 digit number into a year and a month', function() {
      var date = dateSerialiser.deserialise("198305");
      expect(date.year).toEqual(1983);
      expect(date.month).toEqual(5);
      expect(date.day).toBeUndefined();
    });

    it('should deserialise an 8 digit number into a year, month and day', function() {
      var date = dateSerialiser.deserialise("19830512");
      expect(date.year).toEqual(1983);
      expect(date.month).toEqual(5);
      expect(date.day).toEqual(12);
    });

    it('should deserialise any other length number into null', function() {
      var date = dateSerialiser.deserialise("1983512");
      expect(date).toBeNull();
    });

    it('should deserialise any other length number into null', function() {
      var date = dateSerialiser.deserialise("1983a512");
      expect(date).toBeNull();
    });
    
    it('should serialise a year into a 4 digit number', function() {
      var str = dateSerialiser.serialise({year: 1983});
      expect(str).toEqual("1983");
    });

    it('should serialise a year and month into a 6 digit number', function() {
      var str = dateSerialiser.serialise({year: 1983, month: 9});
      expect(str).toEqual("198309");
    });

    it('should serialise a year, month and day into an 8 digit number', function() {
      var str = dateSerialiser.serialise({year: 1983, month: 9, day: 12});
      expect(str).toEqual("19830912");
    });
  });

  describe('The DateSuffix service', function() {
	var dateSuffix;
	beforeEach(inject(function ($injector) {
	  dateSuffix = $injector.get('DateSuffix');
	}));
	for (var number = 0; number < 30; number++) {
	  var expectedSuffix;
	  if (number == 1 || number == 21) {
		expectedSuffix = "st";
	  }
	  if (number == 2 || number == 22) {
		expectedSuffix = "nd";
	  }
	  if (number == 3 || number == 23) {
		expectedSuffix = "rd";
	  }
	  else {
		expectedSuffix = "th";
	  }
	  it("should return '" + expectedSuffix + "' for " + number, function() {
	    expect(dateSuffix.suffixFor(number)).toEqual(expectedSuffix);
	  });
	}
  });
  
  describe('The roktaYear filter', function() {
	var scope, $compile;
	beforeEach(inject(function ($rootScope, _$compile_) {
	  scope = $rootScope;
	  $compile = _$compile_;
	}));
	var compile = function(day) {
	  scope.day = day;
	  var element = $compile(angular.element('<span ng-bind-html="day | roktaYear"></span>'))(scope);
	  scope.$digest();
	  return element;
	};
	it("outputs only the year", function() {
	  var el = compile({year: 2013});
	  expect(el.text()).toEqual("2013");
	});
  });

  describe('The roktaMonth filter', function() {
	var scope, $compile;
	beforeEach(inject(function ($rootScope, _$compile_) {
	  scope = $rootScope;
	  $compile = _$compile_;
	}));
	var compile = function(day) {
	  scope.day = day;
	  var element = $compile(angular.element('<span ng-bind-html="day | roktaMonth"></span>'))(scope);
	  scope.$digest();
	  return element;
	};
	it("outputs only the year and month", function() {
	  var el = compile({year: 2013, month: 9});
	  expect(el.text()).toEqual("September 2013");
	});
  });

  describe('The roktaDay filter', function() {
	var scope, $compile;
	beforeEach(inject(function ($rootScope, _$compile_) {
	  scope = $rootScope;
	  $compile = _$compile_;
	}));
	var compile = function(day) {
	  scope.day = day;
	  var element = $compile(angular.element('<span ng-bind-html="day | roktaDay"></span>'))(scope);
	  scope.$digest();
	  return element;
	};
	
	it("outputs the year, month and day for a day object", function() {
	  var el = compile({ year: 2013, month: 9, day: 3 });
	  expect(el.html().toString()).toEqual('the 3<span class="day-suffix">rd</span> of September 2013');
	});
	
	it("outputs the year, month and day for a Date object", function() {
	  var el = compile(new Date(2013, 8, 3));
	  expect(el.html().toString()).toEqual('the 3<span class="day-suffix">rd</span> of September 2013');
	});

	it("outputs the year, month and day for a string", function() {
	  var el = compile("2013-09-03T12:00:00Z");
	  expect(el.html().toString()).toEqual('the 3<span class="day-suffix">rd</span> of September 2013');
	});
  });
});
