'use strict';

/* jasmine specs for controllers go here */
describe("The panel module's", function() {

  beforeEach(module('rokta.panel'));

  describe('panel directive', function() {
    var builder;
    beforeEach(inject(function ($compile, $rootScope) {
      builder = function(html) {
        var element = angular.element(html);
        $compile(element)($rootScope);
        $rootScope.$digest();
        return element;
      };
    }));
    
    describe('with no extra arguments', function() {
      var el;
      beforeEach(function() {
        el = builder("<div rokta-panel>Hello</div>");
      });

      it('should have the panel class.', function() {
        expect(el.hasClass("panel")).toEqual(true);
      });

      it('should have the panel-default class.', function() {
        expect(el.hasClass("panel-default")).toEqual(true);
      });
      
      it('should only have one child', function() {
        expect(el.children().length).toEqual(1);
      });
      
      it('should include the supplied text', function() {
        expect(el.text()).toEqual("Hello");
      });
    });
    
    describe('with a type argument', function() {
      var el;
      beforeEach(function() {
        el = builder("<div rokta-panel type='info'>Hello</div>");
      });

      it('should have the panel class.', function() {
        expect(el.hasClass("panel")).toEqual(true);
      });

      it('should have the panel-info class.', function() {
        expect(el.hasClass("panel-info")).toEqual(true);
      });      
    });

    describe('with a header argument', function() {
      var el;
      beforeEach(function() {
        el = builder("<div rokta-panel header='Title'>Hello</div>");
      });

      it('should have two children.', function() {
        expect(el.children().length).toEqual(2);
      });

      it('should have the text of the header and body.', function() {
        expect(el.text()).toEqual("TitleHello");
      });
    });

    describe('with a footer argument', function() {
      var el;
      beforeEach(function() {
        el = builder("<div rokta-panel footer='Bye'>Hello</div>");
      });

      it('should have two children.', function() {
        expect(el.children().length).toEqual(2);
      });

      it('should have the text of the body and footer.', function() {
        expect(el.text()).toEqual("HelloBye");
      });
    });

    describe('with header and footer arguments', function() {
      var el;
      beforeEach(function() {
        el = builder("<div rokta-panel header='Title' footer='Bye'>Hello</div>");
      });

      it('should have three children.', function() {
        expect(el.children().length).toEqual(3);
      });

      it('should have the text of the header, body and footer.', function() {
        expect(el.text()).toEqual("TitleHelloBye");
      });
    });
  });
});
