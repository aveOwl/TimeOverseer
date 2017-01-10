package com.timeoverseer.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The <code>Customer</code> class represents a {@link Person} interested
 * in development of certain {@link Project} by certain {@link Company}.
 */
@Entity
@Table(name = "customer", schema = "overseer")
@PrimaryKeyJoinColumn(name = "cust_id", referencedColumnName = "id")
public class Customer extends Person {

    @Column(name = "business_interests", nullable = false)
    private String businessInterests;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "customers")
    private Set<Company> companies;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "customer", orphanRemoval = true)
    private Set<Project> projects;

    protected Customer() {
    }

    public Customer(String firstName, String lastName, String login, String password, String businessInterests) {
        super(firstName, lastName, login, password);
        this.businessInterests = businessInterests;
    }

    public String getBusinessInterests() {
        return businessInterests;
    }

    public void setBusinessInterests(String businessInterests) {
        this.businessInterests = businessInterests;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public void addCompany(Company... companies) {
        if (this.companies == null) {
            this.companies = new HashSet<>();
        }
        Collections.addAll(this.companies, companies);
    }

    public void removeCompany(Company... companies) {
        for (Company c : companies) {
            this.companies.remove(c);
        }
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void addProject(Project... projects) {
        if (this.projects == null) {
            this.projects = new HashSet<>();
        }
        Collections.addAll(this.projects, projects);
    }

    public void removeProject(Project... projects) {
        for (Project p : projects) {
            this.projects.remove(p);
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "businessInterests='" + businessInterests + '\'' +
                ", companies=" + companies.stream()
                .map(Company::getName)
                .collect(Collectors.toList()) +
                ", projects=" + projects +
                "} " + super.toString();
    }
}
