module.exports = function(config){
  config.set({
    basePath : '../',

    files : [
      'http://cdnjs.cloudflare.com/ajax/libs/jquery/1.10.2/jquery.js',
      'http://ajax.googleapis.com/ajax/libs/angularjs/1.2.9/angular.js',
      'http://ajax.googleapis.com/ajax/libs/angularjs/1.2.9/angular-sanitize.js',
      'http://ajax.googleapis.com/ajax/libs/angularjs/1.2.9/angular-route.js',
      'http://ajax.googleapis.com/ajax/libs/angularjs/1.2.9/angular-animate.js',
      'http:////cdnjs.cloudflare.com/ajax/libs/angular-ui-bootstrap/0.10.0/ui-bootstrap-tpls.js',
      'http://code.angularjs.org/1.2.9/angular-mocks.js',
      'http://cdn.jsdelivr.net/restangular/1.2.0/restangular.min.js',
      'http:////cdnjs.cloudflare.com/ajax/libs/lodash.js/2.4.1/lodash.js',
      'app/assets/javascripts/**/*.js',
      'public/javascripts/**/*.js',
      'test/**/*.js'
    ],

    exclude : [],

    autoWatch : true,

    frameworks: ['jasmine'],

    browsers : ['Chrome'],

    plugins : [
            'karma-junit-reporter',
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-jasmine'
            ],

    junitReporter : {
      outputFile: 'test_out/unit.xml',
      suite: 'unit'
    }
  });
};
