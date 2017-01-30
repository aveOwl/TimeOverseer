/**
 * ProjectManager Controller.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .controller('ProjectManagerController', ProjectManagerController);

    ProjectManagerController.$inject = ['$scope', '$stateParams', '$log', 'ProjectManagerService'];
    function ProjectManagerController($scope, $stateParams, $log, ProjectManagerService) {
        var pmId = $stateParams.id;

        $scope.projectManagerInfo = true;
        $scope.updateProjectManager = updateProjectManager;

        getProjectManager();

        /**
         * Retrieves projectManager by id from route.
         */
        function getProjectManager() {
            ProjectManagerService.perform().get({id: pmId}).$promise
                .then(function (projectManager) {
                    $scope.employee = projectManager;
                    $log.debug("Fetched projectManager", projectManager);

                    getCompany(projectManager);
                    getProject(projectManager);
                    getDevelopers(projectManager);

                }, function (error) {
                    $log.error("Failed to fetch projectManager by id", error);
                });
        }

        /**
         * Performs full update on projectManager entity.
         */
        function updateProjectManager() {
            ProjectManagerService.perform().update({id: pmId}, $scope.employee).$promise
                .then(function (projectManager) {
                    $log.debug("Successfully updated projectManager", projectManager);

                }, function (error) {
                    $log.error("Failed to update projectManager", error);
                });
        }

        /**
         * Retrieves company associated with provided projectManager.
         * @param projectManager
         */
        function getCompany(projectManager) {
            ProjectManagerService.getCompany(projectManager).$promise
                .then(function (company) {
                    $scope.company = company;
                    $log.debug("Fetched company for projectManager", company);

                }, function (error) {
                    $log.error("Failed to fetch company for projectManager", error)
                });
        }

        /**
         * Retrieves project associated with provided projectManager.
         * @param projectManager
         */
        function getProject(projectManager) {
            ProjectManagerService.getProject(projectManager).$promise
                .then(function (project) {
                    $scope.project = project;
                    $log.debug("Fetched project for projectManager", project);

                }, function (error) {
                    $log.warn("No project is assigned to projectManager", error);
                });
        }

        /**
         * Retrieves set of developers associated with provided projectManager.
         * @param projectManager
         */
        function getDevelopers(projectManager) {
            ProjectManagerService.getDevelopers(projectManager).$promise
                .then(function (response) {
                    $scope.developers = response._embedded.developers;
                    $log.debug("Fetched developers for projectManager", $scope.developers);

                }, function (error) {
                    $log.error("Failed to fetch developers for projectManager", error);
                });
        }
    }
}());