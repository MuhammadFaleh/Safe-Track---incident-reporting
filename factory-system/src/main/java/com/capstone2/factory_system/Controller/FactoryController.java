package com.capstone2.factory_system.Controller;

import com.capstone2.factory_system.Api.ApiResponse;
import com.capstone2.factory_system.Model.Factory;
import com.capstone2.factory_system.Service.FactoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/factory")
public class FactoryController {
    private final FactoryService factoryService;

    @GetMapping("/get-factories")
    public ResponseEntity<?> getFactories(){
        return ResponseEntity.status(200).body(factoryService.getFactories());
    }

    @PostMapping("/create-factory")
    public ResponseEntity<?> createFactory(@RequestBody @Valid Factory factory){
        factoryService.createFactory(factory);
        return ResponseEntity.status(200).body(new ApiResponse("factory was created successfully"));
    }

    @PutMapping("/update-factory/{id}")
    public ResponseEntity<?> updateFactory(@PathVariable Integer id, @RequestBody @Valid Factory factory){
        factoryService.updateFactory(id, factory);
        return ResponseEntity.status(200).body(new ApiResponse("factory was updated successfully"));
    }


    @DeleteMapping("/delete-factory/{id}/{username}")
    public ResponseEntity<?> deleteFactory(@PathVariable Integer id, @PathVariable String username){
        factoryService.deleteFactory(id, username);
        return ResponseEntity.status(200).body(new ApiResponse("factory was deleted successfully"));
    }

    @GetMapping("/get-factory-id/{id}")
    public ResponseEntity<?> getFactoryById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(factoryService.getFactoryById(id));
    }

    @GetMapping("/get-factory-username/{username}")
    public ResponseEntity<?> getFactoryByUsername(@PathVariable String username){
        return ResponseEntity.status(200).body(factoryService.getFactoryByUsername(username));
    }

    @GetMapping("/get-factory-email/{email}")
    public ResponseEntity<?> getFactoryByEmail(@PathVariable String email){
        return ResponseEntity.status(200).body(factoryService.getFactoryByEmail(email));
    }

    @GetMapping("/get-factory-record-number/{factoryNumber}")
    public ResponseEntity<?> getFactoryByFactoryRecordNumber(@PathVariable String factoryNumber){
        return ResponseEntity.status(200).body(factoryService.getFactoryByFactoryRecordNumber(factoryNumber));
    }

    @GetMapping("/get-factory-city/{city}")
    public ResponseEntity<?> getFactoryByCity(@PathVariable String city){
        return ResponseEntity.status(200).body(factoryService.getFactoryByCity(city));
    }

    @GetMapping("/get-factory-name/{name}")
    public ResponseEntity<?> getFactoryByName(@PathVariable String name){
        return ResponseEntity.status(200).body(factoryService.getFactoryByName(name));
    }

    @GetMapping("/get-factory-date/{date}")
    public ResponseEntity<?> getFactoryByDate(@PathVariable LocalDate date){
        return ResponseEntity.status(200).body(factoryService.getFactoryByDate(date));
    }

    @GetMapping("/get-factory-request-id/{id}")
    public ResponseEntity<?> getFactoryByRequestId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(factoryService.getFactoryByRequestId(id));
    }
}