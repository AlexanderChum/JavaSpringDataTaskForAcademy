package practice.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import practice.service.DepartmentService;

import java.util.UUID;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
@Slf4j
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController {
    DepartmentService service;

    @PostMapping("/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public String createDepartment(@PathVariable(name = "name")
                                   @NotBlank(message = "Имя департамента не должно быть пустым")
                                   String name) {
        log.info("Получен запрос на создание департамента");
        return service.createDepartment(name);
    }

    @PutMapping("/{UUID}/{name}")
    @ResponseStatus(HttpStatus.OK)
    public String updateDepartment(@PathVariable(name = "UUID")
                                   @NotNull(message = "id должен быть указан")
                                   UUID uuid,

                                   @PathVariable(name = "name")
                                   @NotBlank(message = "Имя департамента не должно быть пустым")
                                   String name) {
        log.info("Получен запрос на обновление департамента");
        return service.updateDepartment(uuid, name);
    }

    @DeleteMapping("/{UUID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDepartment(@PathVariable(name = "UUID")
                                 @NotNull(message = "id должен быть указан")
                                 UUID uuid) {
        log.info("Получен запрос на удаление департамента");
        service.deleteDepartment(uuid);
    }

    @GetMapping("/{UUID}")
    @ResponseStatus(HttpStatus.OK)
    public String getDepartment(@PathVariable(name = "UUID")
                                @NotNull(message = "id должен быть указан")
                                UUID uuid) {
        log.info("Получен запрос на получение департамента");
        return service.getDepartment(uuid);
    }
}
