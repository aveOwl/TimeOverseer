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
            });
    };

    app.config(['$stateProvider', '$urlRouterProvider', RouteConfiguration]);
}());
