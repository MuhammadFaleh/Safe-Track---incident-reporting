package com.capstone2.factory_system.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class IncidentAffectedEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "severity can't be empty")
    @Pattern(regexp = "^(Low|Medium|High|Very High)$"
            , message = "must be 'Low','Medium','High' or 'Very High'")
    @Column(columnDefinition = "varchar(20) not null check(severity='Low' or severity='Medium' or severity='High' " +
            "or severity='Very High' )")
    private String severity;
    @NotBlank(message = "notes can't be empty")
    @Size(min = 10, max = 400, message = "notes must be between 10 and 400 in length")
    @Column(columnDefinition = "text not null check ( length(notes) <= 400 and length(notes) >=10)")
    private String notes;
    @NotNull(message = "incident Id must not be null")
    @Column(columnDefinition = "int not null")
    private Integer incidentId;
    @NotNull(message = "equipment Id must not be null")
    @Column(columnDefinition = "int not null")
    private Integer equipmentId;
}
