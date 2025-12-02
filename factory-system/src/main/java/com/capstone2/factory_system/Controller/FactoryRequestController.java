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
    public ResponseEntity<?> createFactoryRequest(@RequestBody @Valid FactoryRequest factoryRequest){
        factoryRequestService.createFactoryRequest(factoryRequest);
        return ResponseEntity.status(200).body(new ApiResponse("factory request was created successfully"));

    }

    @PutMapping("/update-factory-request/{factoryRecord}")
    public ResponseEntity<?> updateFactoryRequest(@PathVariable String factoryRecord, @RequestBody @Valid FactoryRequest factoryRequest, Errors errors){
        factoryRequestService.updateFactoryRequest(factoryRecord, factoryRequest);
        return ResponseEntity.status(200).body(new ApiResponse("factory request was updated successfully"));
    }

    @DeleteMapping("/delete-factory-request/{id}")
    public ResponseEntity<?> deleteFactoryRequest(@PathVariable Integer id){
        factoryRequestService.deleteFactoryRequest(id);
        return ResponseEntity.status(200).body(new ApiResponse("factory request was deleted successfully"));

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
        factoryRequestService.approve(factoryRecord, adminId);
        return ResponseEntity.status(200).body(new ApiResponse("factory request was approved successfully"));
    }

    @PutMapping("/reject/{factoryRecord}/{adminId}")
    public ResponseEntity<?> reject(@PathVariable String factoryRecord, @PathVariable Integer adminId){
        factoryRequestService.reject(factoryRecord, adminId);
        return ResponseEntity.status(200).body(new ApiResponse("factory request was rejected successfully"));
    }
}