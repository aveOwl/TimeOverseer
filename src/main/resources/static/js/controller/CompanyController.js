/**
 * Company controller.
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var CompanyController = function ($scope, $resource, $stateParams, $log, CompanyService, DeveloperService, ProjectManagerService) {
        $scope.companyInfo = true;
        $scope.employeesInfo = true;
        $scope.customersInfo = true;

        var companyId = $stateParams.id; // id from route

        CompanyService.get({id: companyId}).$promise
            .then(function success(company) {
                $scope.company = company;
                $log.debug("Fetched company", $scope.company);

                $scope.updateCompany = function () {
                    CompanyService.update({id: companyId}, $scope.company).$promise
                        .then(function success(company) {
                            $log.debug("Successfully updated company", company);
                        }, function error(response) {
                            $log.error("Failed to update company", response);
                        });
                };

                // EMPLOYEES
                var Employees = $resource(company._links.employees.href);
                Employees.get().$promise
                    .then(function success(employees) {
                        // if employees is present in '_embedded' there is no developers or projectManagers
                        $scope.noEmployees = typeof employees._embedded.employees != 'undefined';
                        $scope.developers = employees._embedded.developers;
                        $scope.projectManagers = employees._embedded.projectManagers;

                        $log.debug("Fetched developers for company", $scope.developers);
                        $log.debug("Fetched projectManagers for company", $scope.projectManagers);

                        $scope.employee = {
                            firstName: $scope.firstName,
                            lastName: $scope.lastName,
                            login: $scope.login,
                            password: $scope.password,
                            qualification: $scope.qualification,
                            employer: company._links.self.href
                        };

                        $scope.addEmployee = function () {
                            if ($scope.employee.position == 'Developer') {
                                DeveloperService.save($scope.employee).$promise
                                    .then(function (dev) {
                                        if (typeof $scope.developers == 'undefined') {
                                            $scope.developers = [];
                                            $scope.noEmployees = false;
                                        }
                                        $scope.developers.push(dev);
                                        $log.debug("Saved developer for company", dev);
                                    }, function error(response) {
                                        $log.error("Failed to add developer to company", response);
                                    });
                            } else {
                                ProjectManagerService.save($scope.employee).$promise
                                    .then(function (pm) {
                                        if (typeof $scope.projectManagers == 'undefined') {
                                            $scope.projectManagers = [];
                                            $scope.noEmployees = false;
                                        }
                                        $scope.projectManagers.push(pm);
                                        $log.debug("Saved ProjectManager for company", pm);
                                    }, function error(response) {
                                        $log.error("Failed to add projectManager to company", response);
                                    });
                            }
                        };
                    }, function error(response) {
                        $log.error("Failed to fetch employees for company", response);
                    });

                // CUSTOMERS
                var Customers = $resource(company._links.customers.href);
                Customers.get().$promise
                    .then(function success(customers) {
                        $scope.customers = customers._embedded.customers;
                        $log.debug("Fetched customers for company", $scope.customers);

                        // TODO customers for company
                    }, function error(response) {
                        $log.error("Failed to fetch customers for company", response);
                    })
            }, function error(response) {
                $log.error("Failed to fetch company by id", response);
            });
    };

    // register controller
    app.controller('CompanyController',
        ['$scope', '$resource', '$stateParams', '$log', 'CompanyService', 'DeveloperService', 'ProjectManagerService', CompanyController])
}());