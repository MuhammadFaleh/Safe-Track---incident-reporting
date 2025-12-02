package com.capstone2.factory_system.Controller;

import com.capstone2.factory_system.Api.ApiResponse;
import com.capstone2.factory_system.Model.Department;
import com.capstone2.factory_system.Service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/get-departments")
    public ResponseEntity<?> getDepartments(){
        return ResponseEntity.status(200).body(departmentService.getDepartments());
    }

    @PostMapping("/create-department")
    public ResponseEntity<?> createDepartment(@RequestBody @Valid Department department){
        departmentService.createDepartment(department);
        return ResponseEntity.status(200).body(new ApiResponse("department was created successfully"));
    }

    @PutMapping("/update-department/{id}/{username}")
    public ResponseEntity<?> updateDepartment(@PathVariable Integer id, @PathVariable String username, @RequestBody @Valid Department department){

        departmentService.updateDepartment(id, department, username);
        return ResponseEntity.status(200).body(new ApiResponse("department was updated successfully"));
    }

    @DeleteMapping("/delete-department/{id}/{username}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Integer id, @PathVariable String username){
        departmentService.deleteDepartment(id, username);
        return ResponseEntity.status(200).body(new ApiResponse("department was deleted successfully"));

    }

    @GetMapping("/get-department-id/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(departmentService.getDepartmentById(id));
    }

    @GetMapping("/get-departments-type/{factoryId}/{type}")
    public ResponseEntity<?> getDepartmentsByTypeAndFactoryId(@PathVariable Integer factoryId, @PathVariable String type){
        return ResponseEntity.status(200).body(departmentService.getDepartmentsByTypeAndFactoryId(factoryId, type));
    }

    @GetMapping("/get-department-name/{factoryId}/{name}")
    public ResponseEntity<?> getDepartmentByNameAndFactoryId(@PathVariable Integer factoryId, @PathVariable String name){
        return ResponseEntity.status(200).body(departmentService.getDepartmentByNameAndFactoryId(factoryId, name));
    }
}