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
    public ResponseEntity<?> createAdmin(@RequestBody @Valid Admin admin){
        adminService.createAdmin(admin);
        return ResponseEntity.status(200).body(new ApiResponse("admin was created successfully"));

    }
    @PutMapping("/update-admin/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable Integer id, @RequestBody @Valid Admin admin){

        adminService.updateAdmin(id,admin);
        return ResponseEntity.status(200).body(new ApiResponse("admin was updated successfully"));
    }
    @DeleteMapping("/delete-admin/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id){
        adminService.deleteAdmin(id);
        return ResponseEntity.status(200).body(new ApiResponse("admin was deleted successfully"));
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

