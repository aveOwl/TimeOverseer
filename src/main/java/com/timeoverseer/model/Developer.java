package com.timeoverseer.model;

import com.timeoverseer.model.enums.Qualification;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * The <code>Developer</code> class represents a person responsible for
 * completing assigned set of tasks {@link Task}.
 */
@Entity
@Table(name = "developer", schema = "overseer")
@PrimaryKeyJoinColumn(name = "emp_id", referencedColumnName = "id")
public class Developer extends Employee {

    // if developer removed -> ProjectManager stays
    @ManyToOne
    @JoinColumn(name = "project_manager", referencedColumnName = "id")
    private ProjectManager projectManager;

    // if developer removed -> task stays
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "developer_task",
            joinColumns = {@JoinColumn(name = "developer_id", referencedColumnName = "id")},
            inverseJoinColumns ={@JoinColumn(name = "task_id", referencedColumnName = "id")})
    private Set<Task> tasks;

    protected Developer() {
    }

    public Developer(String firstName,
                     String lastName,
                     String login,
                     String password,
                     Company employer,
                     Qualification qualification,
                     ProjectManager projectManager) {
        super(firstName, lastName, login, password, employer, qualification);
        this.projectManager = projectManager;
    }

    public ProjectManager getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        if (this.tasks == null) {
            this.tasks = new HashSet<>();
        }
        this.tasks.add(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Developer developer = (Developer) o;

        return projectManager != null ? projectManager.equals(developer.projectManager) : developer.projectManager == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (projectManager != null ? projectManager.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "projectManager=" + projectManager +
                ", tasks=" + tasks +
                "} " + super.toString();
    }
}
