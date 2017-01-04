package com.timeoverseer.model;

import com.timeoverseer.model.enums.Qualification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * The <code>Task</code> class represents a single step to complete
 * a certain {@link Sprint}.
 * A <code>Task</code> describes a required {@link Employee} proficiency level
 * {@link Qualification} to complete it, estimate time to complete the task,
 * time in which a task was actually completed, indicates whether it was assigned
 * to an {@link Employee} or not. May contain several subtasks, required to complete
 * it and keeps track of employees {@link Employee} working on it.
 */
@Entity
@Table(name = "task", schema = "overseer")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // if task removed -> sprint stays
    @ManyToOne
    @JoinColumn(name = "sprint_id", referencedColumnName = "id")
    private Sprint sprint;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_assigned", nullable = false)
    private boolean isAssigned;

    // required employee proficiency level
    @Column(name = "proficiency", nullable = false)
    private Qualification proficiency;

    @Column(name = "time_to_complete")
    private Long timeToComplete;

    @Column(name = "time_in_development")
    private Long timeInDevelopment;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tasks")
    private Set<Developer> developers;

    protected Task() {
    }

    public Task(Sprint sprint,
                String name,
                boolean isAssigned,
                Qualification proficiency,
                Long timeToComplete) {
        this.sprint = sprint;
        this.name = name;
        this.isAssigned = isAssigned;
        this.proficiency = proficiency;
        this.timeToComplete = timeToComplete;
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

    public Long getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(Long timeToCompleteTask) {
        this.timeToComplete = timeToCompleteTask;
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

    public Set<Developer> getEmployees() {
        return developers;
    }

    public void addDeveloper(Developer developer) {
        if (this.developers == null) {
            this.developers = new HashSet<>();
        }
        this.developers.add(developer);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sprint=" + sprint +
                ", timeToCompleteTask=" + timeToComplete +
                ", isAssigned=" + isAssigned +
                ", proficiency=" + proficiency +
                ", developers=" + developers +
                '}';
    }
}
