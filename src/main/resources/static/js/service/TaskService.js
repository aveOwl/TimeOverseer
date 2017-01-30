/**
 * Task Service.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .factory('TaskService', TaskService);

    TaskService.$inject = ['$resource'];
    function TaskService($resource) {
        var taskService = {};

        // Basic CRUD operations
        taskService.perform = function () {
            return $resource('/tasks/:id', null,
                {
                    'update': {method: 'PUT'}
                });
        };

        taskService.getSprint = function (task) {
            return $resource(task._links.sprint.href).get();
        };

        taskService.getDevelopers = function (task) {
            return $resource(task._links.developers.href).get();
        };

        return taskService;
    }
}());