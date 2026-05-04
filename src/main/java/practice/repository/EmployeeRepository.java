package practice.repository;

import practice.model.Employee;
import practice.model.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    EmployeeProjection findByEmployeeId(UUID employeeId);
}
