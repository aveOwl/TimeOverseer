/**
 * Home Controller.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', '$window', '$log', 'HomeService'];
    function HomeController($scope, $window, $log, HomeService) {

        $scope.submitCompany = submitCompany;
        $scope.submitCustomer = submitCustomer;

        function submitCompany(company) {
            HomeService.saveCompany(company).$promise
                .then(function (createdCompany) {
                    $log.debug("Saving company", createdCompany);
                    $window.location.href = "/#/overseer/companies/" + createdCompany.id;

                }, function (error) {
                    $log.error("Failed to save company", error);
                });
        }

        function submitCustomer(customer) {
            HomeService.saveCustomer(customer).$promise
                .then(function (createdCustomer) {
                    $log.debug("Saved customer", createdCustomer);
                    $window.location.href = "/#/overseer/customers/" + createdCustomer.id;

                }, function (error) {
                    $log.error("Failed to save customer", error);
                });
        }
    }
}());