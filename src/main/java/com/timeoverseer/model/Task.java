package com.timeoverseer.model;

import com.timeoverseer.model.enums.Qualification;

import java.util.HashSet;
import java.util.Set;

/**
 * The <code>Task</code> class represents a single step to complete
 * a certain {@link Sprint}.
 * A <code>Task</code> describes a required {@link Employee} proficiency level
 * {@link Qualification} to complete it, estimate time to complete the task
 * {@link Estimate}, time in which a task was actually completed, indicates whether
 * it was assigned to an {@link Employee} or not. May contain several subtasks,
 * required to complete it and keeps track of employees {@link Employee} working on it.
 */
public class Task {

    private Long id;

    private String name;

    // task is assigned to specific sprint
    private Sprint sprint;

    private Estimate timeToCompleteTask;

    private Estimate developmentTime;

    private boolean isAssigned;

    // required employee proficiency level
    private Qualification proficiency;

    // subtasks of this tasks
    private Set<Task> subTasks;

    // different employees may work on the same task
    private Set<Employee> employees;

    public Task() {
    }

    public Task(String name, Sprint sprint, Estimate timeToCompleteTask, Qualification proficiency) {
        this.name = name;
        this.sprint = sprint;
        this.timeToCompleteTask = timeToCompleteTask;
        this.proficiency = proficiency;
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

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Estimate getTimeToCompleteTask() {
        return timeToCompleteTask;
    }

    public void setTimeToCompleteTask(Estimate timeToCompleteTask) {
        this.timeToCompleteTask = timeToCompleteTask;
    }

    public Estimate getDevelopmentTime() {
        return developmentTime;
    }

    public void setDevelopmentTime(Estimate developmentTime) {
        this.developmentTime = developmentTime;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    public Qualification getProficiency() {
        return proficiency;
    }

    public void setProficiency(Qualification proficiency) {
        this.proficiency = proficiency;
    }

    public Set<Task> getSubTasks() {
        return subTasks;
    }

    public void addSubTask(Task task) {
        if (this.subTasks == null) {
            this.subTasks = new HashSet<>();
        }
        this.subTasks.add(task);
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        if (this.employees == null) {
            this.employees = new HashSet<>();
        }
        this.employees.add(employee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (isAssigned != task.isAssigned) return false;
        if (id != null ? !id.equals(task.id) : task.id != null) return false;
        if (!name.equals(task.name)) return false;
        if (!sprint.equals(task.sprint)) return false;
        if (!timeToCompleteTask.equals(task.timeToCompleteTask)) return false;
        return proficiency == task.proficiency;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + sprint.hashCode();
        result = 31 * result + timeToCompleteTask.hashCode();
        result = 31 * result + (isAssigned ? 1 : 0);
        result = 31 * result + proficiency.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sprint=" + sprint +
                ", timeToCompleteTask=" + timeToCompleteTask +
                ", developmentTime=" + developmentTime +
                ", isAssigned=" + isAssigned +
                ", proficiency=" + proficiency +
                ", subTasks=" + subTasks +
                ", employees=" + employees +
                '}';
    }
}
