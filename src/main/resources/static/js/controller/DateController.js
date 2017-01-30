/**
 * Date Controller.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .controller('DateController', DateController);

    DateController.$inject = ['$rootScope', '$log'];
    function DateController($rootScope, $log) {
        $rootScope.checkDates = function (startDate, endDate) {
            $log.debug("Verifying dates", [startDate, endDate]);

            $rootScope.dateError = new Date(startDate) > new Date(endDate);
        }
    }
}());