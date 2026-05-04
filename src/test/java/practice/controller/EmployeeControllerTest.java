package practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import practice.model.EmployeeProjection;
import practice.model.dto.EmployeeRequest;
import practice.model.dto.EmployeeUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import practice.service.EmployeeService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    EmployeeService employeeService;

    final UUID testUuid = UUID.randomUUID();

    private EmployeeProjection createTestProjection() {
        return new EmployeeProjection() {
            @Override
            public String getFullName() {
                return "Иван Иванов";
            }

            @Override
            public String getPosition() {
                return "Разработчик";
            }

            @Override
            public String getDepartmentName() {
                return "АйТи";
            }
        };
    }

    @Test
    void createEmployeeShouldReturnCreated() throws Exception {
        EmployeeRequest request = EmployeeRequest.builder()
                .firstName("Иван")
                .lastName("Иванов")
                .position("Разработчик")
                .salary(5000.0)
                .departmentId(UUID.randomUUID())
                .build();

        EmployeeProjection projection = createTestProjection();

        when(employeeService.createEmployee(any(EmployeeRequest.class)))
                .thenReturn(projection);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullName").value("Иван Иванов"))
                .andExpect(jsonPath("$.position").value("Разработчик"));
    }

    @Test
    void getEmployeeShouldReturnOk() throws Exception {
        EmployeeProjection projection = createTestProjection();

        when(employeeService.getEmployee(testUuid))
                .thenReturn(projection);

        mockMvc.perform(get("/employees/{UUID}", testUuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Иван Иванов"));
    }

    @Test
    void updateEmployeeShouldReturnOk() throws Exception {
        EmployeeUpdateRequest request = EmployeeUpdateRequest.builder()
                .position("Синьор")
                .salary(6000.0)
                .build();

        EmployeeProjection updatedProjection = new EmployeeProjection() {
            @Override
            public String getFullName() {
                return "Иван Иванов";
            }

            @Override
            public String getPosition() {
                return "Синьор";
            }

            @Override
            public String getDepartmentName() {
                return "АйТи";
            }
        };

        when(employeeService.updateEmployee(any(UUID.class), any(EmployeeUpdateRequest.class)))
                .thenReturn(updatedProjection);

        mockMvc.perform(put("/employees/{UUID}", testUuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.position").value("Синьор"));
    }

    @Test
    void deleteEmployeeShouldReturnNoContent() throws Exception {
        doNothing().when(employeeService).deleteEmployee(testUuid);

        mockMvc.perform(delete("/employees/{UUID}", testUuid))
                .andExpect(status().isNoContent());
    }

    @Test
    void createEmployeeWithInvalidRequestShouldReturnBadRequest() throws Exception {
        String invalidJson = "{}";

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}