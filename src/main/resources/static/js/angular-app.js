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
                        'home@': {
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
                })
                .state('customers', {
                    url: "/customers/{id}",
                    activeTab: 'customer',
                    controller: CustomerController,
                    views: {
                        'customer@': {
                            templateUrl: '/templates/customer.html'
                        }
                    }
                });
        }]);

    var NavigationController = function ($scope, $state) {
        $scope.$on("$stateChangeSuccess", function () {
            $scope.activeTab = $state.current.activeTab;
            $scope.showIntro = $scope.activeTab == 'home';
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

        $scope.customer = {
            firstName: $scope.firstName,
            lastName: $scope.lastName,
            login: $scope.login,
            password: $scope.password,
            businessInterests: $scope.businessInterests
        };

        $scope.submitCompany = function () {
            $http({
                method: 'POST',
                url: '/companies',
                data: $scope.company
            }).then(
                function (response) {
                    $window.location.href = redirect(response);
                }
            )
        };

        $scope.submitCustomer = function () {
            $http({
                method: 'POST',
                url: '/customers',
                data: $scope.customer
            }).then(
                function (response) {
                    $window.location.href = redirect(response);
                }
            )
        };

        var redirect = function (response) {
            var host = 'http://localhost:9090';
            var link = response.data._links.self.href;
            return link.replace(host, '/#');
        }
    };

    var CompanyController = function ($scope, $resource, $stateParams, $http, $location, $state) {
        $scope.companyInfo = true;

        $resource('/companies/:id', {id: '@id'}).get({id: $stateParams.id})
            .$promise
            .then(function (company) {
                $scope.company = company;

                // UPDATE
                $scope.updateCompany = function () {
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

    var CustomerController = function ($scope, $resource, $stateParams, $http, $location, $state) {
        $scope.customerInfo = true;

        $resource('/customers/:id', {id: '@id'}).get({id: $stateParams.id})
            .$promise
            .then(function (customer) {
                $scope.customer = customer;

                // UPDATE
                $scope.updateCustomer = function () {
                    $http({
                        method: 'PUT',
                        url: $location.url(),
                        data: $scope.customer
                    }).success(
                        $scope.reloadRoute = function () {
                            $state.reload();
                        }
                    )
                };
            })
    };

    // register controllers
    application.controller('NavigationController', ["$scope", "$state", NavigationController]);
    application.controller('HomeController', ["$scope", "$http", "$window", HomeController]);
    application.controller('CompanyController', ["$scope", "$resource", "$stateParams", "$http", "$location", "$state", CompanyController]);
    application.controller('CustomerController', ["$scope", "$resource", "$stateParams", "$http", "$location", "$state", CustomerController]);
}());