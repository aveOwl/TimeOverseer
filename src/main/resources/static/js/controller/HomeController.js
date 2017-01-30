/**
 * Home Controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var HomeController = function ($scope, $window, $log, CompanyService, CustomerService) {
        $scope.submitCompany = function (company) {
            CompanyService.save(company, function success(createdCompany) {
                $window.location.href = "/#/overseer/companies/" + createdCompany.id;
                $log.debug("Saved company", createdCompany);
            }, function error(response) {
                $log.error("Failed to save company", response);
            });
        };

        $scope.submitCustomer = function (customer) {
            CustomerService.save($scope.customer, function success(createdCustomer) {
                $window.location.href = "/#/overseer/customers/" + createdCustomer.id;
                $log.debug("Saved customer", createdCustomer);
            }, function error(response) {
                $log.error("Failed to save customer", response);
            });
        };
    };

    // register controller
    app.controller('HomeController',
        ['$scope', '$window', '$log', 'CompanyService', 'CustomerService', HomeController]);
}());