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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_assigned", nullable = false)
    private boolean isAssigned;

    @Column(name = "qualification", nullable = false)
    private Qualification qualification;

    @Column(name = "time_to_complete")
    private Long timeToComplete;

    @Column(name = "time_in_development")
    private Long timeInDevelopment;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tasks")
    private Set<Developer> developers;

    protected Task() {
    }

    public Task(String name,
                boolean isAssigned,
                Qualification qualification,
                Long timeToComplete,
                Sprint sprint) {
        this.name = name;
        this.isAssigned = isAssigned;
        this.qualification = qualification;
        this.timeToComplete = timeToComplete;
        this.sprint = sprint;
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

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public Long getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(Long timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

    public Long getTimeInDevelopment() {
        return timeInDevelopment;
    }

    public void setTimeInDevelopment(Long timeInDevelopment) {
        this.timeInDevelopment = timeInDevelopment;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
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
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isAssigned=" + isAssigned +
                ", qualification=" + qualification +
                ", timeToComplete=" + timeToComplete +
                ", timeInDevelopment=" + timeInDevelopment +
                ", sprint=" + sprint.getName() +
                ", developers=" + developers.stream()
                .map(d -> d.getFirstName() + " " + d.getLastName())
                .collect(Collectors.toList()) +
                '}';
    }
}
