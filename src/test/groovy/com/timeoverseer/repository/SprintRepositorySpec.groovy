package com.timeoverseer.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = SprintRepository)
class SprintRepositorySpec extends AbstractRepositorySpec {
    @Autowired
    SprintRepository sprintRepository

    void setup() {
        customer1.addProject(project)
        project.customer = customer1

        project.addSprint(sprint)
        sprint.project = project

        entityManager.persistAndFlush(customer1)
    }

    def "should persist sprint"() {
        when:
        def fetchedSprint = sprintRepository.findByName(sprint.name)

        then:
        fetchedSprint.project == project
    }

    def "should delete sprint"() {
        given:
        project.removeSprint(sprint)

        when:
        sprintRepository.delete(sprint)

        then:
        sprintRepository.findByName(sprint.name) == null
    }

    def "should update sprint"() {
        given:
        sprint.name = "Best Phase"

        when:
        def updatedSprint = sprintRepository.save(sprint)

        then:
        updatedSprint.name.contains("Best")
    }
}
