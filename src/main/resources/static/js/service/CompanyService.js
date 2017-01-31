/**
 * Company Service.
 */
(function () {
    'use strict';

    angular.module('overseer')
        .factory('CompanyService', CompanyService);

    CompanyService.$inject = ['$resource', '$log', 'DeveloperService', 'ProjectManagerService'];
    function CompanyService($resource, $log, DeveloperService, ProjectManagerService) {
        var companyService = {};
        var developers = [];
        var projectManagers = [];

        var devToDelete = [];
        var pmToDelete = [];

        // Basic CRUD operations
        companyService.perform = function () {
            return $resource('/companies/:id', null,
                {
                    'update': {method: 'PUT'}
                });
        };

        /**
         * Returns set of employees associated with this company.
         */
        companyService.getEmployees = function (company) {
            return $resource(company._links.employees.href).get();
        };

        /**
         * Returns set of developers associated with this company.
         */
        companyService.getDevelopers = function (employees) {
            developers = employees._embedded.developers;
            if (typeof developers === 'undefined') {
                developers = [];
            }
            return developers;
        };

        /**
         * Returns set of projectManagers associated with this company.
         */
        companyService.getProjectManagers = function (employees) {
            projectManagers = employees._embedded.projectManagers;
            if (typeof projectManagers === 'undefined') {
                projectManagers = [];
            }
            return projectManagers;
        };

        /**
         * Adds Developer to company.
         */
        companyService.addDeveloper = function (dev) {
            DeveloperService.perform().save(dev).$promise
                .then(function (savedDev) {
                    $log.debug("Saved developer for company", savedDev);
                    developers.push(savedDev);

                }, function (error) {
                    $log.error("Failed to add developer to company", error);
                });
        };

        /**
         * Adds ProjectManager to company.
         */
        companyService.addProjectManager = function (pm) {
            ProjectManagerService.perform().save(pm).$promise
                .then(function (savedPm) {
                    $log.debug("Saved ProjectManager for company", savedPm);
                    projectManagers.push(savedPm);

                }, function (error) {
                    $log.error("Failed to add projectManager to company", error);
                });

        };

        /**
         * Returns true if company has no developers or projectManagers associated with it,
         * false otherwise.
         */
        companyService.isEmployeesPresent = function (employees) {
            return typeof employees._embedded.employees != 'undefined'
        };

        /**
         * Clears projectManager and developer remove lists.
         *
         * @param developers set of developers to remove marked developer from.
         * @param projectManagers set of projectManagers to remove marked projectManager from.
         */
        companyService.removeEmployees = function (developers, projectManagers) {
            $log.debug("Removing developers", devToDelete);
            devToDelete.forEach(function (dev) {
                removeFromArray(dev, developers);
                DeveloperService.perform().remove({id: dev.id});
            });
            $log.debug("Removing projectManagers", pmToDelete);
            pmToDelete.forEach(function (pm) {
                removeFromArray(pm, projectManagers);
                ProjectManagerService.perform().remove({id: pm.id});
            });
        };

        /**
         * Adds single developer from provided developers set
         * to remove list based on provided index.
         *
         * @param developers set of developers.
         * @param index index to identify developer by.
         */
        companyService.checkDev = function (developers, index) {
            var dev = developers[index];
            if (devToDelete.includes(dev)) {
                $log.debug("Removing developer from delete list", dev);
                removeFromArray(dev, pmToDelete);
            } else {
                $log.debug("Preparing to delete developer", dev);
                devToDelete.push(dev);
            }
        };

        /**
         * Adds single projectManager from provided projectManagers set
         * to remove list based on provided index.
         *
         * @param projectManagers set of projectManagers.
         * @param index index to identify projectManager by.
         */
        companyService.checkPm = function (projectManagers, index) {
            var pm = projectManagers[index];
            if (pmToDelete.includes(pm)) {
                $log.debug("Removing projectManager from delete list", pm);
                removeFromArray(pm, pmToDelete);
            } else {
                $log.debug("Preparing to delete projectManager", pm);
                pmToDelete.push(pm);
            }
        };

        /**
         * Returns resource of customers associated with this company.
         * @param company
         */
        companyService.getCustomers = function (company) {
            return $resource(company._links.customers.href).get();
        };

        /**
         * Removes item from given array.
         *
         * @param item item to remove.
         * @param array array containing item.
         */
        function removeFromArray(item, array) {
            var idx = array.indexOf(item);
            array.splice(idx, 1);
        }

        return companyService;
    }
}());