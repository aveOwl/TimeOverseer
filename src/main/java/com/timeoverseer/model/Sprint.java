package com.timeoverseer.model;

import java.util.HashSet;
import java.util.Set;

/**
 * The <code>Sprint</code> class represents specific {@link Project} phase.
 * A <code>Sprint</code> itself consists of several tasks {@link Task},
 * required to complete it.
 */
public class Sprint {

    private Long id;

    private String name;

    // sprint is assigned to specific project
    private Project project;

    // sprint may have several tasks to complete it
    private Set<Task> tasks;

    public Sprint() {
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

        Sprint sprint = (Sprint) o;

        if (id != null ? !id.equals(sprint.id) : sprint.id != null) return false;
        if (!name.equals(sprint.name)) return false;
        return project.equals(sprint.project);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + project.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Sprint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", project=" + project +
                ", tasks=" + tasks +
                '}';
    }
}
