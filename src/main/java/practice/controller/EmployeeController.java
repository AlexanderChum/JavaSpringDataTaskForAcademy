package practice.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import practice.model.EmployeeProjection;
import practice.model.dto.EmployeeRequest;
import practice.model.dto.EmployeeUpdateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import practice.service.EmployeeService;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class EmployeeController {

    EmployeeService service;

    @PostMapping
    public ResponseEntity<EmployeeProjection> create(@RequestBody @Valid EmployeeRequest request) {
        log.info("Получен запрос на создание сотрудника");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.createEmployee(request));
    }

    @GetMapping("/{UUID}")
    public ResponseEntity<EmployeeProjection> getById(@PathVariable(name = "UUID") UUID uuid) {
        log.info("Получен запрос на получение сотрудника");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getEmployee(uuid));
    }

    @PutMapping("/{UUID}")
    public ResponseEntity<EmployeeProjection> update(@PathVariable(name = "UUID") UUID uuid,
                                                     @RequestBody EmployeeUpdateRequest request) {
        log.info("Получен запрос на обновление сотрудника");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.updateEmployee(uuid, request));
    }

    @DeleteMapping("/{UUID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "UUID") UUID uuid) {
        service.deleteEmployee(uuid);
    }
}