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

        var removeList = [];

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
         * Adds employee to company.
         */
        companyService.addEmployee = function (employee) {
            switch (employee.position) {
                case 'Developer':
                    addDeveloper(employee);
                    break;
                case 'ProjectManager':
                    addProjectManager(employee);
                    break;
                default:
                    $log.error("Specified position is not supported");
            }
        };

        /**
         * Adds developer to company.
         */
        function addDeveloper(employee) {
            DeveloperService.perform().save(employee).$promise
                .then(function (dev) {
                    $log.debug("Saved developer for company", dev);
                    developers.push(dev);

                }, function (error) {
                    $log.error("Failed to add developer to company", error);
                });
        }

        /**
         * Adds projectManager to company.
         */
        function addProjectManager(employee) {
            ProjectManagerService.perform().save(employee).$promise
                .then(function (pm) {
                    $log.debug("Saved ProjectManager for company", pm);
                    projectManagers.push(pm);

                }, function (error) {
                    $log.error("Failed to add projectManager to company", error);
                });
        }

        /**
         * Returns true if company has no developers or projectManagers associated with it,
         * false otherwise.
         */
        companyService.isEmployeesPresent = function (employees) {
            return typeof employees._embedded.employees != 'undefined'
        };

        /**
         * Adds employee to remove list, if employee already in remove list
         * removes it from the list.
         */
        companyService.addToRemoveList = function (employee) {
            if (removeList.includes(employee)) {
                $log.debug("Removing employee from remove list", employee);
                removeFromArray(employee, removeList);
            } else {
                $log.debug("Preparing to delete employee", employee);
                removeList.push(employee);
            }
        };

        /**
         * Clears remove lists.
         */
        companyService.removeEmployees = function () {
            $log.debug("Removing employees", removeList);
            removeList.forEach(function (employee) {
                switch (employee.position) {
                    case 'Developer':
                        DeveloperService.perform().remove({id: employee.id});
                        break;
                    case 'ProjectManager':
                        ProjectManagerService.perform().remove({id: employee.id});
                        break;
                    default:
                        $log.error("Specified position is not supported");
                }
            });
            removeList = [];
        };

        /**
         * Removes from provided employees list all employees present in remove list.
         * @param employees
         */
        companyService.exclude = function (employees) {
            return employees.filter(function (e) {
                return !removeList.includes(e);
            });
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