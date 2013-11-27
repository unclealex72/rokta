'use strict';

/* jasmine specs for controllers go here */
describe("The filter module's", function() {

  beforeEach(module('rokta.filter'));

  describe('Filter service', function(){
	var filter;
	beforeEach(inject(function ($injector) {
	  filter = $injector.get('Filter');
    }));
	
	it('should transform d[0-9]{4} into a year filter', function() {
	  expect(filter.of("d2013")).toEqual({type: "year", year: 2013});
	});

	it('should transform d[0-9]{6} into a month filter', function() {
	  expect(filter.of("d201309")).toEqual({type: "month", year: 2013, month: 9});
	});

	it('should transform d[0-9]{8} into a day filter', function() {
	  expect(filter.of("d20130905")).toEqual({type: "day", year: 2013, month: 9, day: 5});
	});

	it('should transform s[0-9]{8} into a since filter', function() {
	  expect(filter.of("s20130905")).toEqual({type: "since", year: 2013, month: 9, day: 5});
	});

	it('should transform u[0-9]{8} into an until filter', function() {
	  expect(filter.of("u20130905")).toEqual({type: "until", year: 2013, month: 9, day: 5});
	});

	it('should transform b[0-9]{16} into a between filter', function() {
	  expect(filter.of("b2013090520141011")).toEqual(
		{type: "between", from: {year: 2013, month: 9, day: 5}, to: {year: 2014, month: 10, day: 11}});
	});

	it('should serialise a year filter to d[0-9]{4}', function() {
	  expect(filter.date({year: 2013})).toEqual("d2013");
	});

	it('should serialise a month filter to d[0-9]{6}', function() {
	  expect(filter.date({year: 2013, month: 9})).toEqual("d201309");
	});

	it('should serialise a day filter to d[0-9]{8}', function() {
	  expect(filter.date({year: 2013, month: 9, day: 5})).toEqual("d20130905");
	});

	it('should serialise a since filter to d[0-9]{8}', function() {
	  expect(filter.since({year: 2013, month: 9, day: 5})).toEqual("s20130905");
	});

	it('should serialise an until filter to d[0-9]{8}', function() {
	  expect(filter.until({year: 2013, month: 9, day: 5})).toEqual("u20130905");
	});

	it('should serialise a between filter to d[0-9]{8}', function() {
	  expect(filter.between({year: 2012, month: 12, day:19}, {year: 2013, month: 9, day: 5})).toEqual(
		"b2012121920130905");
	});
  });

  describe('The roktaGame filter', function() {
	var scope, $compile;
	beforeEach(inject(function ($rootScope, _$compile_) {
	  scope = $rootScope;
	  $compile = _$compile_;
	}));
	var compile = function(gameFilter) {
	  scope.filter = gameFilter;
	  var element = $compile(angular.element('<span ng-bind-html="filter | roktaGame"></span>'))(scope);
	  scope.$digest();
	  return element;
	};
	
	it("outputs only the year for a year filter", function() {
	  var el = compile({type: "year", year: 2013});
	  expect(el.text()).toEqual("in 2013");
	});
	
	it("outputs only the month and year for a month filter", function() {
	  var el = compile({type: "month", year: 2013, month: 9});
	  expect(el.text()).toEqual("in September 2013");
	});

	it("outputs the day for a day filter", function() {
	  var el = compile({type: "day", year: 2013, month: 9, day: 3});
	  expect(el.html()).toEqual('on the 3<span class="day-suffix">rd</span> of September 2013');
	});

	it("outputs since for a since filter", function() {
	  var el = compile({type: "since", year: 2013, month: 9, day: 1});
	  expect(el.html()).toEqual('since the 1<span class="day-suffix">st</span> of September 2013');
	});

	it("outputs until for an until filter", function() {
	  var el = compile({type: "until", year: 2013, month: 3, day: 5});
	  expect(el.html()).toEqual('until the 5<span class="day-suffix">th</span> of March 2013');
	});

	it("outputs two whole dates for a between filter when the years are different", function() {
	  var el = compile({type: "between", from: {year: 2012, month: 3, day: 5}, to: {year: 2013, month: 3, day: 5 }});
	  expect(el.html()).toEqual(
		'from the 5<span class="day-suffix">th</span> of March 2012 until the 5<span class="day-suffix">th</span> of March 2013');
	  var el = compile({type: "between", from: {year: 2012, month: 9, day: 5}, to: {year: 2013, month: 3, day: 5 }});
	  expect(el.html()).toEqual(
		'from the 5<span class="day-suffix">th</span> of September 2012 until the 5<span class="day-suffix">th</span> of March 2013');
	  var el = compile({type: "between", from: {year: 2012, month: 9, day: 12}, to: {year: 2013, month: 3, day: 5 }});
	  expect(el.html()).toEqual(
		'from the 12<span class="day-suffix">th</span> of September 2012 until the 5<span class="day-suffix">th</span> of March 2013');
	});

	it("outputs two days, two months and one year for a between filter when the years are the same", function() {
	  var el = compile({type: "between", from: {year: 2013, month: 3, day: 5}, to: {year: 2013, month: 4, day: 5 }});
	  expect(el.html()).toEqual(
		'from the 5<span class="day-suffix">th</span> of March until the 5<span class="day-suffix">th</span> of April 2013');
	  var el = compile({type: "between", from: {year: 2013, month: 3, day: 5}, to: {year: 2013, month: 4, day: 21 }});
	  expect(el.html()).toEqual(
		'from the 5<span class="day-suffix">th</span> of March until the 21<span class="day-suffix">st</span> of April 2013');
	});

	it("outputs two days, one month and one year for a between filter when the months are the same", function() {
	  var el = compile({type: "between", from: {year: 2013, month: 3, day: 5}, to: {year: 2013, month: 3, day: 6 }});
	  expect(el.html()).toEqual(
		'from the 5<span class="day-suffix">th</span> until the 6<span class="day-suffix">th</span> of March 2013');
	});

	it("outputs one day, one month and one year for a between filter when the days are the same", function() {
	  var el = compile({type: "between", from: {year: 2013, month: 3, day: 5}, to: {year: 2013, month: 3, day: 5 }});
	  expect(el.html()).toEqual(
		'on the 5<span class="day-suffix">th</span> of March 2013');
	});
  });
});
