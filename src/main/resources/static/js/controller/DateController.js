/**
 * Date controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var DateController = function ($rootScope, $log) {

        $rootScope.checkDates = function (startDate, endDate) {
            $log.debug("Verifying dates", [startDate, endDate]);

            $rootScope.dateError = new Date(startDate) > new Date(endDate);
        }
    };

    // register controller
    app.controller('DateController', ['$rootScope', '$log', DateController])
}());