package practice.service.impls;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import practice.model.Department;
import practice.model.Employee;
import practice.model.EmployeeProjection;
import practice.model.dto.EmployeeRequest;
import practice.model.dto.EmployeeUpdateRequest;
import practice.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.repository.DepartmentRepository;
import practice.repository.EmployeeRepository;
import practice.service.EmployeeService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;
    DepartmentRepository departmentRepository;

    @Override
    public EmployeeProjection createEmployee(EmployeeRequest request) {
        Department department = departmentCheckIfExists(request.getDepartmentId());

        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .position(request.getPosition())
                .salary(request.getSalary())
                .department(department)
                .build();

        log.info("Сохраняем сотрудника");
        Employee saved = employeeRepository.save(employee);
        return employeeRepository.findByEmployeeId(saved.getEmployeeId());
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeProjection getEmployee(UUID uuid) {
        log.info("Возвращаем сотрудника");
        return employeeRepository.findByEmployeeId(uuid);
    }

    @Override
    public EmployeeProjection updateEmployee(UUID uuid, EmployeeUpdateRequest request) {
        Employee employee = employeeCheckIfExists(uuid);

        if (request.getDepartmentId() != null) {
            employee.setDepartment(departmentCheckIfExists(request.getDepartmentId()));
        }
        if (request.getFirstName() != null) employee.setFirstName(request.getFirstName());
        if (request.getLastName() != null) employee.setLastName(request.getLastName());
        if (request.getPosition() != null) employee.setPosition(request.getPosition());
        if (request.getSalary() != null) employee.setSalary(request.getSalary());

        log.info("Обновляем сотрудника");
        Employee updated = employeeRepository.save(employee);
        return employeeRepository.findByEmployeeId(updated.getEmployeeId());
    }

    @Override
    public void deleteEmployee(UUID uuid) {
        employeeCheckIfExists(uuid);
        log.info("Удаляем сотрудника");
        employeeRepository.deleteById(uuid);
    }

    private Department departmentCheckIfExists(UUID uuid) {
        return departmentRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Департамент не найден"));
    }

    private Employee employeeCheckIfExists(UUID uuid) {
        return employeeRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Сотрудник не найден"));
    }
}