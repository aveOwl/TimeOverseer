package com.timeoverseer.model.enums;

public enum Role {
    PROJECT_MANAGER,
    EMPLOYEE;

    @Override
    public String toString() {
        return "Role{" + this.name().toLowerCase() + "}";
    }
}
