/**
 * Angular Route
 */
(function () {
    'use strict';

    var app = angular.module('overseer');

    var RouteConfiguration = function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.when('/', '/overseer')
            .otherwise('/overseer');

        $stateProvider
            .state('home', {
                url: "/overseer",
                activeTab: 'home',
                controller: 'HomeController',
                views: {
                    'home@': {
                        templateUrl: '/templates/home.html'
                    }
                }
            })
            .state('companies', {
                url: "/overseer/companies/{id}",
                onEnter: function () {
                    $('html, body').animate({
                        scrollTop: $("#company")
                    })
                },
                activeTab: 'company',
                controller: 'CompanyController',
                views: {
                    'company@': {
                        templateUrl: '/templates/company.html'
                    }
                }
            })
            .state('customers', {
                url: "/overseer/customers/{id}",
                onEnter: function () {
                    $('html, body').animate({
                        scrollTop: $("#customer")
                    })
                },
                activeTab: 'customer',
                controller: 'CustomerController',
                views: {
                    'customer@': {
                        templateUrl: '/templates/customer.html'
                    }
                }
            })
            .state('projects', {
                url: "/overseer/projects/{id}",
                onEnter: function () {
                    $('html, body').animate({
                        scrollTop: $("#project")
                    })
                },
                activeTab: 'project',
                controller: 'ProjectController',
                views: {
                    'project@': {
                        templateUrl: '/templates/project.html'
                    }
                }
            })
            .state('sprints', {
                url: "/overseer/sprints/{id}",
                onEnter: function () {
                    $('html, body').animate({
                        scrollTop: $("#sprint")
                    })
                },
                activeTab: 'sprint',
                controller: 'SprintController',
                views: {
                    'sprint@': {
                        templateUrl: '/templates/sprint.html'
                    }
                }
            })
            .state('developers', {
                url: "/overseer/developers/{id}",
                onEnter: function () {
                    $('html, body').animate({
                        scrollTop: $("#developer")
                    })
                },
                activeTab: 'developer',
                controller: 'DeveloperController',
                views: {
                    'developer@': {
                        templateUrl: '/templates/developer.html'
                    }
                }
            })
            .state('projectManagers', {
                url: "/overseer/projectManagers/{id}",
                onEnter: function () {
                    $('html, body').animate({
                        scrollTop: $("#projectManager")
                    })
                },
                activeTab: 'projectManager',
                controller: 'ProjectManagerController',
                views: {
                    'projectManager@': {
                        templateUrl: '/templates/projectManager.html'
                    }
                }
            })
            .state('tasks', {
                url: "/overseer/tasks/{id}",
                onEnter: function () {
                    $('html, body').animate({
                        scrollTop: $("#task")
                    })
                },
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
