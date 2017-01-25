/**
 * Customer Service
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define service
    var CustomerService = function ($resource) {
        return $resource('/customers/:id', null,
            {
                'update': {method: 'PUT'}
            });
    };

    // register service
    app.factory('CustomerService', ['$resource', CustomerService])
}());