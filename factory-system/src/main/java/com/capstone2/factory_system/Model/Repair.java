package com.capstone2.factory_system.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Repair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer repairId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime endDate;
    @NotBlank(message = "severity can't be empty")
    @Pattern(regexp = "^(Open|Closed)$"
            , message = "must be 'Open','Closed'")
    @Column(columnDefinition = "varchar(20) not null check(status='Open' or status='Closed')")
    private String status;
    @NotNull(message = "employee Id must not be null")
    @Column(columnDefinition = "int not null")
    private Integer employeeId;
    @NotNull(message = "equipment Id must not be null")
    @Column(columnDefinition = "int not null")
    private Integer equipmentId;
    @NotNull(message = "factory Id must not be null")
    @Column(columnDefinition = "int not null")
    private Integer factoryId;
}
