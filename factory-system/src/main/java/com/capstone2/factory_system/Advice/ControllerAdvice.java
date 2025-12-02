package com.capstone2.factory_system.Advice;

import com.capstone2.factory_system.Api.ApiException;
import com.capstone2.factory_system.Api.ApiResponse;
import org.springframework.boot.json.JsonParseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> ApiException(ApiException apiException){
        return ResponseEntity.status(400).body(new ApiResponse(apiException.getMessage()));
    }

    @ExceptionHandler(value= SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> SqlValidationError(SQLIntegrityConstraintViolationException sqlValidationError){
        return ResponseEntity.status(400).body(new ApiResponse(sqlValidationError.getMessage()));
    }

    @ExceptionHandler(value= HttpMessageNotReadableException.class)
    public ResponseEntity<?> JsonParsingError(HttpMessageNotReadableException jsonNotReadable){
        return ResponseEntity.status(400).body(new ApiResponse("make sure you entered a correct response json error"));

    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> TypeMismatchError(MethodArgumentTypeMismatchException mismatchError){
        return ResponseEntity.status(400).body(new ApiResponse(("wrong value type entered did you do a word in place of a number?")));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> ValidationError( MethodArgumentNotValidException nonValidArg){
        return ResponseEntity.status(400).body(new ApiResponse(nonValidArg.getFieldError().getDefaultMessage()));
    }
    //use it later
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException duplicateEntry) {
        return ResponseEntity.status(400).body(new ApiResponse(duplicateEntry.getMessage()));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> DuplicateKeyException(DuplicateKeyException duplicateEntry) {
        return ResponseEntity.status(400).body(new ApiResponse("the name or email is already taken please chose another"));
    }

    @ExceptionHandler(value = JsonParseException.class)
    public ResponseEntity<?> jsonWrongValue(JsonParseException jsonParseException) {
        return ResponseEntity.status(400).body(new ApiResponse(jsonParseException.getMessage()));
    }

    @ExceptionHandler(value =
            HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));
    }
}
