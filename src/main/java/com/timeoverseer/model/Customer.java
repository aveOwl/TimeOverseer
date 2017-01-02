package com.timeoverseer.model;

import java.util.HashSet;
import java.util.Set;

/**
 * The <code>Customer</code> class represents a person interested in development
 * of certain {@link Project} by certain {@link Company}.
 */
public class Customer extends Person {

    private String businessInterests;

    // companies to develop projects
    private Set<Company> companies;

    // customer may have several projects
    private Set<Project> projects;

    public Customer() {
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
