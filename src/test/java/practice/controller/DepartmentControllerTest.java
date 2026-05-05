package practice.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import practice.service.DepartmentService;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class DepartmentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DepartmentService departmentService;

    final UUID testUuid = UUID.randomUUID();

    @Test
    void createDepartmentShouldReturnCreated() throws Exception {
        String departmentName = "Тест";
        String createdId = testUuid.toString();

        when(departmentService.createDepartment(departmentName))
                .thenReturn(createdId);

        mockMvc.perform(post("/department/{name}", departmentName))
                .andExpect(status().isCreated())
                .andExpect(content().string(createdId));
    }

    @Test
    void updateDepartmentShouldReturnOk() throws Exception {
        String newName = "HR";
        String updatedId = testUuid.toString();

        when(departmentService.updateDepartment(testUuid, newName))
                .thenReturn(updatedId);

        mockMvc.perform(put("/department/{UUID}/{name}", testUuid, newName))
                .andExpect(status().isOk())
                .andExpect(content().string(updatedId));
    }

    @Test
    void updateDepartmentWithInvalidUuidFormat_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/department/invalid-uuid/HR"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteDepartmentShouldReturnNoContent() throws Exception {
        doNothing().when(departmentService).deleteDepartment(testUuid);

        mockMvc.perform(delete("/department/{UUID}", testUuid))
                .andExpect(status().isNoContent());
    }

    @Test
    void getDepartmentShouldReturnOk() throws Exception {
        String departmentInfo = "Department: IT, id: " + testUuid;

        when(departmentService.getDepartment(testUuid))
                .thenReturn(departmentInfo);

        mockMvc.perform(get("/department/{UUID}", testUuid))
                .andExpect(status().isOk())
                .andExpect(content().string(departmentInfo));
    }
}
