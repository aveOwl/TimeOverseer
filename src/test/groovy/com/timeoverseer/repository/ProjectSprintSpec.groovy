package com.timeoverseer.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [ProjectRepository, SprintRepository])
class ProjectSprintSpec extends AbstractRepositorySpec {
    @Autowired
    ProjectRepository projectRepository
    @Autowired
    SprintRepository sprintRepository

    void setup() {
        customer1.addProject(project)
        project.addSprint(sprint)

        project.customer = customer1
        sprint.project = project

        entityManager.persistAndFlush(customer1)
    }

    def "should save sprint when project saved"() {
        expect:
        sprintRepository.findByName(sprint.name) != null
    }

    def "should delete sprint if project deleted"() {
        given:
        project.removeSprint(sprint)
        sprint.project == null

        when:
        projectRepository.delete(project)

        then:
        sprintRepository.findByName(sprint.name) == null
    }

    def "should not delete project if sprint deleted"() {
        given:
        project.removeSprint(sprint)

        when:
        sprintRepository.delete(sprint)

        then:
        projectRepository.findByCustomer(customer1) != null
    }
}
