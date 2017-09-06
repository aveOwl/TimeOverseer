package com.timeoverseer.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import static com.timeoverseer.model.enums.Qualification.MIDDLE

@ContextConfiguration(classes = ProjectManagerRepository)
class ProjectManagerRepositorySpec extends AbstractRepositorySpec {
    @Autowired
    ProjectManagerRepository projectManagerRepository

    void setup() {
        company.addEmployee(projectManager)

        entityManager.persistAndFlush(company)
    }

    def "should persist project manager"() {
        when:
        def fetchedProjectManager = projectManagerRepository.findByEmployer(company)

        then:
        fetchedProjectManager.id != null
        fetchedProjectManager.qualification == projectManager.qualification
        fetchedProjectManager.firstName.contains(projectManager.firstName)
    }

    def "should delete project manager"() {
        given:
        company.removeEmployee(projectManager)

        when:
        projectManagerRepository.delete(projectManager)

        then:
        projectManagerRepository.findByFirstName(projectManager.firstName) == null
    }

    def "should update project manager"() {
        given:
        projectManager.qualification = MIDDLE

        when:
        def updatedProjectManager = projectManagerRepository.save(projectManager)

        then:
        updatedProjectManager.qualification == MIDDLE
        updatedProjectManager.employer == company
    }
}
