package com.timeoverseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.timeoverseer.model.enums.Qualification;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, of = "qualification")
@ToString(callSuper = true, exclude = "employer")
@Entity
@Table(name = "employee", schema = "overseer")
@PrimaryKeyJoinColumn(name = "emp_id", referencedColumnName = "id")
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee extends Person {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company employer;

    @Column(name = "qualification", nullable = false)
    @Enumerated(EnumType.STRING)
    private Qualification qualification;

    public Employee(String firstName,
                    String lastName,
                    String login,
                    String password,
                    Company employer, Qualification qualification) {
        super(firstName, lastName, login, password);
        this.employer = employer;
        this.qualification = qualification;
    }
}
