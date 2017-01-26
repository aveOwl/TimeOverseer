/**
 * Developer Service
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define service
    var DeveloperService = function ($resource) {
        return $resource('/developers/:id', null,
            {
                'update': {method: 'PUT'}
            });
    };

    // register service
    app.factory('DeveloperService', ['$resource', DeveloperService])
}());