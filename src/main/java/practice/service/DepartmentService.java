package practice.service;

import java.util.UUID;

public interface DepartmentService {

    String createDepartment(String name);

    String updateDepartment(UUID uuid, String name);

    void deleteDepartment(UUID uuid);

    String getDepartment(UUID uuid);
}
