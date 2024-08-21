package org.devcastle.zerotohundred.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long employeeId;

    @NotBlank(message = "Employee name must not be null")
    @Size(min = 3,message = "Name characters must be in range 3-10")
    private String employeeName;

    @Email(message = "Enter a valid email")
    private String employeeEmail;

    @NotNull(message = "Age can not be blank")
    @Max(message = "Age can not be greater than 80", value = 80)
    @Min(message = "Age can not be less than 18", value = 18)
    @Positive(message = "Age can not b negative")
    private Integer employeeAge;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(MALE|FEMALE)$", message = "Gender must be MALE or FEMALE ")
    private String gender;

    @NotBlank(message = "Employee Role must not be null")
    private String role;

    @Positive(message = "Salary can not be zero or negative")
    @NotNull(message = "Salary can not be blank")
    @Digits(integer = 6, fraction = 2, message = "The salary can be in the form XXXXX.YY")
    @DecimalMax(value = "100000.99")
    @DecimalMin(value = "100.50")
    private double salary;

    @PastOrPresent(message = "Date of joining can not be of future")
    private LocalDate dateOfJoining;

    @AssertTrue(message = "Employee must be active")
    @JsonProperty("isActive")
    private Boolean isActive;
}
