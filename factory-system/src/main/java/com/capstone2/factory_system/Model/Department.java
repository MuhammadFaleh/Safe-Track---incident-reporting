package com.capstone2.factory_system.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentId;
    @NotBlank(message = "department name can't be empty")
    @Size(message = "size must be at least 4 characters long and 200 max length", min = 4, max = 200)
    @Column(columnDefinition = "varchar(200) not null check(length(name)>3)")
    private String name;
    @NotBlank(message = "type can't be empty")
    @Pattern(regexp = "^(HR|R&D|Maintenance|Management)$", message = "must be 'HR', 'R&D','Management' or 'Maintenance'")
    @Column(columnDefinition = "varchar(40) not null check(type='HR' or type='R&D' or type='Maintenance' " +
            "or type='Management')")
    private String type;
    @NotNull(message = "factory Id must not be null")
    @Column(columnDefinition = "int not null")
    private Integer factoryId;
}
