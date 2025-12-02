package com.capstone2.factory_system.Controller;

import com.capstone2.factory_system.Api.ApiResponse;
import com.capstone2.factory_system.Model.CorrectiveAction;
import com.capstone2.factory_system.Service.CorrectiveActionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/corrective-action")
public class CorrectiveActionController {
    private final CorrectiveActionService correctiveActionService;

    @GetMapping("/get-corrective-actions")
    public ResponseEntity<?> getCorrectiveActions(){
        return ResponseEntity.status(200).body(correctiveActionService.getCorrectiveActions());
    }

    @PostMapping("/create-corrective-action")
    public ResponseEntity<?> createCorrectiveAction(@RequestBody @Valid CorrectiveAction correctiveAction){
        correctiveActionService.createCorrectiveAction(correctiveAction);
        return ResponseEntity.status(200).body(new ApiResponse("corrective action was created successfully"));
    }

    @PutMapping("/update-corrective-action/{id}")
    public ResponseEntity<?> updateCorrectiveAction(@PathVariable Integer id, @RequestBody @Valid CorrectiveAction correctiveAction){

        correctiveActionService.updateCorrectiveAction(id, correctiveAction);
        return ResponseEntity.status(200).body(new ApiResponse("corrective action was updated successfully"));
    }

    @DeleteMapping("/delete-corrective-action/{id}")
    public ResponseEntity<?> deleteCorrectiveAction(@PathVariable Integer id){
        correctiveActionService.deleteCorrectiveAction(id);
        return ResponseEntity.status(200).body(new ApiResponse("corrective action was deleted successfully"));
    }

    @GetMapping("/get-corrective-action-id/{id}")
    public ResponseEntity<?> getCorrectiveActionById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(correctiveActionService.getCorrectiveActionById(id));
    }

    @GetMapping("/get-corrective-action-IncidentId-status/{id}/{status}")
    public ResponseEntity<?> getCorrectiveActionByIncidentIdAndStatus(@PathVariable Integer id,@PathVariable String status){
        return ResponseEntity.status(200).body(correctiveActionService.getCorrectiveActionByStatusAndIncidentId(id, status));
    }
    @GetMapping("/get-corrective-action-IncidentId/{id}")
    public ResponseEntity<?> getCorrectiveActionByIncidentId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(correctiveActionService.getCorrectiveActionByIncidentId(id));
    }
    @PutMapping("/change-status/{id}/{empId}")
    public ResponseEntity<?> changeStatus(@PathVariable Integer id, @PathVariable Integer empId){
        correctiveActionService.changeStatus(id, empId);
        return ResponseEntity.status(200).body(new ApiResponse("corrective action status was changed successfully"));
    }

}