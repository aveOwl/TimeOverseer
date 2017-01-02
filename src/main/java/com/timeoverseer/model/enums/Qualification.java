package com.timeoverseer.model.enums;

import com.timeoverseer.model.Employee;

/**
 * The <code>Qualification</code> enum represents {@link Employee} level of proficiency.
 */
public enum Qualification {
    TRAINEE,
    JUNIOR,
    MIDDLE,
    SENIOR,
    ARCHITECT;

    @Override
    public String toString() {
        return "Qualification{" + this.name().toLowerCase() + "}";
    }
}
