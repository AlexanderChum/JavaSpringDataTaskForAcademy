CREATE TABLE IF NOT EXISTS departments (
    department_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS employees (
    employee_id UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL,
    salary DOUBLE PRECISION NOT NULL,
    department_id UUID,
    CONSTRAINT fk_employee_department FOREIGN KEY (department_id) REFERENCES departments(department_id)
);