/**
 * Project controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var ProjectController = function ($scope, $resource, $stateParams, $log, ProjectService, SprintService) {
        $scope.projectInfo = true;
        $scope.sprintsInfo = true;

        var projectId = $stateParams.id;

        ProjectService.get({id: projectId}).$promise
            .then(function success(project) {
                $scope.project = project;
                $log.debug("Fetched project", $scope.project);

                $scope.updateProject = function () {
                    ProjectService.update({id: projectId}, $scope.project).$promise
                        .then(function success(project) {
                            $log.debug("Successfully updated project", $scope.project);
                        }, function error(response) {
                            $log.error("Failed to update project", response);
                        });
                };

                // CUSTOMER
                var Customer = $resource(project._links.customer.href);
                Customer.get().$promise
                    .then(function (customer) {
                        $scope.customer = customer;
                        $log.debug("Fetched customer", $scope.customer);
                    }, function error(response) {
                        $log.error("Failed to fetch customer for project", response);
                    });

                // MANAGER
                var Manager = $resource(project._links.projectManager.href);
                Manager.get().$promise
                    .then(function success(manager) {
                        $scope.manager = manager;
                        $log.debug("Fetched manager", manager)
                    }, function error() {
                        $log.warn("No manager is assigned to project", $scope.project);
                    });

                // SPRINTS
                var Sprints = $resource(project._links.sprints.href);
                Sprints.get().$promise
                    .then(function (sprints) {
                        $scope.sprints = sprints._embedded.sprints;
                        $log.debug("Fetched sprints", $scope.sprints);

                        $scope.sprint = {
                            name: $scope.name,
                            project: project._links.self.href
                        };

                        $scope.addSprint = function () {
                            SprintService.save($scope.sprint).$promise
                                .then(function success(sprint) {
                                    $scope.sprints.push(sprint);
                                    $log.debug("Saving sprint for project", sprint);
                                }, function error(response) {
                                    $log.error("Failed to add sprint to project", response);
                                });
                        }
                    }, function error(response) {
                        $log.error("Failed to fetch sprints for project", response);
                    });
            }, function error(response) {
                $log.error("Failed to fetch project by id", response);
            });
    };

    // register controller
    app.controller('ProjectController', ['$scope', '$resource', '$stateParams', '$log', 'ProjectService', 'SprintService', ProjectController])
}());