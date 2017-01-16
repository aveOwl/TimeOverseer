package com.timeoverseer.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.timeoverseer.model.enums.Qualification;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, isGetterVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = {"name", "qualification", "isAssigned"})
@ToString(callSuper = true, exclude = {"sprint", "developers"})
@Entity
@Table(name = "task", schema = "overseer")
public class Task extends AbstractEntity {

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

    public void addDeveloper(Developer developer) {
        if (this.developers == null) {
            this.developers = new HashSet<>();
        }
        this.developers.add(developer);
    }

    public void removeDeveloper(Developer developer) {
        this.developers.remove(developer);
    }
}
