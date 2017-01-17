/**
 * Customer controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var CustomerController = function ($scope, $resource, $stateParams, $http, $location) {
        $scope.customerInfo = true;
        $scope.projectsInfo = true;

        $resource('/customers/:id', {id: '@id'}).get({id: $stateParams.id})
            .$promise
            .then(function (customer) {
                $scope.customer = customer;

                $scope.updateCustomer = function () {
                    $http({
                        method: 'PUT',
                        url: $location.url(),
                        data: $scope.customer
                    });
                };

                var getProjects = customer._links.projects.href;
                $http.get(getProjects).then(function (response) {
                    $scope.projects = response.data._embedded.projects;

                    $scope.project = {
                        name: $scope.name,
                        description: $scope.description,
                        startDate: $scope.startDate,
                        endDate: $scope.endDate,
                        customer: customer._links.self.href
                    };

                    $scope.addProject = function () {
                        $scope.projects.push($scope.project);

                        $http({
                            method: 'POST',
                            url: '/projects',
                            data: $scope.project
                        });
                    };
                });
            });
    };

    // register controller
    app.controller('CustomerController', ['$scope', '$resource', '$stateParams', '$http', '$location', CustomerController])
}());