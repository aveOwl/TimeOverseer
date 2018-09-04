# TimeOverseer
[![Build Status](https://travis-ci.org/aveowl/TimeOverseer.svg?branch=master)](https://travis-ci.org/aveowl/TimeOverseer)
[![Coverage Status](https://coveralls.io/repos/github/aveowl/TimeOverseer/badge.svg?branch=master)](https://coveralls.io/github/aveowl/TimeOverseer?branch=master)

TimeOverseer is a management platform with an integrated set of tools and services allowing clients to arrange and 
keep track of working hours on their projects.

## This is work-in-progress build

* Subject description.
- [x] **Company** develops software for their clients.
- [x] Different **Projects** are in development for **Customers**.
- [x] **Project** has start and end date.
- [x] **Project** consist of **Sprints**.
- [ ] The next **Sprint** may only begin after all previous sprints are complete.
- [x] Each **Sprint** consist of **Tasks**.
- [x] **Task** has **Estimate** time to complete in hours.
- [ ] **Task** depends on other tasks. Explicit task can start only when all affecting tasks are done.
- [ ] **Task** may contain subtasks.
- [x] **Task** may require specific **Employee** level of proficiency to realize it.
- [x] **ProjectManager** settles tasks for **Employees** specifying start date and time to finish.
- [x] **Employee** can work on different **Tasks** at the same time.
- [x] Several **Employees** can be developing one **Task**.

* Application roles.
- [ ] **Administrator** creates objects in the system such as **Employee**, **Customer**, **Project**, assigns **ProjectManager**.
- [ ] **ProjectManager** controls **Project**, initiates **Sprints**, **Tasks**, assigns **Tasks** to **Employees**.
- [ ] **Employee** confirms the receipt of the **Task** records execution time or sends the request to extend development time.
- [ ] **Customer** monitors **Project** progress.
