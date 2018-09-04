/**
 * Navigation Controller.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .controller('NavigationController', NavigationController);

    NavigationController.$inject = ['$scope', '$state'];

    function NavigationController($scope, $state) {
        $scope.$on("$stateChangeSuccess", function () {
            $scope.activeTab = $state.current.activeTab;
            $scope.showIntro = $scope.activeTab == 'home';
        });
    }
}());