(function () {
    // define module
    var application = angular.module('overseer', ['ngRoute', 'ngResource']);

    application.config(['$routeProvider',
        function ($routeProvider) {
            $routeProvider
                .when('/', {
                    templateUrl: '/templates/home.html',
                    controller: HomeController,
                    activeTab: 'home'
                })
                .when('/companies/:id', {
                    templateUrl: '/templates/company.html',
                    controller: CompanyController,
                    activeTab: 'company'
                })
                .otherwise({
                    redirectTo: '/'
                });
        }]);

    var NavigationController = function ($scope, $route) {
        $scope.$on("$routeChangeSuccess", function () {
            $scope.activeTab = $route.current.activeTab;
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
                    $window.location.href = '#/companies/' + response.data.id + "/#company";
                }
            )
        }
    };

    var CompanyController = function ($scope, $resource, $routeParams) {
        $scope.company = $resource('/companies/:id').get({id: $routeParams.id});

        console.log($resource('/companies/:id').get({id: $routeParams.id}));
    };

    // register controllers
    application.controller('NavigationController', ["$scope", "$route", NavigationController]);
    application.controller('HomeController', ["$scope", "$http", "$window", HomeController]);
    application.controller('CompanyController', ["$scope", "$resource", "$routeParams", CompanyController]);
}());