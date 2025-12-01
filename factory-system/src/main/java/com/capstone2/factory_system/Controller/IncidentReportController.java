package com.capstone2.factory_system.Controller;

import com.capstone2.factory_system.Api.ApiResponse;
import com.capstone2.factory_system.Model.IncidentReport;
import com.capstone2.factory_system.Service.IncidentReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/incident-report")
public class IncidentReportController {
    private final IncidentReportService incidentReportService;

    @GetMapping("/get-incident-reports")
    public ResponseEntity<?> getIncidentReports(){
        return ResponseEntity.status(200).body(incidentReportService.getIncidentReports());
    }

    @PostMapping("/create-incident-report")
    public ResponseEntity<?> createIncidentReport(@RequestBody @Valid IncidentReport incidentReport, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String status = incidentReportService.createIncidentReport(incidentReport);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("incident report was created successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @PutMapping("/update-incident-report/{id}")
    public ResponseEntity<?> updateIncidentReport(@PathVariable Integer id, @RequestBody @Valid IncidentReport incidentReport, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String status = incidentReportService.updateIncidentReport(id, incidentReport);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("incident report was updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @DeleteMapping("/delete-incident-report/{username}/{id}")
    public ResponseEntity<?> deleteIncident(@PathVariable String username, @PathVariable Integer id){
        String status = incidentReportService.deleteIncident(username, id);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("incident report was deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @GetMapping("/get-incident-report-id/{id}")
    public ResponseEntity<?> getIncidentById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(incidentReportService.getIncidentById(id));
    }

    @GetMapping("/get-incident-date-range/{id}/{dateTime1}/{dateTime2}")
    public ResponseEntity<?> getIncidentByDateRange(@PathVariable Integer id, @PathVariable LocalDateTime dateTime1, @PathVariable LocalDateTime dateTime2){
        return ResponseEntity.status(200).body(incidentReportService.getIncidentByDateRange(id, dateTime1, dateTime2));
    }

    @GetMapping("/get-incident-factory-id/{id}")
    public ResponseEntity<?> getIncidentByFactoryId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(incidentReportService.getIncidentByFactoryId(id));
    }

    @GetMapping("/get-incident-reported-by/{id}")
    public ResponseEntity<?> getIncidentByReportedBy(@PathVariable Integer id){
        return ResponseEntity.status(200).body(incidentReportService.getIncidentByReportedBy(id));
    }

    @GetMapping("/get-incident-severity/{id}/{severity}")
    public ResponseEntity<?> getIncidentBySeverity(@PathVariable Integer id, @PathVariable String severity){
        return ResponseEntity.status(200).body(incidentReportService.getIncidentBySeverity(id, severity));
    }

    @GetMapping("/get-incident-type/{id}/{type}")
    public ResponseEntity<?> getIncidentByType(@PathVariable Integer id, @PathVariable String type){
        return ResponseEntity.status(200).body(incidentReportService.getIncidentByType(id, type));
    }
}