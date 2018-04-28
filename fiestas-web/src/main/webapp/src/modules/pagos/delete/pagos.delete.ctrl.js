(function (ng) {
    var mod = ng.module("pagoModule");
    mod.constant("pagoContext", "pagos");
    mod.constant("eventosContext", "api/eventos");
    mod.controller('pagoDeleteCtrl', ['$scope', '$http', 'eventosContext', '$state', 'pagoContext',
        /**
         * @ngdoc controller
         * @name eventos.controller:eventoDeleteCtrl
         * @description
         * Definición del controlador auxiliar para eliminar Eventos. 
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} eventosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Eventos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} pagoContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Pagos en el Backend.
         */
        function ($scope, $http, eventosContext, $state, pagoContext) {
            var idEvento = $state.params.eventoId;
            var idPago = $state.params.pagoId;
            /**
             * @ngdoc function
             * @name deletePago
             * @methodOf pagos.controller:eventoDeleteCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para eliminar un pago.
             * @param {String} id El ID del pago a eliminar.
             */
            $scope.deletePago = function () {
                $http.delete(eventosContext + '/' + idEvento + '/' + pagoContext + '/' + idPago, {}).then(function (response) {
                    $state.go('pagosList', {pagoId: response.data.id}, {reload: true});
                });
            };
        }
    ]);
}
)(window.angular);