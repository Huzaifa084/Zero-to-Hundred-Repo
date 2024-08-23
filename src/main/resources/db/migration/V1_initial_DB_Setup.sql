CREATE TABLE employee
(
    employee_id     BIGINT       NOT NULL,
    employee_name   VARCHAR(255) NULL,
    employee_email  VARCHAR(255) NULL,
    employee_age    INT          NULL,
    date_of_joining date         NULL,
    gender          VARCHAR(255) NULL,
    `role`          VARCHAR(255) NULL,
    salary          DOUBLE       NOT NULL,
    is_active       BIT(1)       NULL,
    CONSTRAINT pk_employee PRIMARY KEY (employee_id)
);