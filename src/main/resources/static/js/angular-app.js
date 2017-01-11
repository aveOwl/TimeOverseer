(function () {
    // define module
    var application = angular.module('overseer', ['ui.router', 'ngResource', '720kb.datepicker']);

    application.config(['$stateProvider', '$urlRouterProvider',
        function ($stateProvider, $urlRouterProvider) {
            $urlRouterProvider.otherwise('/');

            $stateProvider
                .state('home', {
                    url: "/",
                    activeTab: 'home',
                    controller: HomeController,
                    views: {
                        'front@': {
                            templateUrl: '/templates/home.html'
                        }
                    }
                })
                .state('companies', {
                    url: "/companies/{company}",
                    activeTab: 'company',
                    controller: CompanyController,
                    views: {
                        'company@': {
                            templateUrl: '/templates/company.html'
                        }
                    }
                });
        }]);

    var NavigationController = function ($scope, $state) {
        $scope.$on("$stateChangeSuccess", function () {
            $scope.activeTab = $state.current.activeTab;
        });
    };

    var HomeController = function ($scope, $http, $window) {
        $scope.company = {
            name: $scope.name,
            founded: $scope.founded,
            industry: $scope.industry,
            founders: $scope.founders,
            products: $scope.products
        };

        $scope.submit = function () {
            $http({
                method: 'POST',
                url: '/companies',
                data: $scope.company
            }).then(
                function (response) {
                    $window.location.href = '#/companies/' + response.data.id;
                }
            )
        }
    };

    var CompanyController = function ($scope, $resource, $stateParams) {
        $scope.company = $resource('/companies/:company').get({company: $stateParams.company});
    };

    // register controllers
    application.controller('NavigationController', ["$scope", "$state", NavigationController]);
    application.controller('HomeController', ["$scope", "$http", "$window", HomeController]);
    application.controller('CompanyController', ["$scope", "$resource", "$stateParams", CompanyController]);
}());