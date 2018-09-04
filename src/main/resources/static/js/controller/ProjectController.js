/**
 * Project Controller.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .controller('ProjectController', ProjectController);

    ProjectController.$inject = ['$scope', '$stateParams', '$log', 'ProjectService', 'SprintService'];

    function ProjectController($scope, $stateParams, $log, ProjectService, SprintService) {
        var projectId = $stateParams.id;

        $scope.projectInfo = true;
        $scope.sprintsInfo = true;
        $scope.updateProject = updateProject;
        $scope.addSprint = addSprint;

        getProject();

        /**
         * Retrieves project by id from route.
         */
        function getProject() {
            ProjectService.perform().get({id: projectId}).$promise
                .then(function (project) {
                    $scope.project = project;
                    $log.debug("Fetched project", project);

                    getCustomer(project);
                    getManager(project);
                    getSprints(project);

                }, function (error) {
                    $log.error("Failed to fetch project by id", error);
                });
        }

        /**
         * Performs full update on project entity.
         */
        function updateProject() {
            ProjectService.perform().update({id: projectId}, $scope.project).$promise
                .then(function (project) {
                    $log.debug("Successfully updated project", project);

                }, function (error) {
                    $log.error("Failed to update project", error);
                });
        }

        /**
         * Retrieves customer associated with provided project.
         * @param project
         */
        function getCustomer(project) {
            ProjectService.getCustomer(project).$promise
                .then(function (customer) {
                    $scope.customer = customer;
                    $scope.customerName = customer.firstName + " " + customer.lastName;
                    $log.debug("Fetched customer", customer);

                }, function (error) {
                    $log.error("Failed to fetch customer for project", error);
                });
        }

        /**
         * Retrieves projectManager associated with provided project.
         * @param project
         */
        function getManager(project) {
            ProjectService.getManager(project).$promise
                .then(function (manager) {
                    $scope.manager = manager;
                    $scope.managerName = manager.firstName + " " + manager.lastName;
                    $log.debug("Fetched manager", manager);

                }, function (error) {
                    $log.warn("No manager is assigned to project", error);
                });
        }

        /**
         * Retrieves set of sprints associated with provided project.
         * @param project
         */
        function getSprints(project) {
            ProjectService.getSprints(project).$promise
                .then(function (response) {
                    $scope.sprints = response._embedded.sprints;
                    $log.debug("Fetched sprints", $scope.sprints);

                    $scope.sprint = {
                        name: $scope.name,
                        project: project._links.self.href
                    };

                }, function (error) {
                    $log.error("Failed to fetch sprints for project", error);
                });
        }

        /**
         * Adds sprint to project.
         */
        function addSprint() {
            SprintService.perform().save($scope.sprint).$promise
                .then(function (sprint) {
                    $scope.sprints.push(sprint);
                    $log.debug("Saving sprint for project", sprint);

                }, function (error) {
                    $log.error("Failed to add sprint to project", error);
                });
        }
    }
}());