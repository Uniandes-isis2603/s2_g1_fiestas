(function (ng) {

    var mod = ng.module("valoracionModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/valoraciones/';

            $urlRouterProvider.otherwise("/valoracionesList");

            $stateProvider.state('valoraciones', {
                url: '/valoraciones',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'valoraciones.html',
                        controller: 'valoracionesCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('valoracionesList', {
                url: '/list',
                parent: 'valoraciones',
                views: {
                    'listView': {
                        templateUrl: basePath + 'valoraciones.list.html'
                    }
                }
            }).state('valoracionCreate', {
                url: '/create',
                parent: 'valoraciones',
                views: {
                    'listView': {
                        templateUrl: basePath + '/new/valoraciones.new.html',
                        controller: 'valoracionNewCtrl'
                    }
                }
            });
        }
    ]);
})(window.angular);
