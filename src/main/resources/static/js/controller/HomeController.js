/**
 * Home Controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var HomeController = function ($scope, $window, $log, CompanyService, CustomerService) {
        $scope.company = {
            name: $scope.name,
            founded: $scope.founded,
            industry: $scope.industry,
            founders: $scope.founders,
            products: $scope.products
        };

        $scope.customer = {
            firstName: $scope.firstName,
            lastName: $scope.lastName,
            login: $scope.login,
            password: $scope.password,
            businessInterests: $scope.businessInterests
        };

        $scope.submitCompany = function () {
            CompanyService.save($scope.company, function success(company) {
                $window.location.href = "/#/companies/" + company.id;
                $log.debug("Saved company", $scope.company);
            }, function error(response) {
                $log.error("Failed to save company", response);
            });
        };

        $scope.submitCustomer = function () {
            CustomerService.save($scope.customer, function success(customer) {
                $window.location.href = "/#/customers/" + customer.id;
                $log.debug("Saved customer", $scope.customer);
            }, function error(response) {
                $log.error("Failed to save customer", response);
            });
        };
    };

    // register controller
    app.controller('HomeController',
        ['$scope', '$window', '$log', 'CompanyService', 'CustomerService', HomeController]);
}());