/**
 * Navigation Controller
 */
(function () {
    'use strict';

    // fetch app
    var app = angular.module('overseer');

    // define controller
    var NavigationController = function ($scope, $state) {
        $scope.$on("$stateChangeSuccess", function () {
            $scope.activeTab = $state.current.activeTab;
            $scope.showIntro = $scope.activeTab == 'home';
        });
    };

    // register controller
    app.controller('NavigationController', ["$scope", "$state", NavigationController]);
}());