/**
 * Home Service.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .factory('HomeService', HomeService);

    HomeService.$inject = ['CompanyService', 'CustomerService'];

    function HomeService(CompanyService, CustomerService) {
        var homeService = {};


        homeService.saveCompany = function (company) {
            return CompanyService.perform().save(company);
        };

        homeService.saveCustomer = function (customer) {
            return CustomerService.perform().save(customer);
        };

        return homeService;
    }
}());