/**
 * Customer Service.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .factory('CustomerService', CustomerService);

    CustomerService.$inject = ['$resource'];
    function CustomerService($resource) {
        var customerService = {};

        // Basic CRUD operations
        customerService.perform = function () {
            return $resource('/customers/:id', null,
                {
                    'update': {method: 'PUT'}
                });
        };

        customerService.getProjects = function (customer) {
            return $resource(customer._links.projects.href).get();
        };

        customerService.getCompanies = function (customer) {
            return $resource(customer._links.companies.href).get();
        };

        return customerService;
    }
}());