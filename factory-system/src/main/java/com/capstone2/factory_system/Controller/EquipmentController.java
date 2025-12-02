package com.capstone2.factory_system.Controller;

import com.capstone2.factory_system.Api.ApiResponse;
import com.capstone2.factory_system.Model.Equipment;
import com.capstone2.factory_system.Service.EquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/equipment")
public class EquipmentController {
    private final EquipmentService equipmentService;

    @GetMapping("/get-equipment")
    public ResponseEntity<?> getEquipment(){
        return ResponseEntity.status(200).body(equipmentService.getEquipment());
    }

    @PostMapping("/create-equipment")
    public ResponseEntity<?> createEquipment(@RequestBody @Valid Equipment equipment){
        equipmentService.createEquipment(equipment);
        return ResponseEntity.status(200).body(new ApiResponse("equipment was created successfully"));

    }

    @PutMapping("/update-equipment/{id}")
    public ResponseEntity<?> updateEquipment(@PathVariable Integer id, @RequestBody @Valid Equipment equipment){
        equipmentService.updateEquipment(id, equipment);
        return ResponseEntity.status(200).body(new ApiResponse("equipment was updated successfully"));

    }

    @DeleteMapping("/delete-equipment/{id}/{username}")
    public ResponseEntity<?> deleteEquipment( @PathVariable Integer id, @PathVariable String username){
        equipmentService.deleteEquipment(username, id);
        return ResponseEntity.status(200).body(new ApiResponse("equipment was deleted successfully"));

    }

    @GetMapping("/get-equipment-id/{id}")
    public ResponseEntity<?> getEquipmentById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(equipmentService.getEquipmentById(id));
    }

    @GetMapping("/get-equipment-factory/{id}")
    public ResponseEntity<?> getByFactoryId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(equipmentService.getByFactoryId(id));
    }

    @PutMapping("/set-retired/{id}/{username}")
    public ResponseEntity<?> setRetired(@PathVariable Integer id, @PathVariable String username){
        equipmentService.setRetired(id, username);
        return ResponseEntity.status(200).body(new ApiResponse("equipment status was set to retired successfully"));
    }

    @PutMapping("/set-under-repair/{id}/{employeeId}")
    public ResponseEntity<?> setUnderRepair(@PathVariable Integer id, @PathVariable Integer employeeId){
        equipmentService.setUnderRepair(id, employeeId);
        return ResponseEntity.status(200).body(new ApiResponse("equipment status was set to under repair successfully"));
    }

    @GetMapping("/get-last-maintenance-range/{id}/{dateTime1}/{dateTime2}")
    public ResponseEntity<?> getLastMaintainByRange( @PathVariable Integer id, @PathVariable LocalDateTime dateTime1, @PathVariable LocalDateTime dateTime2){
        return ResponseEntity.status(200).body(equipmentService.getLastMaintainByRange(dateTime1, dateTime2, id));
    }

    @GetMapping("/get-by-status-factory/{status}/{id}")
    public ResponseEntity<?> getByStatusAndFactory(@PathVariable String status, @PathVariable Integer id){
        return ResponseEntity.status(200).body(equipmentService.getByStatusAndFactory(status, id));
    }
}