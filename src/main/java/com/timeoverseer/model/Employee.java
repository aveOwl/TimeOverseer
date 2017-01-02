package com.timeoverseer.model;

import com.timeoverseer.model.enums.Qualification;
import com.timeoverseer.model.enums.Role;

import java.util.HashSet;
import java.util.Set;

/**
 * The <code>Employee</code> class represents a person working in a
 * certain {@link Company} on a certain {@link Project}.
 * <p>
 * <code>Employee</code> may work as regular Employee or as Project Manager, how its
 * described in {@link Role} enum.
 * <p>
 * <code>Employee</code> have particular level of proficiency {@link Qualification}
 * and responsible for completing assigned set of tasks {@link Task}.
 * <p>
 * ProjectManager is in charge of specific {@link Project}, responsible for
 * initiating sprints {@link Sprint}, tasks {@link Task} and assigning tasks {@link Task}
 * to employees {@link Employee}.
 */
public class Employee extends Person {

    private Company employer;

    private Role role;

    private Project project;

    private Employee projectManager;

    private Estimate timeToFinishProject;

    // employee proficiency level
    private Qualification qualification;

    private Set<Employee> employees;

    public Employee() {
    }

    public Employee(String firstName,
                    String lastName,
                    String login,
                    String password,
                    Company employer, Role role, Qualification qualification) {
        super(firstName, lastName, login, password);
        this.employer = employer;
        this.role = role;
        this.qualification = qualification;
    }

    public Company getEmployer() {
        return employer;
    }

    public void setEmployer(Company employer) {
        this.employer = employer;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Employee getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(Employee projectManager) {
        this.projectManager = projectManager;
    }

    public Estimate getTimeToFinishProject() {
        return timeToFinishProject;
    }

    public void setTimeToFinishProject(Estimate timeToFinishProject) {
        this.timeToFinishProject = timeToFinishProject;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        if (this.employees == null) {
            this.employees = new HashSet<>();
        }
        this.employees.add(employee);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employer=" + employer +
                ", role=" + role +
                ", projectManager=" + projectManager +
                ", timeToFinishProject=" + timeToFinishProject +
                ", qualification=" + qualification +
                ", employees=" + employees +
                ", project=" + project +
                "} " + super.toString();
    }
}
