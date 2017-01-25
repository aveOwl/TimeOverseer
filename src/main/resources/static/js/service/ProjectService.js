/**
 * Project Service
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define service
    var ProjectService = function ($resource) {
        return $resource('/projects/:id', null,
            {
                'update': {method: 'PUT'}
            });
    };

    // register service
    app.factory('ProjectService', ['$resource', ProjectService])
}());