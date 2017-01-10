package com.timeoverseer.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The <code>Sprint</code> class represents specific {@link Project} phase.
 * A <code>Sprint</code> itself consists of several tasks {@link Task},
 * required to complete it.
 */
@Entity
@Table(name = "sprint", schema = "overseer")
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sprint", orphanRemoval = true)
    private Set<Task> tasks;

    protected Sprint() {
    }

    public Sprint(String name, Project project) {
        this.name = name;
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task... tasks) {
        if (this.tasks == null) {
            this.tasks = new HashSet<>();
        }
        Collections.addAll(this.tasks, tasks);
    }

    public void removeTask(Task... tasks) {
        for (Task t : tasks) {
            this.tasks.remove(t);
        }
    }

    @Override
    public String toString() {
        return "Sprint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", project=" + project.getDescription() +
                ", tasks=" + tasks +
                '}';
    }
}
