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
        $scope.selected = {};

        $scope.updateCompany = updateCompany;
        $scope.cancelOnCompany = cancelOnCompany;
        $scope.removeCompany = removeCompany;

        $scope.addEmployee = addEmployee;
        $scope.cancelOnEmployee = cancelOnEmployee;
        $scope.addToRemoveList = addToRemoveList;
        $scope.removeEmployees = removeEmployees;

        $scope.someSelected = someSelected;

        getCompany();

        /**
         * Retrieves company by id from route.
         */
        function getCompany() {
            CompanyService.perform().get({id: companyId}).$promise
                .then(function (company) {
                    $scope.company = company;
                    $log.debug("Fetched company", company);

                    $scope.originCompany = angular.copy(company);

                    getEmployees(company);
                    getCustomers(company);

                }, function (error) {
                    $log.error("Failed to fetch company by id", error);
                });
        }

        /**
         * Performs full company update.
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
         * Resets company to its previous state.
         */
        function cancelOnCompany() {
            $scope.company = angular.copy($scope.originCompany);
            $scope.companyInfo = true;
        }

        /**
         * Removes company.
         */
        function removeCompany() {
            CompanyService.perform().remove({id: companyId}).$promise
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

                    $scope.employee = {
                        firstName: '',
                        lastName: '',
                        login: '',
                        password: '',
                        qualification: '',
                        position: ''
                    };
                    $scope.originEmployee = angular.copy($scope.employee);

                    $log.debug("Fetched developers for company", $scope.developers);
                    $log.debug("Fetched projectManagers for company", $scope.projectManagers);
                }, function (error) {
                    $log.error("Failed to fetch employees for company", error);
                });
        }

        /**
         * Adds employee to company.
         */
        function addEmployee(employee) {
            $scope.noEmployees = false;
            employee.employer = $scope.company._links.self.href;

            CompanyService.addEmployee(employee);

            resetEmployeeForm();
        }

        /**
         * Resets employee to its previous state.
         */
        function cancelOnEmployee() {
            $scope.employeesInfo = true;
            resetEmployeeForm();
        }

        /**
         * Resets employee form to its previous state.
         */
        function resetEmployeeForm() {
            $scope.employee = angular.copy($scope.originEmployee);
            $scope.employee_form.$setPristine();
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

            CompanyService.removeEmployees($scope.selected);
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

        /**
         * Returns true if at least one employee marked to removal.
         */
        function someSelected(object) {
            return Object.keys(object).some(function (key) {
                return object[key];
            })
        }
    }
}());