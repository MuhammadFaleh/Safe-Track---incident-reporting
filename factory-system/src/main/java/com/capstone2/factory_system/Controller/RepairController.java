package com.capstone2.factory_system.Controller;

import com.capstone2.factory_system.Api.ApiResponse;
import com.capstone2.factory_system.Model.Repair;
import com.capstone2.factory_system.Service.RepairService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/repair")
public class RepairController {
    private final RepairService repairService;

    @GetMapping("/get-repairs")
    public ResponseEntity<?> getRepair(){
        return ResponseEntity.status(200).body(repairService.getRepair());
    }

    @PostMapping("/create-repair")
    public ResponseEntity<?> createRepair(@RequestBody @Valid Repair repair){
        repairService.createRepair(repair);
        return ResponseEntity.status(200).body(new ApiResponse("repair was created successfully"));

    }

    @PutMapping("/update-repair/{id}")
    public ResponseEntity<?> updateRepair(@PathVariable Integer id, @RequestBody @Valid Repair repair){
        repairService.updateRepair(id, repair);
        return ResponseEntity.status(200).body(new ApiResponse("repair was updated successfully"));

    }

    @DeleteMapping("/delete-repair/{id}/{employeeId}")
    public ResponseEntity<?> deleteRepair(@PathVariable Integer id, @PathVariable Integer employeeId){
        repairService.deleteRepair(id, employeeId);
        return ResponseEntity.status(200).body(new ApiResponse("repair was deleted successfully"));
    }

    @PutMapping("/set-status-close/{id}/{employeeId}")
    public ResponseEntity<?> setStatusClose(@PathVariable Integer id, @PathVariable Integer employeeId){
        return ResponseEntity.status(200).body(new ApiResponse("repair status was set to closed successfully"));
    }

    @GetMapping("/get-repair-id/{id}")
    public ResponseEntity<?> getRepairById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(repairService.getRepairById(id));
    }

    @GetMapping("/get-repair-factory-id/{id}")
    public ResponseEntity<?> getRepairByFactoryId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(repairService.getRepairByFactoryId(id));
    }

    @GetMapping("/get-repair-equipment-id/{id}")
    public ResponseEntity<?> getRepairByEquipmentId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(repairService.getRepairByEquipmentId(id));
    }

    @GetMapping("/get-repair-employee-id/{id}")
    public ResponseEntity<?> getRepairByEmployeeId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(repairService.getRepairByEmployeeId(id));
    }

    @GetMapping("/get-repair-status/{factoryId}/{status}")
    public ResponseEntity<?> getRepairByStatus(@PathVariable Integer factoryId, @PathVariable String status){
        return ResponseEntity.status(200).body(repairService.getRepairByStatus(factoryId, status));
    }

    @GetMapping("/get-repair-end-date-range/{factoryId}/{dateTime1}/{dateTime2}")
    public ResponseEntity<?> getRepairByEndDateRange(@PathVariable Integer factoryId, @PathVariable LocalDateTime dateTime1, @PathVariable LocalDateTime dateTime2){
        return ResponseEntity.status(200).body(repairService.getRepairByEndDateRange(dateTime1, dateTime2, factoryId));
    }

    @GetMapping("/get-repair-start-date-range/{factoryId}/{dateTime1}/{dateTime2}")
    public ResponseEntity<?> getRepairByStartDateRange(@PathVariable Integer factoryId,@PathVariable LocalDateTime dateTime1, @PathVariable LocalDateTime dateTime2){
        return ResponseEntity.status(200).body(repairService.getRepairByStartDateRange(dateTime1, dateTime2, factoryId));
    }
}