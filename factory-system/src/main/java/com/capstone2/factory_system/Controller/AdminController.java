package com.capstone2.factory_system.Controller;

import com.capstone2.factory_system.Api.ApiResponse;
import com.capstone2.factory_system.Model.Admin;
import com.capstone2.factory_system.Service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/get-admins")
    public ResponseEntity<?> getAdmins(){
        return ResponseEntity.status(200).body(adminService.getAdmins());
    }

    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody @Valid Admin admin, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String status = adminService.createAdmin(admin);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("admin was created successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }
    @PutMapping("/update-admin/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Integer id, @RequestBody @Valid Admin admin, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String status = adminService.updateAdmin(id,admin);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("admin was updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }
    @DeleteMapping("/delete-admin/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id){
        String status = adminService.deleteAdmin(id);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("admin was deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @GetMapping("/get-admin-id/{id}")
    public ResponseEntity<?> getAdminsById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(adminService.getAdminById(id));
    }
    @GetMapping("/get-admin-email/{email}")
    public ResponseEntity<?> getAdminsByEmail(@PathVariable String email){
        return ResponseEntity.status(200).body(adminService.getAdminByEmail(email));
    }

    @GetMapping("/get-admin-username/{username}")
    public ResponseEntity<?> getAdminsByUsername(@PathVariable String username){
        return ResponseEntity.status(200).body(adminService.getAdminByUsername(username));
    }
}
