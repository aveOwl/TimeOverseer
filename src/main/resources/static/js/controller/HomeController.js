/**
 * Home Controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var HomeController = function ($scope, $window, CompanyService, CustomerService) {
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
            }, function error(response) {
                console.log(response);
            });
        };

        $scope.submitCustomer = function () {
            CustomerService.save($scope.customer, function success(customer) {
                $window.location.href = "/#/customers/" + customer.id;
            }, function error(response) {
                console.log(response);
            });
        };
    };

    // register controller
    app.controller('HomeController', ['$scope', '$window', 'CompanyService', 'CustomerService', HomeController]);
}());