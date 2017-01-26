/**
 * Angular Route
 */
(function () {
    'use strict';

    var app = angular.module('overseer');

    var RouteConfiguration = function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/');

        $stateProvider
            .state('home', {
                url: "/",
                activeTab: 'home',
                controller: 'HomeController',
                views: {
                    'home@': {
                        templateUrl: '/templates/home.html'
                    }
                }
            })
            .state('companies', {
                url: "/companies/{id}",
                activeTab: 'company',
                controller: 'CompanyController',
                views: {
                    'company@': {
                        templateUrl: '/templates/company.html'
                    }
                }
            })
            .state('customers', {
                url: "/customers/{id}",
                activeTab: 'customer',
                controller: 'CustomerController',
                views: {
                    'customer@': {
                        templateUrl: '/templates/customer.html'
                    }
                }
            })
            .state('projects', {
                url: "/projects/{id}",
                activeTab: 'project',
                controller: 'ProjectController',
                views: {
                    'project@': {
                        templateUrl: '/templates/project.html'
                    }
                }
            })
            .state('sprints', {
                url: "/sprints/{id}",
                activeTab: 'sprint',
                controller: 'SprintController',
                views: {
                    'sprint@': {
                        templateUrl: '/templates/sprint.html'
                    }
                }
            })
            .state('developers', {
                url: "/developers/{id}",
                activeTab: 'developer',
                controller: 'DeveloperController',
                views: {
                    'developer@': {
                        templateUrl: '/templates/developer.html'
                    }
                }
            })
            .state('projectManagers', {
                url: "/projectManagers/{id}",
                activeTab: 'projectManager',
                controller: 'ProjectManagerController',
                views: {
                    'projectManager@': {
                        templateUrl: '/templates/projectManager.html'
                    }
                }
            })
            .state('tasks', {
                url: "/tasks/{id}",
                activeTab: 'task',
                controller: 'TaskController',
                views: {
                    'task@': {
                        templateUrl: '/templates/task.html'
                    }
                }
            });
    };

    app.config(['$stateProvider', '$urlRouterProvider', RouteConfiguration]);
}());
