package com.timeoverseer.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * The <code>Administrator</code> class represents a {@link Person} in charge
 * of registering instances of {@link Employee}, {@link Customer}, {@link Project}
 * and assigning project managers {@link ProjectManager} to developers {@link Developer}.
 */
@Entity
@Table(name = "administrator", schema = "overseer")
@PrimaryKeyJoinColumn(name = "admin_id", referencedColumnName = "id")
public class Administrator extends Person {

    protected Administrator() {
    }

    public Administrator(String firstName, String lastName, String login, String password) {
        super(firstName, lastName, login, password);
    }

    @Override
    public String toString() {
        return "Administrator{} " + super.toString();
    }
}
