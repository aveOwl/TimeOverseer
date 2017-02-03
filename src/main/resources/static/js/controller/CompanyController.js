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
        $scope.removeCompany = removeCompany;
        $scope.addEmployee = addEmployee;
        $scope.removeEmployees = removeEmployees;
        $scope.checkDev = checkDev;
        $scope.checkPm = checkPm;

        getCompany();

        /**
         * Retrieves company by id from route.
         */
        function getCompany() {
            CompanyService.perform().get({id: companyId}).$promise
                .then(function (company) {
                    $scope.company = company;
                    $log.debug("Fetched company", company);

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

                    $log.debug("Fetched developers for company", $scope.developers);
                    $log.debug("Fetched projectManagers for company", $scope.projectManagers);

                    $scope.employee = {
                        firstName: $scope.firstName,
                        lastName: $scope.lastName,
                        login: $scope.login,
                        password: $scope.password,
                        qualification: $scope.qualification,
                        employer: company._links.self.href
                    };

                }, function (error) {
                    $log.error("Failed to fetch employees for company", error);
                });
        }

        /**
         * Adds employee to company. Developer or ProjectManager depending
         * on specified position.
         */
        function addEmployee() {
            
            $scope.noEmployees = false;
            if ($scope.employee.position == 'Developer') {
                CompanyService.addDeveloper($scope.employee);
            } else if ($scope.employee.position == 'ProjectManager') {
                CompanyService.addProjectManager($scope.employee);
            }
        }

        function verifyLogin(login) {

        }

        function removeEmployees() {
            CompanyService.removeEmployees($scope.developers, $scope.projectManagers);
            if ($scope.developers.length == 0 && $scope.projectManagers.length == 0) {
                $scope.noEmployees = true;
            }
        }

        function checkDev(index) {
            CompanyService.checkDev($scope.developers, index);
        }

        function checkPm(index) {
            CompanyService.checkPm($scope.projectManagers, index);
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