/**
 * Project controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var ProjectController = function ($scope, $resource, $stateParams, ProjectService, SprintService) {
        $scope.projectInfo = true;
        $scope.sprintsInfo = true;

        var projectId = $stateParams.id;

        ProjectService.get({id: projectId}).$promise
            .then(function success(project) {
                $scope.project = project;

                $scope.updateProject = function () {
                    ProjectService.update($scope.project);
                };

                // CUSTOMER
                var Customer = $resource(project._links.customer.href);
                Customer.get().$promise
                    .then(function (customer) {
                        $scope.customer = customer;
                        $scope.customerName = customer.firstName + " " + customer.lastName;
                    }, function error(response) {
                        if (response.status == 404)
                            console.log("Failed to fetch customer for project: " + $scope.project.name);
                    });

                // MANAGER
                var Manager = $resource(project._links.projectManager.href);
                Manager.get().$promise
                    .then(function success(manager) {
                        $scope.manager = manager;
                    }, function error(response) {
                        if (response.status == 404) {
                            $scope.manager = "Not Assigned Yet";
                            console.log("No manager is assigned to project: " + $scope.project.name);
                        }
                    });

                // SPRINTS
                var Sprints = $resource(project._links.sprints.href);
                Sprints.get().$promise
                    .then(function (sprints) {
                        $scope.sprints = sprints._embedded.sprints;

                        $scope.sprint = {
                            name: $scope.name,
                            project: project._links.self.href
                        };

                        $scope.addSprint = function () {
                            $scope.sprints.push($scope.sprint);
                            SprintService.save($scope.sprint);
                        }
                    }, function error(response) {
                        if (response.status == 404)
                            console.log("Failed to fetch sprints for project: " + $scope.project.name);
                    });
            }, function error(response) {
                if (response.status == 404)
                    console.log("Failed to fetch project by id: " + projectId);
            });
    };

    // register controller
    app.controller('ProjectController', ['$scope', '$resource', '$stateParams', 'ProjectService', 'SprintService', ProjectController])
}());