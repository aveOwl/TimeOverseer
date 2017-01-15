package com.timeoverseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "developer", schema = "overseer")
@PrimaryKeyJoinColumn(name = "dev_id", referencedColumnName = "emp_id")
public class Developer extends Employee {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_manager")
    private ProjectManager projectManager;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "developer_task",
            joinColumns = {@JoinColumn(name = "developer_id")},
            inverseJoinColumns = {@JoinColumn(name = "task_id")})
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

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    private String projectManagerName() {
        return projectManager == null ? null :
                projectManager.getFirstName() + " " + projectManager.getLastName();
    }

    @Override
    public String toString() {
        return "Developer{" +
                "projectManager=" + this.projectManagerName() +
                ", tasks=" + this.tasks +
                "} " + super.toString();
    }
}
