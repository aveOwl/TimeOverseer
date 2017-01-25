/**
 * Sprint controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var SprintController = function ($scope, $resource, $stateParams, SprintService) {
        $scope.sprintInfo = true;
        $scope.tasksInfo = true;

        var sprintId = $stateParams.id;

        SprintService.get({id: sprintId}).$promise
            .then(function success(sprint) {
                $scope.sprint = sprint;

                $scope.updateSprint = function () {
                    SprintService.update($scope.sprint);
                };

                // PROJECT
                var Project = $resource(sprint._links.project.href);
                Project.get().$promise
                    .then(function success(project) {
                        $scope.project = project;

                    }, function error(response) {
                        if (response.status == 404)
                            console.log("Failed to fetch project for sprint: " + $scope.sprint.name);
                    });

                // TASKS
                var Tasks = $resource(sprint._links.tasks.href);
                Tasks.get().$promise
                    .then(function success(tasks) {
                        $scope.tasks = tasks._embedded.tasks;

                    }, function error(response) {
                        if (response.status == 404)
                            console.log("Failed to fetch tasks for sprint: " + $scope.sprint.name);
                    });
            }, function error(response) {
                if (response.status == 404)
                    console.log("Failed to fetch sprint by id: " + sprintId);
            });
    };

    // register controller
    app.controller('SprintController', ['$scope', '$resource', '$stateParams', 'SprintService', SprintController])
}());