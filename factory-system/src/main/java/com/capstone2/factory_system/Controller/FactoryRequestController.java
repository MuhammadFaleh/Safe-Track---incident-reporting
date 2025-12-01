package com.capstone2.factory_system.Controller;

import com.capstone2.factory_system.Api.ApiResponse;
import com.capstone2.factory_system.Model.FactoryRequest;
import com.capstone2.factory_system.Service.FactoryRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/factory-request")
public class FactoryRequestController {
    private final FactoryRequestService factoryRequestService;

    @GetMapping("/get-factory-requests")
    public ResponseEntity<?> getFactoryRequests(){
        return ResponseEntity.status(200).body(factoryRequestService.getFactoryRequests());
    }

    @PostMapping("/create-factory-request")
    public ResponseEntity<?> createFactoryRequest(@RequestBody @Valid FactoryRequest factoryRequest, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String status = factoryRequestService.createFactoryRequest(factoryRequest);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("factory request was created successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @PutMapping("/update-factory-request/{factoryRecord}")
    public ResponseEntity<?> updateFactoryRequest(@PathVariable String factoryRecord, @RequestBody @Valid FactoryRequest factoryRequest, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String status = factoryRequestService.updateFactoryRequest(factoryRecord, factoryRequest);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("factory request was updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @DeleteMapping("/delete-factory-request/{id}")
    public ResponseEntity<?> deleteFactoryRequest(@PathVariable Integer id){
        String status = factoryRequestService.deleteFactoryRequest(id);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("factory request was deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @GetMapping("/get-factory-request-id/{id}")
    public ResponseEntity<?> getFactoryRequestId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(factoryRequestService.getFactoryRequestId(id));
    }

    @GetMapping("/get-factory-by-records/{factoryRecord}")
    public ResponseEntity<?> getFactoryByRecords(@PathVariable String factoryRecord){
        return ResponseEntity.status(200).body(factoryRequestService.getFactoryByRecords(factoryRecord));
    }

    @GetMapping("/get-newest-request/{factoryRecord}")
    public ResponseEntity<?> getNewestRequest(@PathVariable String factoryRecord){
        return ResponseEntity.status(200).body(factoryRequestService.getNewestRequest(factoryRecord));
    }

    @GetMapping("/get-factories-pending")
    public ResponseEntity<?> getFactoriesPending(){
        return ResponseEntity.status(200).body(factoryRequestService.getFactoriesPending());
    }

    @PutMapping("/approve/{factoryRecord}/{adminId}")
    public ResponseEntity<?> approve(@PathVariable String factoryRecord, @PathVariable Integer adminId){
        String status = factoryRequestService.approve(factoryRecord, adminId);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("factory request was approved successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @PutMapping("/reject/{factoryRecord}/{adminId}")
    public ResponseEntity<?> reject(@PathVariable String factoryRecord, @PathVariable Integer adminId){
        String status = factoryRequestService.reject(factoryRecord, adminId);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("factory request was rejected successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }
}