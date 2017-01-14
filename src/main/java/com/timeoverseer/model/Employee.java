package com.timeoverseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.timeoverseer.model.enums.Qualification;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * The <code>Employee</code> class represents a person working in a
 * certain {@link Company} owning certain {@link Qualification}.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "employee", schema = "overseer")
@PrimaryKeyJoinColumn(name = "emp_id", referencedColumnName = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends Person {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @JsonBackReference
    private Company employer;

    @Column(name = "qualification", nullable = false)
    private Qualification qualification;

    protected Employee() {
    }

    public Employee(String firstName,
                    String lastName,
                    String login,
                    String password,
                    Company employer, Qualification qualification) {
        super(firstName, lastName, login, password);
        this.employer = employer;
        this.qualification = qualification;
    }

    public Company getEmployer() {
        return employer;
    }

    public void setEmployer(Company employer) {
        this.employer = employer;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employer=" + this.employer.getName() +
                ", qualification=" + this.qualification +
                "} " + super.toString();
    }
}
