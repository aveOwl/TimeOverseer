/**
 * Home Controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
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

        /**
         * Redirects to created resource.
         */
        var redirect = function (response) {
            var host = 'http://localhost:9090';
            var link = response.data._links.self.href;
            return link.replace(host, '/#');
        }
    };

    // register controller
    app.controller('HomeController', ['$scope', '$http', '$window', HomeController]);
}());