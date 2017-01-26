/**
 * ProjectManager Service
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define service
    var ProjectManagerService = function ($resource) {
        return $resource('/projectManagers/:id', null,
            {
                'update': {method: 'PUT'}
            });
    };

    // register service
    app.factory('ProjectManagerService', ['$resource', ProjectManagerService])
}());