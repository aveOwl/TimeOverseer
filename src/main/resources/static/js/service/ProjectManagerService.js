/**
 * ProjectManager Service
 */
(function () {
    'use strict';

    angular.module('overseer')
        .factory('ProjectManagerService', ProjectManagerService);

    ProjectManagerService.$inject = ['$resource'];
    function ProjectManagerService($resource) {
        var projectManagerService = {};

        // Basic CRUD operations
        projectManagerService.perform = function () {
            return $resource('/projectManagers/:id', null,
                {
                    'update': {method: 'PUT'}
                });
        };

        projectManagerService.getCompany = function (projectManager) {
            return $resource(projectManager._links.employer.href).get();
        };

        projectManagerService.getProject = function (projectManager) {
            return $resource(projectManager._links.project.href).get();
        };

        projectManagerService.getDevelopers = function (projectManager) {
            return $resource(projectManager._links.developers.href).get();
        };

        return projectManagerService;
    }
}());