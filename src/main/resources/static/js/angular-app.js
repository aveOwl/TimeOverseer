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
                    url: "/companies/{id}",
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
                    var host = 'http://localhost:9090';
                    var link = response.data._links.self.href;
                    var result = link.replace(host, '/#');
                    $window.location.href = result;
                }
            )
        }
    };

    var CompanyController = function ($scope, $resource, $stateParams, $http, $location, $state) {
        $scope.info = true;

        $resource('/companies/:id', {id: '@id'}).get({id: $stateParams.id})
            .$promise
            .then(function (company) {
                $scope.company = company;

                // UPDATE
                $scope.update = function () {
                    $http({
                        method: 'PUT',
                        url: $location.url(),
                        data: $scope.company
                    }).success(
                        $scope.reloadRoute = function () {
                            $state.reload();
                        }
                    )
                };

                var employees = company._links.employees.href;

                $http.get(employees).then(function (response) {
                    $scope.developers = response.data._embedded.developers;
                    $scope.projectManagers = response.data._embedded.projectManagers;
                });
            });
    };

    // register controllers
    application.controller('NavigationController', ["$scope", "$state", NavigationController]);
    application.controller('HomeController', ["$scope", "$http", "$window", HomeController]);
    application.controller('CompanyController', ["$scope", "$resource", "$stateParams", "$http", "$location", "$state", CompanyController]);
}());