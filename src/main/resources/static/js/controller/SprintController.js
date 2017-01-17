/**
 * Project controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var SprintController = function ($scope, $resource, $stateParams, $http, $location) {
        $scope.sprintInfo = true;
        $scope.tasksInfo = true;

        $resource('/sprints/:id', {id: '@id'}).get({id: $stateParams.id})
            .$promise
            .then(function (sprint) {
                $scope.sprint = sprint;

                $scope.updateSprint = function () {
                    $http({
                        method: 'PUT',
                        url: $location.url(),
                        data: $scope.sprint
                    });
                };

                // PROJECT
                var projectLink = sprint._links.project.href;
                $http.get(projectLink).then(function (response) {
                    $scope.project = response.data;
                });

                // TASKS
                var tasksLink = sprint._links.tasks.href;
                $http.get(tasksLink).then(function (response) {
                    $scope.tasks = response.data._embedded.tasks;
                });
            });
    };

    // register controller
    app.controller('SprintController', ['$scope', '$resource', '$stateParams', '$http', '$location', SprintController])
}());