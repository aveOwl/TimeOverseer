/**
 * Developer controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var DeveloperController = function ($scope, $stateParams, $resource, $log, DeveloperService) {
        $scope.developerInfo = true;

        var devId = $stateParams.id; // id from route

        DeveloperService.get({id: devId}).$promise
            .then(function success(developer) {
                $scope.employee = developer;

                $scope.updateDeveloper = function () {
                    DeveloperService.update({id: devId}, $scope.employee).$promise
                        .then(function success(developer) {
                            $log.debug("Successfully updated developer", developer);
                        }, function error(response) {
                            $log.error("Failed to update developer", response);
                        });
                };

                // Company
                var Company = $resource(developer._links.employer.href);
                Company.get().$promise
                    .then(function success(company) {
                        $scope.company = company;
                        $log.debug("Fetched company for developer", $scope.company);
                    }, function error(response) {
                        $log.error("Failed to fetch company for developer", response)
                    });

                // MANAGER
                var Manager = $resource(developer._links.projectManager.href);
                Manager.get().$promise
                    .then(function success(manager) {
                        $scope.manager = manager;
                        $log.debug("Fetched manager for developer", $scope.manager);
                    }, function error(response) {
                        $log.warn("No manager is assigned to developer", response);
                    });

                // Tasks
                var Tasks = $resource(developer._links.tasks.href);
                Tasks.get().$promise
                    .then(function success(response) {
                        $scope.tasks = response._embedded.tasks;
                        $log.debug("Fetched tasks for developer", $scope.tasks);
                    }, function error(response) {
                        $log.error("Failed to fetch tasks for developer", response);
                    });

            }, function error(response) {
                $log.error("Failed to fetch developer by id", response);
            });
    };

    // register controller
    app.controller('DeveloperController',
        ['$scope', '$stateParams', '$resource', '$log', 'DeveloperService', DeveloperController])
}());