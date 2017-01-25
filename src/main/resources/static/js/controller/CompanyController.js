/**
 * Company controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var CompanyController = function ($scope, $resource, $stateParams, CompanyService) {
        $scope.companyInfo = true;
        $scope.employeesInfo = true;
        $scope.customersInfo = true;

        var companyId = $stateParams.id; // id from route

        CompanyService.get({id: companyId}).$promise
            .then(function success(company) {
                $scope.company = company;

                $scope.updateCompany = function () {
                    CompanyService.update({id: companyId}, $scope.company);
                };

                // EMPLOYEES
                var Employees = $resource(company._links.employees.href);
                Employees.get().$promise
                    .then(function success(employees) {
                        $scope.developers = employees._embedded.developers;
                        $scope.projectManagers = employees._embedded.projectManagers;
                        $scope.noEmployees = employees._embedded.employees.length == 0;

                    }, function error(response) {
                        if (response.status == 404)
                            console.log("Failed to fetch employees for company: " + $scope.company.name);
                    });

                // CUSTOMERS
                var Customers = $resource(company._links.customers.href);
                Customers.get().$promise
                    .then(function success(customers) {
                        $scope.customers = customers._embedded.customers;

                    }, function error(response) {
                        if (response.status == 404)
                            console.log("Failed to fetch customers for company: " + $scope.company.name);
                    })
            }, function error(response) {
                if (response.status == 404)
                    console.log("Failed to fetch company by id: " + companyId);
            });
    };

    // register controller
    app.controller('CompanyController', ['$scope', '$resource', '$stateParams', 'CompanyService', CompanyController])
}());