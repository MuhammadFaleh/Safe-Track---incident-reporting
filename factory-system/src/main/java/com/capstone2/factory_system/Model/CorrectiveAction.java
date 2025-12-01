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
public class CorrectiveAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actionId;
    @NotBlank(message = "description can't be empty")
    @Size(min = 10, max = 400, message = "description must be between 10 and 400 in length")
    @Column(columnDefinition = "text not null check ( length(description) <= 400 and length(description) >=10)")
    private String description;
    @Column(columnDefinition = "varchar(20) not null check(status='Open' or status='Closed')")
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP not null")
    private LocalDateTime endDate;
    @NotNull(message = "incident Id must not be null")
    @Column(columnDefinition = "int not null")
    private Integer incidentId;
}
