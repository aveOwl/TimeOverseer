package com.timeoverseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.timeoverseer.util.LocalDateDeserializer;
import com.timeoverseer.util.LocalDateSerializer;

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
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name = "project", schema = "overseer")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_date", nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate endDate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_manager")
    private ProjectManager projectManager;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true)
    private Set<Sprint> sprints;

    protected Project() {
    }

    public Project(String description,
                   LocalDate startDate,
                   LocalDate endDate,
                   Customer customer,
                   ProjectManager projectManager) {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customer = customer;
        this.projectManager = projectManager;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ProjectManager getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
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

    public void removeSprint(Sprint sprint) {
        this.sprints.remove(sprint);
    }

    private String customerName() {
        return this.customer.getFirstName() + " " + this.customer.getLastName();
    }

    private String projectManagerName() {
        return this.projectManager == null ? null :
                this.projectManager.getFirstName() + " " + this.projectManager.getLastName();
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + this.id +
                ", description='" + this.description +
                ", startDate=" + this.startDate +
                ", endDate=" + this.endDate +
                ", customer=" + this.customerName() +
                ", projectManager=" + this.projectManagerName() +
                ", sprints=" + this.sprints +
                '}';
    }
}
