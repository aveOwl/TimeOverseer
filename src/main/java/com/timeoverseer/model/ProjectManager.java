package com.timeoverseer.model;

import com.timeoverseer.model.enums.Qualification;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The <code>ProjectManager</code> class represents a person in charge of specific
 * {@link Project}, responsible for initiating sprints {@link Sprint}, tasks {@link Task}
 * and assigning tasks {@link Task} to developers {@link Developer}.
 */
@Entity
@Table(name = "project_manager", schema = "overseer")
@PrimaryKeyJoinColumn(name = "pm_id", referencedColumnName = "emp_id")
public class ProjectManager extends Employee {

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "projectManager")
    private Set<Developer> developers;

    public ProjectManager() {
    }

    public ProjectManager(String firstName,
                          String lastName,
                          String login,
                          String password,
                          Company employer,
                          Qualification qualification,
                          Project project) {
        super(firstName, lastName, login, password, employer, qualification);
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void addDeveloper(Developer... developers) {
        if (this.developers == null) {
            this.developers = new HashSet<>();
        }
        Collections.addAll(this.developers, developers);
    }

    public void removeDeveloper(Developer... developers) {
        for (Developer dev : developers) {
            this.developers.remove(dev);
        }
    }

    @Override
    public String toString() {
        return "ProjectManager{" +
                "project=" + project +
                ", developers=" + developers +
                "} " + super.toString();
    }
}
