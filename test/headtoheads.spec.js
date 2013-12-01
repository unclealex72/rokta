'use strict';

/* jasmine specs for controllers go here */
describe("The headtohead module's", function() {

  beforeEach(module('rokta.headtoheads'));

  describe('HeadToHead service', function() {

    var headToHeads;
    beforeEach(inject(function($injector) {
      headToHeads = $injector.get('HeadToHeads');
    }));

    it('should be able to track a single result', function() {
      var players = angular.fromJson('{"players":[{"name":"Dinsey","email":null,"colour":{"persistableToken":"ALICE_BLUE","htmlName":"AliceBlue","rgb":"#F0F8FF","name":["alice","blue"],"index":118},"persisted":true},{"name":"Richard","email":"r14hyp@googlemail.com","colour":{"persistableToken":"DARK_VIOLET","htmlName":"DarkViolet","rgb":"#9400D3","name":["dark","violet"],"index":41},"persisted":true},{"name":"Alex","email":"unclealex72@gmail.com","colour":{"persistableToken":"SPRING_GREEN","htmlName":"SpringGreen","rgb":"#00FF7F","name":["spring","green"],"index":57},"persisted":true},{"name":"Jo","email":null,"colour":{"persistableToken":"ALICE_BLUE","htmlName":"AliceBlue","rgb":"#F0F8FF","name":["alice","blue"],"index":118},"persisted":true},{"name":"Martin","email":null,"colour":{"persistableToken":"BLACK","htmlName":"Black","rgb":"#000000","name":["black"],"index":139},"persisted":true},{"name":"Andy G","email":"andy409green@gmail.com","colour":{"persistableToken":"ALICE_BLUE","htmlName":"AliceBlue","rgb":"#F0F8FF","name":["alice","blue"],"index":118},"persisted":true},{"name":"Andy H","email":"andrew.c.haywood@googlemail.com","colour":{"persistableToken":"YELLOW","htmlName":"Yellow","rgb":"#FFFF00","name":["yellow"],"index":21},"persisted":true},{"name":"Mike","email":"mrossuk@gmail.com","colour":{"persistableToken":"LIGHT_SKY_BLUE","htmlName":"LightSkyBlue","rgb":"#87CEFA","name":["light","sky","blue"],"index":86},"persisted":true},{"name":"Marc","email":null,"colour":{"persistableToken":"BLACK","htmlName":"Black","rgb":"#000000","name":["black"],"index":139},"persisted":true},{"name":"Ann-Marie","email":null,"colour":{"persistableToken":"HOT_PINK","htmlName":"HotPink","rgb":"#FF69B4","name":["hot","pink"],"index":10},"persisted":true},{"name":"Tony","email":"morlandez@gmail.com","colour":{"persistableToken":"RED","htmlName":"Red","rgb":"#FF0000","name":["red"],"index":4},"persisted":true},{"name":"Mark","email":null,"colour":{"persistableToken":"LINEN","htmlName":"Linen","rgb":"#FAF0E6","name":["linen"],"index":127},"persisted":true},{"name":"Darren","email":null,"colour":{"persistableToken":"ALICE_BLUE","htmlName":"AliceBlue","rgb":"#F0F8FF","name":["alice","blue"],"index":118},"persisted":true}]}');
      var data = angular.fromJson('{ "Richard": { "Alex": 5, "Mike": 12, "Ann-Marie": 3, "Tony": 10, "Andy H": 7 }, "Alex": { "Richard": 9, "Mike": 14, "Ann-Marie": 2, "Tony": 7, "Andy H": 21, "Andy G": 2 }, "Mike": { "Richard": 18, "Alex": 14, "Ann-Marie": 3, "Tony": 16, "Andy H": 26, "Andy G": 1 }, "Ann-Marie": { "Mike": 3, "Andy H": 2, "Tony": 1, "Alex": 2 }, "Tony": { "Richard": 15, "Alex": 14, "Mike": 22, "Ann-Marie": 1, "Andy H": 22, "Andy G": 1 }, "Andy H": { "Richard": 9, "Alex": 22, "Mike": 21, "Ann-Marie": 2, "Tony": 10, "Andy G": 5 }, "Andy G": { "Tony": 4, "Alex": 1 } }');
      headToHeads.create(players.players, data);
    });
  });
});
