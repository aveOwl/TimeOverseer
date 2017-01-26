/**
 * Task controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var TaskController = function ($scope, $resource, $stateParams, $log, TaskService) {
        $scope.taskInfo = true;

        var taskId = $stateParams.id;

        TaskService.get({id: taskId}).$promise
            .then(function success(task) {
                $scope.task = task;
                $log.debug("Fetched task", $scope.task);

                $scope.updateTask = function () {
                    TaskService.update({id: taskId}, $scope.task).$promise
                        .then(function success(task) {
                            $log.debug("Successfully updated task", task);
                        }, function error(response) {
                            $log.error("Failed to update task", response);
                        });
                };

                // SPRINT
                var Sprint = $resource(task._links.sprint.href);
                Sprint.get().$promise
                    .then(function success(sprint) {
                        $scope.sprint = sprint;
                        $log.debug("Fetched sprint for task", $scope.sprint);
                    }, function error(response) {
                        $log.error("Failed to fetch sprint for task", response);
                    });

                // DEVELOPERS
                var Developers = $resource(task._links.developers.href);
                Developers.get().$promise
                    .then(function success(developers) {
                        $scope.developers = developers._embedded.developers;
                        $log.debug("Fetched developers for task", $scope.developers);
                    }, function error(response) {
                        $log.error("Failed to fetch developers for task", response);
                    });
            }, function error(response) {
                $log.error("Failed to fetch task by id", response);
            });
    };

    // register controller
    app.controller('TaskController',
        ['$scope', '$resource', '$stateParams', '$log', 'TaskService', TaskController])
}());