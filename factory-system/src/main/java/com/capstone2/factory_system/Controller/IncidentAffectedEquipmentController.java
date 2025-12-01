package com.capstone2.factory_system.Controller;

import com.capstone2.factory_system.Api.ApiResponse;
import com.capstone2.factory_system.Model.IncidentAffectedEquipment;
import com.capstone2.factory_system.Service.IncidentAffectedEquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/incident-affected-equipment")
public class IncidentAffectedEquipmentController {
    private final IncidentAffectedEquipmentService incidentAffectedEquipmentService;

    @GetMapping("/get-incident-affected-equipment")
    public ResponseEntity<?> getIncidentAffectedEqu(){
        return ResponseEntity.status(200).body(incidentAffectedEquipmentService.getIncidentAffectedEqu());
    }

    @PostMapping("/create-incident-affected-equipment")
    public ResponseEntity<?> createIncidentAffectedEqu(@RequestBody @Valid IncidentAffectedEquipment incidentAffectedEquipment, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String status = incidentAffectedEquipmentService.createIncidentAffectedEqu(incidentAffectedEquipment);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("incident affected equipment was created successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @PutMapping("/update-incident-affected-equipment/{id}")
    public ResponseEntity<?> updateIncidentAffectedEqu(@PathVariable Integer id, @RequestBody @Valid IncidentAffectedEquipment incidentAffectedEquipment, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String status = incidentAffectedEquipmentService.updateIncidentAffectedEqu(id, incidentAffectedEquipment);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("incident affected equipment was updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @DeleteMapping("/delete-incident-affected-equipment/{id}/{username}")
    public ResponseEntity<?> deleteIncidentAffectedEqu(@PathVariable Integer id, @PathVariable String username){
        String status = incidentAffectedEquipmentService.deleteIncidentAffectedEqu(id, username);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("incident affected equipment was deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @GetMapping("/get-incident-affected-equipment-id/{id}")
    public ResponseEntity<?> getIncidentById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(incidentAffectedEquipmentService.getIncidentById(id));
    }

    @GetMapping("/get-by-severity-incident/{id}/{severity}")
    public ResponseEntity<?> getIncidentBySeverityAndIncidentId(@PathVariable Integer id, @PathVariable String severity){
        return ResponseEntity.status(200).body(incidentAffectedEquipmentService.getIncidentBySeverityAndIncidentId(id, severity));
    }

    @GetMapping("/get-by-incident-id/{id}")
    public ResponseEntity<?> getByIncidentId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(incidentAffectedEquipmentService.getByIncidentId(id));
    }

    @GetMapping("/get-by-equipment-id/{id}")
    public ResponseEntity<?> getByEquipmentId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(incidentAffectedEquipmentService.getByEquipmentId(id));
    }
}