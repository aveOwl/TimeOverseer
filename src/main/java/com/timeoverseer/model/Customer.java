package com.timeoverseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The <code>Customer</code> class represents a {@link Person} interested
 * in development of certain {@link Project} by certain {@link Company}.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    public void addCompany(Company company) {
        if (this.companies == null) {
            this.companies = new HashSet<>();
        }
        this.companies.add(company);
    }

    public void removeCompany(Company company) {
        this.companies.remove(company);
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void addProject(Project project) {
        if (this.projects == null) {
            this.projects = new HashSet<>();
        }
        this.projects.add(project);
    }

    public void removeProject(Project project) {
        this.projects.remove(project);
    }

    private List<String> companiesName() {
        return this.companies.stream()
                .map(Company::getName)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "businessInterests='" + this.businessInterests +
                ", companies=" + this.companiesName() +
                ", projects=" + this.projects +
                "} " + super.toString();
    }
}
