/**
 * Customer controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var CustomerController = function ($scope, $resource, $stateParams, $http, $location, $state) {
        $scope.customerInfo = true;

        $resource('/customers/:id', {id: '@id'}).get({id: $stateParams.id})
            .$promise
            .then(function (customer) {
                $scope.customer = customer;

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

    // register controller
    app.controller('CustomerController', ['$scope', '$resource', '$stateParams', '$http', '$location', '$state', CustomerController])
}());