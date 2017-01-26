/**
 * ProjectManager controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var ProjectManagerController = function ($scope, $stateParams, $resource, $log, ProjectManagerService) {
        $scope.projectManagerInfo = true;

        var pmId = $stateParams.id; // id from route

        ProjectManagerService.get({id: pmId}).$promise
            .then(function success(projectManager) {
                $scope.employee = projectManager;

                $scope.updateProjectManager = function () {
                    ProjectManagerService.update({id: pmId}, $scope.employee).$promise
                        .then(function success(projectManager) {
                            $log.debug("Successfully updated projectManager", projectManager);
                        }, function error(response) {
                            $log.error("Failed to update projectManager", response);
                        });
                };

                // Company
                var Company = $resource(projectManager._links.employer.href);
                Company.get().$promise
                    .then(function success(company) {
                        $scope.company = company;
                        $log.debug("Fetched company for projectManager", company);
                    }, function error(response) {
                        $log.error("Failed to fetch company for projectManager", response)
                    });

                // Project
                var Project = $resource(projectManager._links.project.href);
                Project.get().$promise
                    .then(function success(project) {
                        $scope.project = project;
                        $log.debug("Fetched project for projectManager", project);

                    }, function error(response) {
                        $log.warn("No project is assigned to projectManager", response);
                    });

                // Developers
                var Developers = $resource(projectManager._links.developers.href);
                Developers.get().$promise
                    .then(function success(response) {
                        $scope.developers = response._embedded.developers;
                        $log.debug("Fetched developers for projectManager", $scope.developers);

                    }, function error(response) {
                        $log.error("Failed to fetch developers for projectManager", response);
                    });

            }, function error(response) {
                $log.error("Failed to fetch projectManager by id", response);
            });
    };

    // register controller
    app.controller('ProjectManagerController',
        ['$scope', '$stateParams', '$resource', '$log', 'ProjectManagerService', ProjectManagerController])
}());