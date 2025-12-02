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
public class IncidentReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer incidentId;
    @NotBlank(message = "type can't be empty")
    @Pattern(regexp = "^(Machinery Incident|Fire Incident|Injury Incident|Chemical Incident|Electrical Incident|Gas Leak)$"
            , message = "must be 'Machinery Incident', 'Fire Incident','Injury Incident'" +
            ",'Chemical Incident','Electrical Incident' or 'Gas Leak'")
    @Column(columnDefinition = "varchar(40) not null check(type='Machinery Incident' or type='Fire Incident' " +
            "or type='Chemical Incident' or type='Electrical Incident' or type='Gas Leak' or type='Injury Incident')")
    private String type;
    @NotBlank(message = "description can't be empty")
    @Size(min = 10, max = 400, message = "description must be between 10 and 400 in length")
    @Column(columnDefinition = "text not null check ( length(description) <= 400 and length(description) >=10)")
    private String description;
    @NotBlank(message = "severity can't be empty")
    @Pattern(regexp = "^(Low|Medium|High|Very High)$"
            , message = "must be 'Low','Medium','High' or 'Very High'")
    @Column(columnDefinition = "varchar(20) not null check(severity='Low' or severity='Medium' or severity='High' " +
            "or severity='Very High' )")
    private String severity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateReported;
    @Pattern(regexp = "^(Open|Closed)$"
            , message = "must be 'Open','Closed'")
    @Column(columnDefinition = "varchar(20) not null check(status='Open' or status='Closed')")
    private String status;
    @NotNull(message = "factory Id must not be null")
    @Column(columnDefinition = "int not null")
    private Integer factoryId;
    @NotNull(message = "employee Id must not be null")
    @Column(columnDefinition = "int not null")
    private Integer reportedBy;

}
