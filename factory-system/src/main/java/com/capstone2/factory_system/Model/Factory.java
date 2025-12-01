package com.capstone2.factory_system.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Factory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer factoryId;
    @NotBlank(message = "username can't be empty")
    @Size(message = "size must be at least 4 characters long and 40 max length", min = 4, max = 40)
    @Column(columnDefinition = "varchar(40) not null unique check(length(username)>3)")
    private String username;
    @NotBlank(message = "full name can't be empty")
    @Size(message = "size must be at least 4 characters long and 200 max length", min = 4, max = 200)
    @Column(columnDefinition = "varchar(200) not null")
    private String fullName;
    @NotBlank(message = "email can't be empty")
    @Email(message = "enter a valid email")
    @Size(max = 200, message = "email size can't be longer than 200 characters")
    @Column(columnDefinition = "varchar(200) not null unique")
    private String email;
    @NotBlank(message = "password can't be empty")
    @Size(message = "password must be at least 5 characters long and not longer than 40", min = 5, max = 40)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$&+,:;=?@#|'<>.^*()%!-]).*$",
            message = "please enter at least one number and special character")
    @Column(columnDefinition = "varchar(255) not null check(length(password) > 4)")
    private String password;
    @NotBlank(message = "factory name can't be empty")
    @Size(message = "size must be at least 4 characters long and 255 max length", min = 4, max = 255)
    @Column( columnDefinition = "varchar(255) not null")
    private String factoryName;
    @NotBlank(message = "factory Record Number can't be empty")
    @Size(message = "must be 10 characters long", min = 10, max = 10)
    @Pattern(regexp = "^([0-9])*$",
            message = "please enter only numbers")
    @Column( columnDefinition = "varchar(10) not null unique")
    private String factoryRecordNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate createdAt;
    @NotBlank(message = "city must not be empty")
    @Column(columnDefinition = "varchar(200) not null")
    private String city;
    @NotNull(message = "factory Request Id must not be null")
    @Column(columnDefinition = "int not null")
    private Integer factoryRequestId;
}
