/**
 * Developer Service.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .factory('DeveloperService', DeveloperService);

    DeveloperService.$inject = ['$resource'];

    function DeveloperService($resource) {
        var developerService = {};

        // Basic CRUD operations
        developerService.perform = function () {
            return $resource('/developers/:id', null,
                {
                    'update': {method: 'PUT'}
                });
        };

        developerService.getCompany = function (developer) {
            return $resource(developer._links.employer.href).get();
        };

        developerService.getManager = function (developer) {
            return $resource(developer._links.projectManager.href).get();
        };

        developerService.getTasks = function (developer) {
            return $resource(developer._links.tasks.href).get();
        };

        return developerService;
    }
}());