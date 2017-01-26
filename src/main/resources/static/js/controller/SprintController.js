/**
 * Sprint controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var SprintController = function ($scope, $resource, $stateParams, $log, SprintService, TaskService) {
        $scope.sprintInfo = true;
        $scope.tasksInfo = true;

        var sprintId = $stateParams.id;

        SprintService.get({id: sprintId}).$promise
            .then(function success(sprint) {
                $scope.sprint = sprint;
                $log.debug("Fetched sprint", $scope.sprint);

                $scope.updateSprint = function () {
                    SprintService.update({id: sprintId}, $scope.sprint).$promise
                        .then(function success(sprint) {
                            $log.debug("Successfully updated sprint", sprint);
                        }, function error(response) {
                            $log.error("Failed to update sprint", response);
                        });
                };

                // PROJECT
                var Project = $resource(sprint._links.project.href);
                Project.get().$promise
                    .then(function success(project) {
                        $scope.project = project;
                        $log.debug("Fetched project for sprint", project);
                    }, function error(response) {
                        $log.error("Failed to fetch project for sprint", response);
                    });

                // TASKS
                var Tasks = $resource(sprint._links.tasks.href);
                Tasks.get().$promise
                    .then(function success(tasks) {
                        $scope.tasks = tasks._embedded.tasks;
                        $log.debug("Fetched tasks for sprint", $scope.tasks);

                        $scope.task = {
                            name: $scope.name,
                            isAssigned: $scope.isAssigned,
                            qualification: $scope.qualification,
                            timeToComplete: $scope.timeToComplete,
                            timeInDevelopment: $scope.timeInDevelopment,
                            sprint: sprint._links.self.href
                        };

                        $scope.addTask = function () {
                            TaskService.save($scope.task).$promise
                                .then(function success(task) {
                                    $scope.tasks.push(task);
                                    $log.debug("Saved task for sprint", task);
                                }, function error(response) {
                                    $log.error("Failed to add task for sprint", response);
                                });
                        };
                    }, function error(response) {
                        $log.error("Failed to fetch tasks for sprint", response);
                    });
            }, function error(response) {
                $log.error("Failed to fetch sprint by id", response);
            });
    };

    // register controller
    app.controller('SprintController',
        ['$scope', '$resource', '$stateParams', '$log', 'SprintService', 'TaskService', SprintController])
}());