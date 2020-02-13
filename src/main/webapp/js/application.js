var mancalaApp = angular.module('mancalaApp', ['ngRoute', 'gameModule']);

mancalaApp.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
    when('/rules', {
        templateUrl: 'templates/rules.html'
    }).
    when('/player', {
        templateUrl: 'templates/home.html',
        controller: 'homeController'
    }).
    when('/game/:id', {
        templateUrl: 'templates/game.html',
        controller: 'gameController'
    }).
    otherwise({
        redirectTo: '/player'
    });
}]);