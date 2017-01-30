/**
 * Sprint Service
 */
(function () {
    'use strict';

    angular.module('overseer')
        .factory('SprintService', SprintService);

    SprintService.$inject = ['$resource'];
    function SprintService($resource) {
        var sprintService = {};

        // Basic CRUD operations
        sprintService.perform = function () {
            return $resource('/sprints/:id', null,
                {
                    'update': {method: 'PUT'}
                });
        };

        sprintService.getProject = function (sprint) {
            return $resource(sprint._links.project.href).get();
        };

        sprintService.getTasks = function (sprint) {
            return $resource(sprint._links.tasks.href).get();
        };

        return sprintService;
    }
}());