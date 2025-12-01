package com.capstone2.factory_system.Controller;

import com.capstone2.factory_system.Api.ApiResponse;
import com.capstone2.factory_system.Api.RAGResponse;
import com.capstone2.factory_system.Api.SQLResponse;
import com.capstone2.factory_system.Service.ChatBotService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chatbot")
@RequiredArgsConstructor
public class ChatBotController {

    private final ChatBotService chatBotService;


    @PostMapping("/ask-rag")
    public ResponseEntity<?> askRAG(@RequestBody @Valid String question, Errors errors) {
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        RAGResponse response = chatBotService.askRAG(question);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/ask-sql")
    public ResponseEntity<?> askSQL(@RequestBody @Valid String question, Errors errors) {
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        SQLResponse response = chatBotService.askSQL(question);
        return ResponseEntity.status(200).body(response);
    }
}