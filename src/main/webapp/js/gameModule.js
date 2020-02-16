var gameModule = angular.module('gameModule', []);

gameModule.controller('homeController', ['$rootScope', '$scope', '$http', '$location',

    function (rootScope, scope, http, location) {

        rootScope.stompClient = null;

        scope.createNewGame = function () {

            http.post("/game/create", {
                headers: {
                    'Content-Type': 'application/json; charset=UTF-8'
                }
            }).success(function (data, status, headers, config) {
                rootScope.gameId = data.id;
                location.path('/game/' + rootScope.gameId);
            }).error(function (data, status, headers, config) {
                location.path('/player/panel');
            });
        };

        rootScope.connectHome = function connect() {
            var socket = new SockJS('/socket');
            scope.stompClient = Stomp.over(socket);
            scope.stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                scope.stompClient.subscribe('/update/home', rootScope.reloadPlayerGames);
                scope.stompClient.subscribe('/update/home', rootScope.reloadGamesToJoin);
            });
        };

        scope.connectHome();

    }
]);

gameModule.controller('gamesToJoinController', ['$rootScope', '$scope', '$http', '$location',
    function (rootScope, scope, http, location) {
        rootScope.reloadGamesToJoin = function () {
            scope.gamesToJoin = [];
            http.get('/game/list').success(function (data) {
                scope.gamesToJoin = data;
            }).error(function (data, status, headers, config) {
                location.path('/player/panel');
            });

            scope.joinGame = function (id) {

                var requestUrl = "/game/join/" + id;
                http.post(requestUrl, {
                    headers: {
                        'Content-Type': 'application/json; charset=UTF-8'
                    }
                }).success(function (data) {
                    rootScope.gameId = data.id;
                    location.path('/game/' + data.id);
                }).error(function (data, status, headers, config) {
                    location.path('/player/panel');
                });
            }
        };

        rootScope.reloadGamesToJoin();
    }]);

gameModule.controller('playerGamesController', ['$rootScope', '$scope', '$http', '$location', '$routeParams',
    function (rootScope, scope, http, location, routeParams) {
        rootScope.reloadPlayerGames  =function () {

            scope.playerGames = [];

            http.get('/game/player/list').success(function (data) {
                scope.playerGames = data;
            }).error(function (data, status, headers, config) {
                location.path('/player/panel');
            });

            scope.loadGame = function (id) {
                console.log(id);
                rootScope.gameId = id;
                http.get('/game/' + id).success(function (data) {
                    location.path('/game/' + id);
                }).error(function (data, status, headers, config) {
                    location.path('/player/panel');
                });
            }
        };

        rootScope.reloadPlayerGames();

    }]);

gameModule.controller('gameController', ['$rootScope', '$routeParams', '$scope', '$http',
    function (rootScope, routeParams, scope, http) {
        rootScope.stompClient = null;

        rootScope.reload = function getData() {
            console.log("RELOADING DATA");
            http.get('/play/board').success(function (data) {
                scope.data = data;
                scope.gameBoard = [];
                data.pits.forEach(function (pit) {
                    scope.gameBoard[pit.position] = pit.stoneCount;
                })
            }).error(function (data, status, headers, config) {
                scope.errorMessage = "Failed do load board properties";
            });
            http.get('/play/turn').success(function (data) {
                scope.gameTurn = data;
            }).error(function (data, status, headers, config) {
                scope.errorMessage = "Failed do load player turn properties";
            });
            http.get('/play/state').success(function (data) {
                scope.gameState = data;
                if(scope.gameState=='GAME_OVER'){
                	http.get('/play/winner').success(function (data) {
                        scope.gameWinner = data;
                    }).error(function (data, status, headers, config) {
                        scope.errorMessage = "Failed do load winning player properties";
                    });
        			document.getElementById("homeButton").style.visibility = "visible";
        			document.getElementById("homeButton").style.display = "block";
                }
            }).error(function (data, status, headers, config) {
                scope.errorMessage = "Failed do load player state properties";
            });
        };

        rootScope.reload();

        rootScope.connectBoard = function connect() {
            var socket = new SockJS('/socket');
            scope.stompClient = Stomp.over(socket);
            scope.stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                scope.stompClient.subscribe('/update/position/' + rootScope.gameId, scope.reload);
                scope.stompClient.subscribe('/update/join/' + rootScope.gameId, scope.reload);
            }, function (error) {
                alert(error.headers.message);
            });
        };

        scope.connectBoard();

        scope.move = function (id) {
            http.post('/play/move/' + id).success(function (data) {
                scope.data = data
                scope.reload();
            }).error(function (data, status, headers, config) {
                scope.errorMessage = "Failed do do move";
            });
        };

    }
]);