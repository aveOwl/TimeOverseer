# TimeOverseer

TimeOverseer is a management platform with an integrated set of tools and services allowing clients to arrange and 
keep track of working hours on their projects.

## This is work-in-progress build

* Subject description.
- [ ] **Company** develops software for their clients.
- [ ] Different **Projects** are in development for **Customers**.
- [ ] **Project** has start and end date.
- [ ] **Project** consist of **Sprints**.
- [ ] The next **Sprint** may only begin after all previous sprints are complete.
- [ ] Each **Sprint** consist of **Tasks**.
- [ ] **Task** has **Estimate** time to complete in hours.
- [ ] **Task** depends on other tasks. Explicit task can start only when all affecting tasks are done.
- [ ] **Task** may contain **Sub-Tasks**.
- [ ] **Task** may require specific **Employee** level of proficiency to realize it.
- [ ] **ProjectManager** settles tasks for **Employees** specifying start date and time to finish.
- [ ] **Employee** can work on different **Tasks** at the same time.
- [ ] Several **Employees** can be developing one **Task**.
- [ ] **Employee** confirms the receipt of the **Task**, records execution time or sends the request to extend development time.

* Application roles.
- [ ] **Administrator** creates objects in the system such as **Employee**, **Customer**, **Project**, assigns **ProjectManager**.
- [ ] **ProjectManager** controls **Project**, initiates **Sprints**, **Tasks**, assigns **Tasks** to **Employees**.
- [ ] **Employee** confirms the receipt of the **Task** records execution time or sends the request to extend development time.
- [ ] **Customer** monitors **Project** progress.

* Technical background.
    * Front-end
        * AngularJS
        * Bootstrap
    * Back-end
        * Apache Tomcat
        * Spring-Boot
        * PostgreSQL
    * Test
        * Spock