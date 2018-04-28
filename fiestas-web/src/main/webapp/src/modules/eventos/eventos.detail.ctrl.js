(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventoContext", "api/eventos");
    mod.controller('eventoDetailCtrl', ['$scope', '$http', 'eventoContext', '$state',
        function ($scope, $http, eventoContext, $state) {
            /**
             * @ngdoc controller
             * @name eventos.controller:eventoDetailCtrl
             * @description
             * Definición de un controlador auxiliar del módulo Eventos. 
             * Se crea el controlador con el cual se manejan las vistas de detalle
             * del módulo.
             * @param {Object} $scope Referencia injectada al Scope definida para este
             * controlador, el scope es el objeto que contiene las variables o 
             * funciones que se definen en este controlador y que son utilizadas 
             * desde el HTML.
             * @param {Object} $http Objeto injectado para la manejar consultas HTTP
             * @param {Object} booksContext Constante injectada que contiene la ruta
             * donde se encuentra el API de Eventos en el Backend.
             * @param {Object} $state Dependencia injectada en la que se recibe el 
             * estado actual de la navegación definida en el módulo.
             */
            if (($state.params.eventoId !== undefined) && ($state.params.eventoId !== null)) {
                 /**
                 * @ngdoc function
                 * @name getEventoID
                 * @methodOf eventos.controller:eventoDetailCtrl
                 * @description
                 * Esta función utiliza el protocolo HTTP para obtener el recurso 
                 * donde se encuentra el autor por ID en formato JSON.
                 * @param {String} URL Dirección donde se encuentra el recurso
                 * del evento o API donde se puede consultar.
                 */
                $http.get(eventoContext+'/' + $state.params.eventoId).then(function (response) {
                    $scope.currentEvento = response.data;
                    $scope.pagosRecords=response.data.pagos;
                    $scope.contratosRecords=response.data.contratos;
                    
                });
            }
        }
    ]);
}
)(window.angular);