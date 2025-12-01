package com.capstone2.factory_system.Model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class QueryRequest {
    @NotBlank(message = "question must not be empty")
    private String question;
}
