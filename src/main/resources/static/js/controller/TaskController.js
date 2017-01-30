/**
 * Task Controller.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .controller('TaskController', TaskController);

    TaskController.$inject = ['$scope', '$stateParams', '$log', 'TaskService'];
    function TaskController($scope, $stateParams, $log, TaskService) {
        var taskId = $stateParams.id;

        $scope.taskInfo = true;
        $scope.updateTask = updateTask;

        getTask();

        /**
         * Retrieves task by id from route.
         */
        function getTask() {
            TaskService.perform().get({id: taskId}).$promise
                .then(function (task) {
                    $scope.task = task;
                    $log.debug("Fetched task", $scope.task);

                    getSprint(task);
                    getDevelopers(task);

                }, function error(response) {
                    $log.error("Failed to fetch task by id", response);
                });
        }

        /**
         * Performs full update on task entity.
         */
        function updateTask() {
            TaskService.perform().update({id: taskId}, $scope.task).$promise
                .then(function (task) {
                    $log.debug("Successfully updated task", task);

                }, function (error) {
                    $log.error("Failed to update task", error);
                });
        }

        /**
         * Retrieves sprint associated with provided task.
         * @param task
         */
        function getSprint(task) {
            TaskService.getSprint(task).$promise
                .then(function (sprint) {
                    $scope.sprint = sprint;
                    $log.debug("Fetched sprint for task", sprint);

                }, function (error) {
                    $log.error("Failed to fetch sprint for task", error);
                });
        }

        /**
         * Retrieves set of developers associated with provided task.
         * @param task
         */
        function getDevelopers(task) {
            TaskService.getDevelopers(task).$promise
                .then(function (response) {
                    $scope.developers = response._embedded.developers;
                    $log.debug("Fetched developers for task", $scope.developers);

                }, function (error) {
                    $log.error("Failed to fetch developers for task", error);
                });
        }
    }
}());