/**
 * Company Service
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define service
    var CompanyService = function ($resource) {
        return $resource('/companies/:id', null,
            {
                'update': {method: 'PUT'}
            });
    };

    // register service
    app.factory('CompanyService', ['$resource', CompanyService])
}());