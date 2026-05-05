package practice.service.impls;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import practice.model.Department;
import practice.model.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.repository.DepartmentRepository;
import practice.service.DepartmentService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository repository;

    @Override
    public String createDepartment(String name) {
        Department toSave = Department.builder().name(name).build();
        log.info("Сохраняем департамент");
        return repository.save(toSave).getName();
    }

    @Override
    public String updateDepartment(UUID uuid, String name) {
        Department toUpdate = checkIfExists(uuid);
        toUpdate.setName(name);
        log.info("Обновляем департамент");
        return repository.save(toUpdate).getName();
    }

    @Override
    public void deleteDepartment(UUID uuid) {
        checkIfExists(uuid);
        log.info("Удаляем репозиторий");
        repository.deleteById(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public String getDepartment(UUID uuid) {
        return checkIfExists(uuid).getName();
    }

    private Department checkIfExists(UUID uuid) {
        return repository.findById(uuid)
                .orElseThrow(() -> new NotFoundException("Департамент не найден"));
    }
}
