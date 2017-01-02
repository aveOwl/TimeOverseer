package com.timeoverseer.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * The <code>Project</code> class represents an enterprise, developed by
 * certain {@link Company}, to achieve a particular aim, requested by a {@link Customer}.
 * A <code>Project</code> has a Project Manager {@link Employee} assigned to it
 * and a set of sprints {@link Sprint}, required to complete it.
 */
public class Project {

    private Long id;

    private Employee projectManager;

    private String description;

    private LocalDate startDate;
    private LocalDate endDate;

    // project contains several sprints
    private Set<Sprint> sprints;

    // project is requested by specific customer
    private Customer customer;

    public Project() {
    }

    public Project(Employee projectManager,
                   String description,
                   LocalDate startDate,
                   LocalDate endDate,
                   Customer customer) {
        this.projectManager = projectManager;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(Employee projectManager) {
        this.projectManager = projectManager;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Sprint> getSprints() {
        return sprints;
    }

    public void addSprint(Sprint sprint) {
        if (this.sprints == null) {
            this.sprints = new HashSet<>();
        }
        this.sprints.add(sprint);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (id != null ? !id.equals(project.id) : project.id != null) return false;
        if (!projectManager.equals(project.projectManager)) return false;
        if (!description.equals(project.description)) return false;
        if (!startDate.equals(project.startDate)) return false;
        if (!endDate.equals(project.endDate)) return false;
        return customer.equals(project.customer);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + projectManager.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + customer.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectManager=" + projectManager +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", sprints=" + sprints +
                ", customer=" + customer +
                '}';
    }
}
