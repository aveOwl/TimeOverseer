/**
 * Project controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var ProjectController = function ($scope, $resource, $stateParams, $http, $location) {
        $scope.projectInfo = true;
        $scope.sprintsInfo = true;

        $resource('/projects/:id', {id: '@id'}).get({id: $stateParams.id})
            .$promise
            .then(function (project) {
                $scope.project = project;

                $scope.updateProject = function () {
                    $http({
                        method: 'PUT',
                        url: $location.url(),
                        data: $scope.project
                    });
                };

                // CUSTOMER
                var customerLink = project._links.customer.href;
                $http.get(customerLink).then(function (response) {
                    $scope.customer = response.data;
                    $scope.customerName = $scope.customer.firstName + " " + $scope.customer.lastName;
                });

                // MANAGER
                var managerLink = project._links.projectManager.href;
                $http.get(managerLink).then(function success(response) {
                    $scope.manager = response.data;
                }, function error() {
                    $scope.manager = "Not Assigned Yet";
                });

                // SPRINTS
                var sprintsLink = project._links.sprints.href;
                $http.get(sprintsLink).then(function (response) {
                    $scope.sprints = response.data._embedded.sprints;

                    $scope.sprint = {
                        name: $scope.name,
                        project: project._links.self.href
                    };

                    $scope.addSprint = function () {
                        $scope.sprints.push($scope.sprint);

                        $http({
                            method: 'POST',
                            url: '/sprints',
                            data: $scope.sprint
                        })
                    }
                });
            });
    };

    // register controller
    app.controller('ProjectController', ['$scope', '$resource', '$stateParams', '$http', '$location', ProjectController])
}());