package com.timeoverseer.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * The <code>Customer</code> class represents a {@link Person} interested
 * in development of certain {@link Project} by certain {@link Company}.
 */
@Entity
@Table(name = "customer", schema = "overseer")
@PrimaryKeyJoinColumn(name = "person_id", referencedColumnName = "id")
public class Customer extends Person {

    @Column(name = "business_interests", nullable = false)
    private String businessInterests;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "customers")
    private Set<Company> companies;

    // if customer removed -> all projects removed as well
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

    public Set<Project> getProjects() {
        return projects;
    }

    public void addProject(Project project) {
        if (this.projects == null) {
            this.projects = new HashSet<>();
        }
        this.projects.add(project);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Customer customer = (Customer) o;

        return businessInterests.equals(customer.businessInterests);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + businessInterests.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "businessInterests=" + businessInterests +
                ", companies=" + companies +
                ", projects=" + projects +
                "} " + super.toString();
    }
}
