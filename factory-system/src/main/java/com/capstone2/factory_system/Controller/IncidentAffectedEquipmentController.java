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
    public ResponseEntity<?> createIncidentAffectedEqu(@RequestBody @Valid IncidentAffectedEquipment incidentAffectedEquipment){
        incidentAffectedEquipmentService.createIncidentAffectedEqu(incidentAffectedEquipment);
        return ResponseEntity.status(200).body(new ApiResponse("incident affected equipment was created successfully"));
    }

    @PutMapping("/update-incident-affected-equipment/{id}")
    public ResponseEntity<?> updateIncidentAffectedEqu(@PathVariable Integer id, @RequestBody @Valid IncidentAffectedEquipment incidentAffectedEquipment){
        incidentAffectedEquipmentService.updateIncidentAffectedEqu(id, incidentAffectedEquipment);
        return ResponseEntity.status(200).body(new ApiResponse("incident affected equipment was updated successfully"));

    }

    @DeleteMapping("/delete-incident-affected-equipment/{id}/{username}")
    public ResponseEntity<?> deleteIncidentAffectedEqu(@PathVariable Integer id, @PathVariable String username){
        incidentAffectedEquipmentService.deleteIncidentAffectedEqu(id, username);
        return ResponseEntity.status(200).body(new ApiResponse("incident affected equipment was deleted successfully"));
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