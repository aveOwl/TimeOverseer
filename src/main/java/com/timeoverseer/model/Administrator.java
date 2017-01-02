package com.timeoverseer.model;

/**
 * The <code>Administrator</code> class represents a person in charge
 * of registering instances of {@link Employee}, {@link Customer}, {@link Project}
 * and assigning Project Managers {@link Employee}.
 */
public class Administrator extends Person {

    public Administrator() {
    }

    public Administrator(String firstName, String lastName, String login, String password) {
        super(firstName, lastName, login, password);
    }

    @Override
    public String toString() {
        return "Administrator{} " + super.toString();
    }
}
