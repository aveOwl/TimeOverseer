package com.timeoverseer.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * The <code>Project</code> class represents an enterprise, developed by
 * certain {@link Company}, to achieve a particular aim, requested by a {@link Customer}.
 * A <code>Project</code> has a Project Manager {@link Employee} assigned to it
 * and a set of sprints {@link Sprint}, required to complete it.
 */
@Entity
@Table(name = "project", schema = "overseer")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // if project removed -> ProjectManager stays
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "project")
    private ProjectManager projectManager;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    // if project removed -> all sprints removed as well
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
    private Set<Sprint> sprints;

    // if project removed -> customer stays
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    protected Project() {
    }

    public Project(ProjectManager projectManager,
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

    public ProjectManager getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(ProjectManager projectManager) {
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
