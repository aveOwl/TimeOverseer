/**
 * Customer Controller.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .controller('CustomerController', CustomerController);

    CustomerController.$inject = ['$scope', '$stateParams', '$log', 'CustomerService', 'ProjectService'];
    function CustomerController($scope, $stateParams, $log, CustomerService, ProjectService) {
        var custId = $stateParams.id; // id from route

        $scope.customerInfo = true;
        $scope.companiesInfo = true;
        $scope.projectsInfo = true;
        $scope.updateCustomer = updateCustomer;
        $scope.addProject = addProject;

        getCustomer();

        /**
         * Retrieves customer by id from route.
         */
        function getCustomer() {
            CustomerService.perform().get({id: custId}).$promise
                .then(function (customer) {
                    $scope.customer = customer;
                    $scope.customerName = customer.firstName + " " + customer.lastName;
                    $log.debug("Fetched customer", customer);

                    getProjects(customer);
                    getCompanies(customer);

                }, function (error) {
                    $log.error("Failed to fetch customer by id", error);
                });
        }

        /**
         * Performs full update on customer entity.
         */
        function updateCustomer() {
            CustomerService.perform().update({id: custId}, $scope.customer).$promise
                .then(function (customer) {
                    $log.debug("Successfully updated customer", customer);
                    $scope.customerName = customer.firstName + " " + customer.lastName;

                }, function (error) {
                    $log.error("Failed to update customer", error);
                });
        }

        /**
         * Retrieves projects associated with provided customer.
         * @param customer
         */
        function getProjects(customer) {
            CustomerService.getProjects(customer).$promise
                .then(function (response) {
                    $scope.projects = response._embedded.projects;
                    $log.debug("Fetched projects for customer", $scope.projects);

                    $scope.project = {
                        name: $scope.name,
                        description: $scope.description,
                        startDate: $scope.startDate,
                        endDate: $scope.endDate,
                        customer: customer._links.self.href
                    };

                }, function (error) {
                    $log.error("Failed to fetch projects for customer", error);
                });
        }

        /**
         * Retrieves companies associated with provided customer.
         * @param customer
         */
        function getCompanies(customer) {
            CustomerService.getCompanies(customer).$promise
                .then(function (response) {
                    $scope.companies = response._embedded.companies;
                    $log.debug("Fetched companies for customer", $scope.companies);

                }, function (error) {
                    $log.error("Failed to fetch companies for customer", error);
                });
        }

        /**
         * Adds project to company.
         */
        function addProject() {
            ProjectService.perform().save($scope.project).$promise
                .then(function (project) {
                    $scope.projects.push(project);
                    $log.debug("Saving project for customer", project);

                }, function (error) {
                    $log.error("Failed to add project for customer", error);
                });
        }
    }
}());