ALTER TABLE overseer.administrator
  DROP CONSTRAINT admin_person_fk;

ALTER TABLE overseer.customer
  DROP CONSTRAINT customer_person_fk;

ALTER TABLE overseer.company_customer
  DROP CONSTRAINT comp_cust_pk,
  DROP CONSTRAINT company_fk,
  DROP CONSTRAINT customer_fk;

ALTER TABLE overseer.project
  DROP CONSTRAINT project_customer_fk,
  DROP CONSTRAINT project_employee_fk;

ALTER TABLE overseer.employee
  DROP CONSTRAINT emp_person_fk,
  DROP CONSTRAINT emp_company_fk,
  DROP CONSTRAINT emp_project_manager_fk,
  DROP CONSTRAINT emp_project_fk;

ALTER TABLE overseer.sprint
  DROP CONSTRAINT sprint_project_fk;

ALTER TABLE overseer.task
  DROP CONSTRAINT task_sprint_fk,
  DROP CONSTRAINT task_subtask_fk;

ALTER TABLE overseer.employee_task
  DROP CONSTRAINT emp_task_pk,
  DROP CONSTRAINT emp_fk,
  DROP CONSTRAINT task_fk;

DROP TABLE overseer.employee_task;
DROP TABLE overseer.task;
DROP TABLE overseer.sprint;
DROP TABLE overseer.project;
DROP TABLE overseer.employee;
DROP TABLE overseer.company_customer;
DROP TABLE overseer.company;
DROP TABLE overseer.customer;
DROP TABLE overseer.administrator;
DROP TABLE overseer.person;

CREATE TABLE overseer.person (
  id         SERIAL PRIMARY KEY,
  first_name VARCHAR(50) NOT NULL,
  last_name  VARCHAR(50) NOT NULL,
  login      VARCHAR(50) NOT NULL UNIQUE,
  password   VARCHAR(50) NOT NULL
);

CREATE TABLE overseer.administrator (
  id        SERIAL PRIMARY KEY,
  person_id INT NOT NULL
);

CREATE TABLE overseer.customer (
  id                 SERIAL PRIMARY KEY,
  person_id          INT          NOT NULL,
  business_interests VARCHAR(255) NOT NULL
);

CREATE TABLE overseer.company (
  id       SERIAL PRIMARY KEY,
  name     VARCHAR(150) NOT NULL,
  founded  DATE         NOT NULL,
  industry VARCHAR(255) NOT NULL,
  founders VARCHAR(255) NOT NULL,
  products VARCHAR(255) NOT NULL
);

CREATE TABLE overseer.company_customer (
  company_id  INT,
  customer_id INT
);

CREATE TABLE overseer.project (
  id              SERIAL PRIMARY KEY,
  project_manager INT          NOT NULL,
  customer_id     INT          NOT NULL,
  description     VARCHAR(500) NOT NULL,
  start_date      DATE         NOT NULL,
  end_date        DATE         NOT NULL
);

CREATE TABLE overseer.employee (
  id              SERIAL PRIMARY KEY,
  person_id       INT         NOT NULL,
  company_id      INT         NOT NULL,
  project_manager INT         NOT NULL,
  project_id      INT         NOT NULL,
  role            VARCHAR(50) NOT NULL,
  qualification   VARCHAR(50) NOT NULL
);

CREATE TABLE overseer.sprint (
  id         SERIAL PRIMARY KEY,
  project_id INT         NOT NULL,
  name       VARCHAR(50) NOT NULL
);

CREATE TABLE overseer.task (
  id           SERIAL PRIMARY KEY,
  name         VARCHAR(50) NOT NULL,
  sprint_id    INT         NOT NULL,
  is_assigned  BOOLEAN     NOT NULL,
  proficientcy VARCHAR(50) NOT NULL,
  sub_task     INT         NOT NULL
);

CREATE TABLE overseer.employee_task (
  employee_id INT,
  task_id     INT
);

ALTER TABLE overseer.administrator
  ADD CONSTRAINT admin_person_fk FOREIGN KEY (person_id)
REFERENCES overseer.person (id);

ALTER TABLE overseer.customer
  ADD CONSTRAINT customer_person_fk FOREIGN KEY (person_id)
REFERENCES overseer.person (id);

ALTER TABLE overseer.company_customer
  ADD CONSTRAINT comp_cust_pk PRIMARY KEY (company_id, customer_id),
  ADD CONSTRAINT company_fk FOREIGN KEY (company_id)
REFERENCES overseer.company (id),
  ADD CONSTRAINT customer_fk FOREIGN KEY (customer_id)
REFERENCES overseer.customer (id);

ALTER TABLE overseer.project
  ADD CONSTRAINT project_customer_fk FOREIGN KEY (customer_id)
REFERENCES overseer.customer (id),
  ADD CONSTRAINT project_employee_fk FOREIGN KEY (project_manager)
REFERENCES overseer.employee (id);

ALTER TABLE overseer.employee
  ADD CONSTRAINT emp_person_fk FOREIGN KEY (person_id)
REFERENCES overseer.person (id),
  ADD CONSTRAINT emp_company_fk FOREIGN KEY (company_id)
REFERENCES overseer.company (id),
  ADD CONSTRAINT emp_project_manager_fk FOREIGN KEY (project_manager)
REFERENCES overseer.employee (id),
  ADD CONSTRAINT emp_project_fk FOREIGN KEY (project_id)
REFERENCES overseer.project (id);

ALTER TABLE overseer.sprint
  ADD CONSTRAINT sprint_project_fk FOREIGN KEY (project_id)
REFERENCES overseer.project (id);

ALTER TABLE overseer.task
  ADD CONSTRAINT task_sprint_fk FOREIGN KEY (sprint_id)
REFERENCES overseer.sprint (id),
  ADD CONSTRAINT task_subtask_fk FOREIGN KEY (sub_task)
REFERENCES overseer.task (id);

ALTER TABLE overseer.employee_task
  ADD CONSTRAINT emp_task_pk PRIMARY KEY (employee_id, task_id),
  ADD CONSTRAINT emp_fk FOREIGN KEY (employee_id)
REFERENCES overseer.employee (id),
  ADD CONSTRAINT task_fk FOREIGN KEY (task_id)
REFERENCES overseer.task (id);