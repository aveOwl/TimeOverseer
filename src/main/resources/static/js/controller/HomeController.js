/**
 * Home Controller.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', '$window', '$log', 'CompanyService', 'CustomerService'];
    function HomeController($scope, $window, $log, CompanyService, CustomerService) {

        $scope.submitCompany = submitCompany;
        $scope.submitCustomer = submitCustomer;

        function submitCompany(company) {
            CompanyService.perform().save(company).$promise
                .then(function (createdCompany) {
                    $window.location.href = "/#/overseer/companies/" + createdCompany.id;
                    $log.debug("Saving company", createdCompany);

                }, function (error) {
                    $log.error("Failed to save company", error);
                });
        }

        function submitCustomer(customer) {
            CustomerService.perform().save(customer).$promise
                .then(function (createdCustomer) {
                    $window.location.href = "/#/overseer/customers/" + createdCustomer.id;
                    $log.debug("Saved customer", createdCustomer);

                }, function (error) {
                    $log.error("Failed to save customer", error);
                });
        }
    }
}());