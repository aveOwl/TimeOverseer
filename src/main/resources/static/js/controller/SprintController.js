/**
 * Sprint controller
 */
(function () {
    'use strict';

    angular.module('overseer')
        .controller('SprintController', SprintController);

    SprintController.$inject = ['$scope', '$stateParams', '$log', 'SprintService', 'TaskService'];

    function SprintController($scope, $stateParams, $log, SprintService, TaskService) {
        var sprintId = $stateParams.id;

        $scope.sprintInfo = true;
        $scope.tasksInfo = true;
        $scope.updateSprint = updateSprint;
        $scope.addTask = addTask;

        getSprint();

        /**
         * Retrieves sprint by id from route.
         */
        function getSprint() {
            SprintService.perform().get({id: sprintId}).$promise
                .then(function (sprint) {
                    $scope.sprint = sprint;
                    $log.debug("Fetched sprint", sprint);

                    getProject(sprint);
                    getTasks(sprint);

                }, function (error) {
                    $log.error("Failed to fetch sprint by id", error);
                });
        }

        /**
         * Performs full update on sprint entity.
         */
        function updateSprint() {
            SprintService.perform().update({id: sprintId}, $scope.sprint).$promise
                .then(function (sprint) {
                    $log.debug("Successfully updated sprint", sprint);

                }, function (error) {
                    $log.error("Failed to update sprint", error);
                });
        }

        /**
         * Retrieves project associated with provided sprint.
         * @param sprint
         */
        function getProject(sprint) {
            SprintService.getProject(sprint).$promise
                .then(function (project) {
                    $scope.project = project;
                    $log.debug("Fetched project for sprint", project);

                }, function (error) {
                    $log.error("Failed to fetch project for sprint", error);
                });
        }

        /**
         * Retrieves set of tasks associated with provided sprint.
         * @param sprint
         */
        function getTasks(sprint) {
            SprintService.getTasks(sprint).$promise
                .then(function (response) {
                    $scope.tasks = response._embedded.tasks;
                    $log.debug("Fetched tasks for sprint", $scope.tasks);

                    $scope.task = {
                        name: $scope.name,
                        isAssigned: $scope.isAssigned,
                        qualification: $scope.qualification,
                        timeToComplete: $scope.timeToComplete,
                        timeInDevelopment: $scope.timeInDevelopment,
                        sprint: sprint._links.self.href
                    };

                }, function (error) {
                    $log.error("Failed to fetch tasks for sprint", error);
                });
        }

        /**
         * Adds task to sprint.
         */
        function addTask() {
            TaskService.perform().save($scope.task).$promise
                .then(function (task) {
                    $scope.tasks.push(task);
                    $log.debug("Saving task for sprint", task);

                }, function (error) {
                    $log.error("Failed to add task for sprint", error);
                });
        }
    }
}());