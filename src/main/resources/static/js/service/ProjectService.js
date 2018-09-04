/**
 * Project Service.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .factory('ProjectService', ProjectService);

    ProjectService.$inject = ['$resource'];

    function ProjectService($resource) {
        var projectService = {};

        // Basic CRUD operations
        projectService.perform = function () {
            return $resource('/projects/:id', null,
                {
                    'update': {method: 'PUT'}
                });
        };

        projectService.getCustomer = function (project) {
            return $resource(project._links.customer.href).get();
        };

        projectService.getManager = function (project) {
            return $resource(project._links.projectManager.href).get();
        };

        projectService.getSprints = function (project) {
            return $resource(project._links.sprints.href).get();
        };

        return projectService;
    }
}());