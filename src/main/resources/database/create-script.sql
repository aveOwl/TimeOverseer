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
  DROP CONSTRAINT emp_company_fk;

ALTER TABLE overseer.developer
  DROP CONSTRAINT dev_emp_fk,
  DROP CONSTRAINT dev_pm_fk;

ALTER TABLE overseer.project_manager
  DROP CONSTRAINT pm_emp_fk,
  DROP CONSTRAINT pm_project_fk;

ALTER TABLE overseer.sprint
  DROP CONSTRAINT sprint_project_fk;

ALTER TABLE overseer.task
  DROP CONSTRAINT task_sprint_fk;

ALTER TABLE overseer.developer_task
  DROP CONSTRAINT dev_task_pk,
  DROP CONSTRAINT dev_fk,
  DROP CONSTRAINT task_fk;

DROP TABLE overseer.developer_task;
DROP TABLE overseer.task;
DROP TABLE overseer.sprint;
DROP TABLE overseer.project;
DROP TABLE overseer.developer;
DROP TABLE overseer.project_manager;
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
  project_manager INT          NULL,
  customer_id     INT          NOT NULL,
  description     VARCHAR(500) NOT NULL,
  start_date      DATE         NOT NULL,
  end_date        DATE         NOT NULL
);

CREATE TABLE overseer.employee (
  id            SERIAL PRIMARY KEY,
  person_id     INT         NOT NULL,
  company_id    INT         NOT NULL,
  qualification VARCHAR(50) NOT NULL
);

CREATE TABLE overseer.developer (
  id              SERIAL PRIMARY KEY,
  emp_id          INT NOT NULL,
  project_manager INT NOT NULL
);

CREATE TABLE overseer.project_manager (
  id         SERIAL PRIMARY KEY,
  emp_id     INT NOT NULL,
  project_id INT NOT NULL
);

CREATE TABLE overseer.sprint (
  id         SERIAL PRIMARY KEY,
  project_id INT         NOT NULL,
  name       VARCHAR(50) NOT NULL
);

CREATE TABLE overseer.task (
  id                  SERIAL PRIMARY KEY,
  sprint_id           INT         NOT NULL,
  name                VARCHAR(50) NOT NULL,
  is_assigned         BOOLEAN     NOT NULL,
  proficiency         VARCHAR(50) NOT NULL,
  time_to_complete    INT         NOT NULL,
  time_in_development INT         NULL
);

CREATE TABLE overseer.developer_task (
  developer_id INT,
  task_id      INT
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
REFERENCES overseer.employee (id) ON DELETE SET NULL;

ALTER TABLE overseer.employee
  ADD CONSTRAINT emp_person_fk FOREIGN KEY (person_id)
REFERENCES overseer.person (id),
  ADD CONSTRAINT emp_company_fk FOREIGN KEY (company_id)
REFERENCES overseer.company (id) ON DELETE CASCADE;

ALTER TABLE overseer.developer
  ADD CONSTRAINT dev_emp_fk FOREIGN KEY (emp_id)
REFERENCES overseer.employee (id),
  ADD CONSTRAINT dev_pm_fk FOREIGN KEY (project_manager)
REFERENCES overseer.project_manager (id) ON DELETE SET NULL;

ALTER TABLE overseer.project_manager
  ADD CONSTRAINT pm_emp_fk FOREIGN KEY (emp_id)
REFERENCES overseer.employee (id),
  ADD CONSTRAINT pm_project_fk FOREIGN KEY (project_id)
REFERENCES overseer.project (id);

ALTER TABLE overseer.sprint
  ADD CONSTRAINT sprint_project_fk FOREIGN KEY (project_id)
REFERENCES overseer.project (id);

ALTER TABLE overseer.task
  ADD CONSTRAINT task_sprint_fk FOREIGN KEY (sprint_id)
REFERENCES overseer.sprint (id);

ALTER TABLE overseer.developer_task
  ADD CONSTRAINT dev_task_pk PRIMARY KEY (developer_id, task_id),
  ADD CONSTRAINT dev_fk FOREIGN KEY (developer_id)
REFERENCES overseer.developer (id),
  ADD CONSTRAINT task_fk FOREIGN KEY (task_id)
REFERENCES overseer.task (id);