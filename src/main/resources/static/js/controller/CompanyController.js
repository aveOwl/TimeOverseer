/**
 * Company Controller.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .controller('CompanyController', CompanyController);

    CompanyController.$inject = ['$scope', '$stateParams', '$log', '$location', 'CompanyService'];
    function CompanyController($scope, $stateParams, $log, $location, CompanyService) {
        var companyId = $stateParams.id;

        $scope.companyInfo = true;
        $scope.employeesInfo = true;
        $scope.customersInfo = true;

        $scope.updateCompany = updateCompany;
        $scope.cancelEdit = cancelEdit;
        $scope.removeCompany = removeCompany;

        $scope.addEmployee = addEmployee;
        $scope.cancelEmployeeAdd = cancelEmployeeAdd;

        $scope.addToRemoveList = addToRemoveList;
        $scope.removeEmployees = removeEmployees;

        $scope.selected = {};
        $scope.someSelected = function (object) {
            return Object.keys(object).some(function (key) {
                return object[key];
            })
        };

        getCompany();

        var localCompany;

        /**
         * Retrieves company by id from route.
         */
        function getCompany() {
            CompanyService.perform().get({id: companyId}).$promise
                .then(function (company) {
                    $scope.company = company;
                    $log.debug("Fetched company", company);

                    localCompany = angular.copy(company);

                    getEmployees(company);
                    getCustomers(company);

                }, function (error) {
                    $log.error("Failed to fetch company by id", error);
                });
        }

        /**
         * Performs full update on company entity.
         */
        function updateCompany() {
            CompanyService.perform().update({id: companyId}, $scope.company).$promise
                .then(function (company) {
                    $log.debug("Successfully updated company", company);

                }, function (error) {
                    $log.error("Failed to update company", error);
                });
        }

        /**
         * Resets edit form to previous state.
         */
        function cancelEdit() {
            $scope.company = angular.copy(localCompany);
            $scope.companyInfo = true;
        }

        /**
         * Removes company.
         */
        function removeCompany() {
            CompanyService.perform().addToRemoveList({id: companyId}).$promise
                .then(function () {
                    $log.debug("Successfully removed company");
                    $location.path("/#/overseer");

                }, function (error) {
                    $log.error("Failed to remove company", error);
                })
        }

        /**
         * Retrieves employees associated with provided company.
         * @param company
         */
        function getEmployees(company) {
            CompanyService.getEmployees(company).$promise
                .then(function (employees) {
                    $scope.noEmployees = CompanyService.isEmployeesPresent(employees);
                    $scope.developers = CompanyService.getDevelopers(employees);
                    $scope.projectManagers = CompanyService.getProjectManagers(employees);

                    $log.debug("Fetched developers for company", $scope.developers);
                    $log.debug("Fetched projectManagers for company", $scope.projectManagers);
                }, function (error) {
                    $log.error("Failed to fetch employees for company", error);
                });
        }

        /**
         * Adds employee to company. Developer or ProjectManager depending
         * on specified position.
         */
        function addEmployee(employee) {
            $scope.noEmployees = false;
            employee.employer = $scope.company._links.self.href;

            CompanyService.addEmployee(employee);

            $scope.employee = {};
            $scope.employee_form.$setPristine();
        }

        /**
         * Resets employee form to its previous state.
         */
        function cancelEmployeeAdd() {
            $scope.employeesInfo = true;
        }

        /**
         * Adds employee to remove list specifying its position.
         * @param employee
         * @param position
         */
        function addToRemoveList(employee, position) {
            employee.position = position;
            CompanyService.addToRemoveList(employee);
        }

        /**
         * Removes all employees prepared to removal and excludes them from
         * developers and projectManagers list.
         */
        function removeEmployees() {
            $scope.developers = CompanyService.exclude($scope.developers);
            $scope.projectManagers = CompanyService.exclude($scope.projectManagers);

            CompanyService.removeEmployees($scope.developers, $scope.projectManagers);
            if ($scope.developers.length == 0 && $scope.projectManagers.length == 0) {
                $scope.noEmployees = true;
            }
        }

        /**
         * Retrieves customers associated with provided company.
         * @param company
         */
        function getCustomers(company) {
            CompanyService.getCustomers(company).$promise
                .then(function (response) {
                    $scope.customers = response._embedded.customers;
                    $log.debug("Fetched customers for company", $scope.customers);

                }, function (error) {
                    $log.error("Failed to fetch customers for company", error);
                })
        }
    }
}());