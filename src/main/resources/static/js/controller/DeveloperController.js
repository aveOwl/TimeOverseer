/**
 * Developer Controller.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .controller('DeveloperController', DeveloperController);

    DeveloperController.$inject = ['$scope', '$stateParams', '$log', 'DeveloperService'];
    function DeveloperController($scope, $stateParams, $log, DeveloperService) {
        var devId = $stateParams.id;

        $scope.developerInfo = true;
        $scope.updateDeveloper = updateDeveloper;

        getDeveloper();

        /**
         * Retrieves developer by id from route.
         */
        function getDeveloper() {
            DeveloperService.perform().get({id: devId}).$promise
                .then(function (developer) {
                    $scope.employee = developer;
                    $log.debug("Fetched developer", developer);

                    getCompany(developer);
                    getManager(developer);
                    getTasks(developer);

                }, function (error) {
                    $log.error("Failed to fetch developer by id", error);
                });
        }

        /**
         * Performs full update on developer entity.
         */
        function updateDeveloper() {
            DeveloperService.perform().update({id: devId}, $scope.employee).$promise
                .then(function (developer) {
                    $log.debug("Successfully updated developer", developer);

                }, function (error) {
                    $log.error("Failed to update developer", error);
                });
        }

        /**
         * Retrieves company associated with provided developer.
         * @param developer
         */
        function getCompany(developer) {
            DeveloperService.getCompany(developer).$promise
                .then(function (company) {
                    $scope.company = company;
                    $log.debug("Fetched company for developer", company);

                }, function (error) {
                    $log.error("Failed to fetch company for developer", error)
                });
        }

        /**
         * Retrieves projectManager associated with provided developer.
         * @param developer
         */
        function getManager(developer) {
            DeveloperService.getManager(developer).$promise
                .then(function (manager) {
                    $scope.manager = manager;
                    $scope.managerName = manager.firstName + " " + manager.lastName;
                    $log.debug("Fetched manager for developer", $scope.manager);

                }, function (error) {
                    $log.warn("No manager is assigned to developer", error);
                });

        }

        /**
         * Retrieves set of tasks associated with provided developer.
         * @param developer
         */
        function getTasks(developer) {
            DeveloperService.getTasks(developer).$promise
                .then(function (response) {
                    $scope.tasks = response._embedded.tasks;
                    $log.debug("Fetched tasks for developer", $scope.tasks);

                }, function (error) {
                    $log.error("Failed to fetch tasks for developer", error);
                });
        }
    }
}());