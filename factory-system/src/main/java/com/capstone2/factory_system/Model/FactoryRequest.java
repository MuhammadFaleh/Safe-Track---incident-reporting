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
public class FactoryRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer factoryRequestId;
    @NotBlank(message = "full name can't be empty")
    @Size(message = "size must be at least 4 characters long and 200 max length", min = 4, max = 200)
    @Column(columnDefinition = "varchar(200) not null")
    private String fullName;
    @NotBlank(message = "factory name can't be empty")
    @Size(message = "size must be at least 4 characters long and 255 max length", min = 4, max = 200)
    @Column( columnDefinition = "varchar(255) not null")
    private String factoryName;
    @NotBlank(message = "factory Record Number can't be empty")
    @Size(message = "must be 10 characters long", min = 10, max = 10)
    @Pattern(regexp = "^([0-9])*$",
            message = "please enter only numbers")
    @Column( columnDefinition = "varchar(10) not null")
    private String factoryRecordNumber;
    @NotBlank(message = "city must not be empty")
    @Column(columnDefinition = "varchar(200) not null")
    private String city;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(columnDefinition = "TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime requestDate;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime endDate;
    @Column(columnDefinition = "varchar(10) not null check(status='approved' or status='rejected' or status='pending')")
    private String status;
    @Column(columnDefinition = "int")
    private Integer checkedBy;
}
