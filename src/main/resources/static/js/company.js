// keeping function from global namespace
(function () {
    // define module
    var application = angular.module('application', []);

    // define controller
    var controller = function ($scope, $http) {

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
                url: '/companies/company',
                data: $scope.company
            });
        }
    };

    // register controller
    application.controller('controller', ["$scope", "$http", controller]);
}());
