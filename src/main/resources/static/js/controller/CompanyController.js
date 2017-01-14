/**
 * Company controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var CompanyController = function ($scope, $resource, $stateParams, $http, $location, $state) {
        $scope.companyInfo = true;

        $resource('/companies/:id', {id: '@id'}).get({id: $stateParams.id})
            .$promise
            .then(function (company) {
                $scope.company = company;

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

    // register controller
    app.controller('CompanyController', ['$scope', '$resource', '$stateParams', '$http', '$location', '$state', CompanyController])
}());