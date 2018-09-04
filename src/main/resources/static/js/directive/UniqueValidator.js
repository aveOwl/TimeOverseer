/**
 * Login Validator.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .directive('uniqueValidator', uniqueValidator);

    uniqueValidator.$inject = ['$http', '$q', '$timeout', '$log'];

    function uniqueValidator($http, $q, $timeout, $log) {
        return {
            restrict: 'A',
            require: 'ngModel',
            link: function (scope, element, attr, ngModel) {
                ngModel.$asyncValidators.login = function (modelValue, viewValue) {
                    var deferred = $q.defer(),
                        currentValue = modelValue || viewValue;

                    $log.debug("Checking login:", currentValue);
                    $timeout(function () {
                        $http.post('/checkUnique/', currentValue)
                            .then(function (response) {
                                if (response.data.status) {
                                    deferred.resolve();
                                } else {
                                    deferred.reject();
                                }
                            })
                    }, 2000);

                    return deferred.promise;
                };
            }
        };
    }
}());