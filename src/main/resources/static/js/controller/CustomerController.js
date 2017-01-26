/**
 * Customer controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var CustomerController = function ($scope, $stateParams, $resource, $log, CustomerService, ProjectService) {
        $scope.customerInfo = true;
        $scope.companiesInfo = true;
        $scope.projectsInfo = true;

        var custId = $stateParams.id; // id from route

        CustomerService.get({id: custId}).$promise
            .then(function success(customer) {
                $scope.customer = customer;
                $log.debug("Fetched customer", customer);

                $scope.updateCustomer = function () {
                    CustomerService.update({id: custId}, $scope.customer).$promise
                        .then(function success(customer) {
                            $log.debug("Successfully updated customer: " + customer);
                        }, function error(response) {
                            $log.error("Failed to update customer", response);
                        });
                };

                // PROJECTS
                var Projects = $resource(customer._links.projects.href);
                Projects.get().$promise
                    .then(function success(response) {
                        $scope.projects = response._embedded.projects;
                        $log.debug("Fetched projects for customer", $scope.projects);

                        $scope.project = {
                            name: $scope.name,
                            description: $scope.description,
                            startDate: $scope.startDate,
                            endDate: $scope.endDate,
                            customer: customer._links.self.href
                        };

                        $scope.addProject = function () {
                            ProjectService.save($scope.project).$promise
                                .then(function success(project) {
                                    $scope.projects.push(project);
                                    $log.debug("Saved project for customer", project);
                                }, function error(response) {
                                    $log.error("Failed to add project for customer", response);
                                });
                        };
                    }, function error(response) {
                        $log.error("Failed to fetch projects for customer", response);
                    });

                // COMPANIES
                var Companies = $resource(customer._links.companies.href);
                Companies.get().$promise
                    .then(function success(response) {
                        $scope.companies = response._embedded.companies;
                        $log.debug("Fetched companies for customer", $scope.companies);

                        // TODO companies for customer
                    }, function error(response) {
                        $log.error("Failed to fetch companies for customer", response);
                    });
            }, function error(response) {
                $log.error("Failed to fetch customer by id", response);
            });
    };

    // register controller
    app.controller('CustomerController',
        ['$scope', '$stateParams', '$resource', '$log', 'CustomerService', 'ProjectService', CustomerController])
}());