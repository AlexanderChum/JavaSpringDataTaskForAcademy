package practice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequest {

    @NotBlank(message = "Имя не может отсутствовать")
    String firstName;

    @NotBlank(message = "Фамилия не может отсутствовать")
    String lastName;

    @NotBlank(message = "Должность не может отсутствовать")
    String position;

    @NotNull(message = "Зарплата не может отсутствовать")
    Double salary;

    @NotNull(message = "id департамента не может отсутствовать")
    UUID departmentId;
}
