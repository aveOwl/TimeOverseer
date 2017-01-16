package com.timeoverseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = "businessInterests")
@ToString(callSuper = true, exclude = "companies")
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

    public Customer(String firstName, String lastName, String login, String password, String businessInterests) {
        super(firstName, lastName, login, password);
        this.businessInterests = businessInterests;
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

    public void addProject(Project project) {
        if (this.projects == null) {
            this.projects = new HashSet<>();
        }
        this.projects.add(project);
    }

    public void removeProject(Project project) {
        this.projects.remove(project);
    }
}
