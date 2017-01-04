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
import java.util.HashSet;
import java.util.Set;

/**
 * The <code>ProjectManager</code> class represents a person in charge of specific
 * {@link Project}, responsible for initiating sprints {@link Sprint}, tasks {@link Task}
 * and assigning tasks {@link Task} to developers {@link Developer}.
 */
@Entity
@Table(name = "project_manager", schema = "overseer")
@PrimaryKeyJoinColumn(name = "emp_id", referencedColumnName = "id")
public class ProjectManager extends Employee {

    // if ProjectManager removed -> project stays
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    // if ProjectManager removed -> Developer stays
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

    public void addEmployee(Developer developer) {
        if (this.developers == null) {
            this.developers = new HashSet<>();
        }
        this.developers.add(developer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ProjectManager that = (ProjectManager) o;

        return project != null ? project.equals(that.project) : that.project == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (project != null ? project.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProjectManager{" +
                "project=" + project +
                ", employees=" + developers +
                "} " + super.toString();
    }
}
