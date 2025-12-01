package com.capstone2.factory_system.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;
    @NotBlank(message = "full name can't be empty")
    @Size(message = "size must be at least 4 characters long and 200 max length", min = 4, max = 200)
    @Column(columnDefinition = "varchar(200) not null")
    private String fullName;
    @Pattern(regexp = "^([MF])$", message = "gender must be M or F")
    @NotBlank(message = "gender can't be empty")
    @Column(columnDefinition = "varchar(1) not null check(gender='F' or gender='M')")
    private String gender;
    @NotBlank(message = "email can't be empty")
    @Email(message = "enter a valid email")
    @Size(max = 200, message = "email size can't be longer than 200 characters")
    @Column(columnDefinition = "varchar(200) not null unique")
    private String email;
    @Column(columnDefinition = "int")
    private Integer managerId;
    @NotNull(message = "department Id must not be null")
    @Column(columnDefinition = "int not null")
    private Integer departmentId;
    @NotNull(message = "factory Id must not be null")
    @Column(columnDefinition = "int not null")
    private Integer factoryId;
}
