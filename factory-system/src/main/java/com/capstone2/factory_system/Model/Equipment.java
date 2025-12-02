package com.capstone2.factory_system.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer equipmentId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP not null")
    private LocalDateTime lastMaintenance;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP not null")
    private LocalDateTime purchaseDate;
    @NotBlank(message = "category can't be empty")
    @Pattern(regexp = "^(Heavy Machinery|Light Machinery|Tools|Material Handling)$", message = "must be " +
            "'Material Handling', 'Heavy Machinery','Light Machinery' or 'Tools'")
    @Column(columnDefinition = "varchar(20) not null check(category='Heavy Machinery' or category='Light Machinery' or category='Tools' " +
            "or category='Material Handling')")
    private String category;
    @Pattern(regexp = "^(Retired|Working|Under Repair)$", message = "must be " +
            "'Retired', 'Working','Under Repair'")
    @Column(columnDefinition = "varchar(20) not null check(status='Under Repair' or status='Working' or status='Retired')")
    private String status;
    @NotBlank(message = "serial Number must not be empty")
    @Size(message = "size must be between 4 and 100", max = 100, min = 4)
    @Column(columnDefinition = "Varchar(100) not null")
    private String serialNumber;
    @NotNull(message = "factory id must not be null")
    private Integer factoryId;
}
