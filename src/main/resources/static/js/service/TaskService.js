/**
 * Task Service
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define service
    var TaskService = function ($resource) {
        return $resource('/tasks/:id', null,
            {
                'update': {method: 'PUT'}
            });
    };

    // register service
    app.factory('TaskService', ['$resource', TaskService])
}());