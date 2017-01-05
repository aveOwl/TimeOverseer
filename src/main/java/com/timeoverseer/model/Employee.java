package com.timeoverseer.model;

import com.timeoverseer.model.enums.Qualification;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Entity
@Table(name = "employee", schema = "overseer")
@PrimaryKeyJoinColumn(name = "emp_id", referencedColumnName = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends Person {

    @ManyToOne
    @JoinColumn(name = "company_id")
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
                "employer=" + employer +
                ", qualification=" + qualification +
                "} " + super.toString();
    }
}
