package practice.service;

import practice.model.EmployeeProjection;
import practice.model.dto.EmployeeRequest;
import practice.model.dto.EmployeeUpdateRequest;

import java.util.UUID;

public interface EmployeeService {

    EmployeeProjection createEmployee(EmployeeRequest request);

    EmployeeProjection getEmployee(UUID id);

    EmployeeProjection updateEmployee(UUID id, EmployeeUpdateRequest request);

    void deleteEmployee(UUID id);
}
