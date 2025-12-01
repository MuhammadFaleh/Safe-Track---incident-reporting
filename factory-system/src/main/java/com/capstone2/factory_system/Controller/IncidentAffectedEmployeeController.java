package com.capstone2.factory_system.Controller;

import com.capstone2.factory_system.Api.ApiResponse;
import com.capstone2.factory_system.Model.IncidentAffectedEmployee;
import com.capstone2.factory_system.Service.IncidentAffectedEmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/incident-affected-employee")
public class IncidentAffectedEmployeeController {
    private final IncidentAffectedEmployeeService incidentAffectedEmployeeService;

    @GetMapping("/get-incident-affected-employees")
    public ResponseEntity<?> getIncidentAffectedEmp(){
        return ResponseEntity.status(200).body(incidentAffectedEmployeeService.getIncidentAffectedEmp());
    }

    @PostMapping("/create-incident-affected-employee")
    public ResponseEntity<?> createIncidentAffectedEmp(@RequestBody @Valid IncidentAffectedEmployee incidentAffectedEmployee, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String status = incidentAffectedEmployeeService.createIncidentAffectedEmp(incidentAffectedEmployee);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("incident affected employee was created successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @PutMapping("/update-incident-affected-employee/{id}")
    public ResponseEntity<?> updateIncidentAffectedEmp(@PathVariable Integer id, @RequestBody @Valid IncidentAffectedEmployee incidentAffectedEmployee, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String status = incidentAffectedEmployeeService.updateIncidentAffectedEmp(id, incidentAffectedEmployee);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("incident affected employee was updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @DeleteMapping("/delete-incident-affected-employee/{id}/{username}")
    public ResponseEntity<?> deleteIncidentAffectedEmp(@PathVariable Integer id, @PathVariable String username){
        String status = incidentAffectedEmployeeService.deleteIncidentAffectedEmp(id, username);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("incident affected employee was deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @GetMapping("/get-incident-affected-employee-id/{id}")
    public ResponseEntity<?> getIncidentById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(incidentAffectedEmployeeService.getIncidentById(id));
    }

    @GetMapping("/get-by-severity-incident/{id}/{severity}")
    public ResponseEntity<?> getIncidentBySeverityAndIncidentId(@PathVariable Integer id, @PathVariable String severity){
        return ResponseEntity.status(200).body(incidentAffectedEmployeeService.getIncidentBySeverityAndIncidentId(id, severity));
    }

    @GetMapping("/get-by-incident-id/{id}")
    public ResponseEntity<?> getByIncidentId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(incidentAffectedEmployeeService.getByIncidentId(id));
    }

    @GetMapping("/get-by-employee-id/{id}")
    public ResponseEntity<?> getByEmployeeId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(incidentAffectedEmployeeService.getByEmployeeId(id));
    }
}