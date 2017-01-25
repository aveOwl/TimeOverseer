/**
 * Customer controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var CustomerController = function ($scope, $stateParams, $resource, CustomerService, ProjectService) {
        $scope.customerInfo = true;
        $scope.companiesInfo = true;
        $scope.projectsInfo = true;

        var custId = $stateParams.id; // id from route

        CustomerService.get({id: custId}).$promise
            .then(function success(customer) {
                $scope.customer = customer;

                $scope.updateCustomer = function () {
                    CustomerService.update({id: custId}, $scope.customer);
                };

                // PROJECTS
                var Projects = $resource(customer._links.projects.href);
                Projects.get().$promise
                    .then(function success(response) {
                        $scope.projects = response._embedded.projects;

                        $scope.project = {
                            name: $scope.name,
                            description: $scope.description,
                            startDate: $scope.startDate,
                            endDate: $scope.endDate,
                            customer: customer._links.self.href
                        };

                        $scope.addProject = function () {
                            $scope.projects.push($scope.project);
                            ProjectService.save($scope.project);
                        };
                    }, function error(response) {
                        if (response.status == 404)
                            console.log("Failed to fetch projects for customer: " +
                                $scope.customer.firstName + " " + $scope.customer.firstName);
                    });

                // COMPANIES
                var Companies = $resource(customer._links.companies.href);
                Companies.get().$promise
                    .then(function success(response) {
                        $scope.companies = response._embedded.companies;

                    }, function error(response) {
                        if (response.status == 404)
                            console.log("Failed to fetch companies for customer: " +
                                $scope.customer.firstName + " " + $scope.customer.firstName);
                    });
            }, function error(response) {
                if (response.status == 404)
                    console.log("Failed to fetch customer by id: " + custId);
            });
    };

    // register controller
    app.controller('CustomerController', ['$scope', '$stateParams', '$resource', 'CustomerService', 'ProjectService', CustomerController])
}());