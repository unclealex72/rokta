'use strict';

/* jasmine specs for controllers go here */
describe("The filters module's", function() {

  beforeEach(module('rokta.filters'));

  describe('CurrentFilter service', function() {
    describe('with a filter provided', function() {
      var routeParams = { filter: 'd20130905' };
      var CurrentFilter;
      beforeEach(function() {
        module(function($provide) {
          $provide.value('$routeParams', routeParams);
        });
        inject(function($injector) {
          CurrentFilter = $injector.get('CurrentFilter');
        });
      });
  
      it('Use $routeParams to get the current router.', function() {
        expect(CurrentFilter.asString()).toEqual('d20130905');
      });
    });

    describe('without a filter provided', function() {
      var routeParams = {};
      var CurrentFilter;
      beforeEach(function() {
        module(function($provide) {
          $provide.value('$routeParams', routeParams);
          $provide.value('Now', new Date(Date.UTC(2014, 8, 5)));
        });
        inject(function($injector) {
          CurrentFilter = $injector.get('CurrentFilter');
        });
      });
  
      it('should use the current year to get the current filter.', function() {
        expect(CurrentFilter.asString()).toEqual('d2014');
      });
    });
  });
     
  describe('Default Filter factory', function() {
    var DefaultFilters;
    var today = new Date(Date.UTC(2014, 8, 5));
    beforeEach(function() {
      module(function($provide) {
        $provide.value('Now', today);
      });
      inject(function($injector) {
        DefaultFilters = $injector.get('DefaultFilters');
      });
    });
    
    it('should return the correct year filter', function() {
      expect(DefaultFilters.year).toEqual({type: 'year', year: 2014});
    });
    
    it('should return the correct month filter', function() {
      expect(DefaultFilters.month).toEqual({type: 'month', year: 2014, month: 9});
    });
    
    it('should return the correct day filter', function() {
      expect(DefaultFilters.day).toEqual({type: 'day', year: 2014, month: 9, day: 5});
    });
    
    it('should return the correct since filter', function() {
      expect(DefaultFilters.since).toEqual({type: 'since', year: 2014, month: 9, day: 5});
    });
    
    it('should return the correct until filter', function() {
      expect(DefaultFilters.until).toEqual({type: 'until', year: 2014, month: 9, day: 5});
    });
    
    it('should return the correct between filter', function() {
      expect(DefaultFilters.between).toEqual({type: 'between', from: {year: 2014, month: 9, day: 5}, to: {year: 2014, month: 9, day: 5}});
    });
    
    
  });
  
  describe('Filter service', function() {
    var filter;
    beforeEach(inject(function($injector) {
      filter = $injector.get('Filter');
    }));

    it('should transform d[0-9]{4} into a year filter', function() {
      expect(filter.of("d2013")).toEqual({
        type : "year",
        year : 2013
      });
    });

    it('should transform d[0-9]{6} into a month filter', function() {
      expect(filter.of("d201309")).toEqual({
        type : "month",
        year : 2013,
        month : 9
      });
    });

    it('should transform d[0-9]{8} into a day filter', function() {
      expect(filter.of("d20130905")).toEqual({
        type : "day",
        year : 2013,
        month : 9,
        day : 5
      });
    });

    it('should transform s[0-9]{8} into a since filter', function() {
      expect(filter.of("s20130905")).toEqual({
        type : "since",
        year : 2013,
        month : 9,
        day : 5
      });
    });

    it('should transform u[0-9]{8} into an until filter', function() {
      expect(filter.of("u20130905")).toEqual({
        type : "until",
        year : 2013,
        month : 9,
        day : 5
      });
    });

    it('should transform b[0-9]{16} into a between filter', function() {
      expect(filter.of("b2013090520141011")).toEqual({
        type : "between",
        from : {
          year : 2013,
          month : 9,
          day : 5
        },
        to : {
          year : 2014,
          month : 10,
          day : 11
        }
      });
    });

    it('should serialise a year filter to d[0-9]{4}', function() {
      expect(filter.serialise({
        type: 'year',
        year : 2013
      })).toEqual("d2013");
    });

    it('should serialise a month filter to d[0-9]{6}', function() {
      expect(filter.serialise({
        type: 'month',
        year : 2013,
        month : 9
      })).toEqual("d201309");
    });

    it('should serialise a day filter to d[0-9]{8}', function() {
      expect(filter.serialise({
        type: 'day',
        year : 2013,
        month : 9,
        day : 5
      })).toEqual("d20130905");
    });

    it('should serialise a since filter to s[0-9]{8}', function() {
      expect(filter.serialise({
        type : 'since',
        year : 2013,
        month : 9,
        day : 5
      })).toEqual("s20130905");
    });

    it('should serialise an until filter to u[0-9]{8}', function() {
      expect(filter.serialise({
        type: 'until',
        year : 2013,
        month : 9,
        day : 5
      })).toEqual("u20130905");
    });

    it('should serialise a between filter to b[0-9]{16}', function() {
      expect(filter.serialise({
        type: 'between',
        from: {
          year : 2012,
          month : 12,
          day : 19
        },
        to: {
          year : 2013,
          month : 9,
          day : 5
        }})).toEqual("b2012121920130905");
    });
  });

  describe('Limits service', function() {
    var limits;
    beforeEach(inject(function($injector) {
      limits = $injector.get('Limits');
    }));

    it('should return all years between the first and last dates inclusive.', function() {
      limits.limits = {
        first : new Date("1972-05-09T09:12:00Z"),
        last : new Date("1975-03-01T09:12:00Z")
      };
      expect(limits.years()).toEqual([1972, 1973, 1974, 1975]);
    });

    it('should return all months for years strictly between the first and last year.', function() {
      limits.limits = {
        first : new Date("1972-05-09T09:12:00Z"),
        last : new Date("1975-03-01T09:12:00Z")
      };
      expect(limits.months(1973)).toEqual([
        {index: 1, month: 'January'},
        {index: 2, month: 'February'},
        {index: 3, month: 'March'},
        {index: 4, month: 'April'},
        {index: 5, month: 'May'},
        {index: 6, month: 'June'},
        {index: 7, month: 'July'},
        {index: 8, month: 'August'},
        {index: 9, month: 'September'},
        {index: 10, month: 'October'},
        {index: 11, month: 'November'},
        {index: 12, month: 'December'}]);
    });
    
    it('should return only months equal to or after the cutoff date for the first year.', function() {
      limits.limits = {
        first : new Date("1972-09-05T09:12:00Z"),
        last : new Date("1975-03-01T09:12:00Z")
      };
      expect(limits.months(1972)).toEqual([
        {index: 9, month: 'September'},
        {index: 10, month: 'October'},
        {index: 11, month: 'November'},
        {index: 12, month: 'December'}
      ]);
    });

    it('should return only months equal to or before the cutoff date for the last year.', function() {
      limits.limits = {
        first : new Date("1972-09-05T09:12:00Z"),
        last : new Date("1975-03-01T09:12:00Z")
      };
      expect(limits.months(1975)).toEqual([
        {index: 1, month: 'January'},
        {index: 2, month: 'February'},
        {index: 3, month: 'March'}
      ]);
    });

    it('should return only months equal to or between the cutoff dates when the first and last years are the same.', function() {
      limits.limits = {
        first : new Date("1972-03-01T09:12:00Z"),
        last : new Date("1972-09-05T09:12:00Z")
      };
      expect(limits.months(1972)).toEqual([
        {index: 3, month: 'March'},
        {index: 4, month: 'April'},
        {index: 5, month: 'May'},
        {index: 6, month: 'June'},
        {index: 7, month: 'July'},
        {index: 8, month: 'August'},
        {index: 9, month: 'September'}
      ]);
    });
  });
  
  describe('roktaGame filter', function() {
    var scope, $compile;
    beforeEach(inject(function($rootScope, _$compile_) {
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
      var el = compile({
        type : "year",
        year : 2013
      });
      expect(el.text()).toEqual("in 2013");
    });

    it("outputs only the month and year for a month filter", function() {
      var el = compile({
        type : "month",
        year : 2013,
        month : 9
      });
      expect(el.text()).toEqual("in September 2013");
    });

    it("outputs the day for a day filter", function() {
      var el = compile({
        type : "day",
        year : 2013,
        month : 9,
        day : 3
      });
      expect(el.html()).toEqual('on the 3<span class="day-suffix">rd</span> of September 2013');
    });

    it("outputs since for a since filter", function() {
      var el = compile({
        type : "since",
        year : 2013,
        month : 9,
        day : 1
      });
      expect(el.html()).toEqual('since the 1<span class="day-suffix">st</span> of September 2013');
    });

    it("outputs until for an until filter", function() {
      var el = compile({
        type : "until",
        year : 2013,
        month : 3,
        day : 5
      });
      expect(el.html()).toEqual('until the 5<span class="day-suffix">th</span> of March 2013');
    });

    it("outputs two whole dates for a between filter when the years are different", function() {
      var el = compile({
        type : "between",
        from : {
          year : 2012,
          month : 3,
          day : 5
        },
        to : {
          year : 2013,
          month : 3,
          day : 5
        }
      });
      expect(el.html()).toEqual('from the 5<span class="day-suffix">th</span> of March 2012 until the 5<span class="day-suffix">th</span> of March 2013');
      var el = compile({
        type : "between",
        from : {
          year : 2012,
          month : 9,
          day : 5
        },
        to : {
          year : 2013,
          month : 3,
          day : 5
        }
      });
      expect(el.html()).toEqual('from the 5<span class="day-suffix">th</span> of September 2012 until the 5<span class="day-suffix">th</span> of March 2013');
      var el = compile({
        type : "between",
        from : {
          year : 2012,
          month : 9,
          day : 12
        },
        to : {
          year : 2013,
          month : 3,
          day : 5
        }
      });
      expect(el.html()).toEqual('from the 12<span class="day-suffix">th</span> of September 2012 until the 5<span class="day-suffix">th</span> of March 2013');
    });

    it("outputs two days, two months and one year for a between filter when the years are the same", function() {
      var el = compile({
        type : "between",
        from : {
          year : 2013,
          month : 3,
          day : 5
        },
        to : {
          year : 2013,
          month : 4,
          day : 5
        }
      });
      expect(el.html()).toEqual('from the 5<span class="day-suffix">th</span> of March until the 5<span class="day-suffix">th</span> of April 2013');
      var el = compile({
        type : "between",
        from : {
          year : 2013,
          month : 3,
          day : 5
        },
        to : {
          year : 2013,
          month : 4,
          day : 21
        }
      });
      expect(el.html()).toEqual('from the 5<span class="day-suffix">th</span> of March until the 21<span class="day-suffix">st</span> of April 2013');
    });

    it("outputs two days, one month and one year for a between filter when the months are the same", function() {
      var el = compile({
        type : "between",
        from : {
          year : 2013,
          month : 3,
          day : 5
        },
        to : {
          year : 2013,
          month : 3,
          day : 6
        }
      });
      expect(el.html()).toEqual('from the 5<span class="day-suffix">th</span> until the 6<span class="day-suffix">th</span> of March 2013');
    });

    it("outputs one day, one month and one year for a between filter when the days are the same", function() {
      var el = compile({
        type : "between",
        from : {
          year : 2013,
          month : 3,
          day : 5
        },
        to : {
          year : 2013,
          month : 3,
          day : 5
        }
      });
      expect(el.html()).toEqual('on the 5<span class="day-suffix">th</span> of March 2013');
    });
  });
});
