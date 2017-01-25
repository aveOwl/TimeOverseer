/**
 * Sprint Service
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define service
    var SprintService = function ($resource) {
        return $resource('/sprints/:id', null,
            {
                'update': {method: 'PUT'}
            });
    };

    // register service
    app.factory('SprintService', ['$resource', SprintService])
}());