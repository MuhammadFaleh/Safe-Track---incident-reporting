package com.capstone2.factory_system.Controller;

import com.capstone2.factory_system.Api.ApiResponse;
import com.capstone2.factory_system.Model.Employee;
import com.capstone2.factory_system.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/get-employees")
    public ResponseEntity<?> getEmployees(){
        return ResponseEntity.status(200).body(employeeService.getEmployees());
    }

    @PostMapping("/create-employee")
    public ResponseEntity<?> createEmployee(@RequestBody @Valid Employee employee, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String status = employeeService.createEmployee(employee);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("employee was created successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @PutMapping("/update-employee/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer id, @RequestBody @Valid Employee employee, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        String status = employeeService.updateEmployee(id, employee);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("employee was updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @DeleteMapping("/delete-employee/{id}/{username}")
    public ResponseEntity<?> deleteEmployee( @PathVariable Integer id, @PathVariable String username){
        String status = employeeService.deleteEmployee(username, id);
        if(status.equalsIgnoreCase("success")){
            return ResponseEntity.status(200).body(new ApiResponse("employee was deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse(status));
    }

    @GetMapping("/get-employee-id/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Integer id){
        return ResponseEntity.status(200).body(employeeService.getEmployeeById(id));
    }

    @GetMapping("/get-employee-email/{email}")
    public ResponseEntity<?> getEmployeeByEmail(@PathVariable String email){
        return ResponseEntity.status(200).body(employeeService.getEmployeeByEmail(email));
    }
    @GetMapping("/get-employee-manger/{id}")
    public ResponseEntity<?> getEmployeeByManager(@PathVariable Integer id){
        return ResponseEntity.status(200).body(employeeService.getEmployeeByManagerId(id));
    }
    @GetMapping("/get-employee-department/{id}")
    public ResponseEntity<?> getEmployeeByDepartment(@PathVariable Integer id){
        return ResponseEntity.status(200).body(employeeService.getEmployeesByDepartmentId(id));
    }
    @GetMapping("/get-employee-factory/{id}")
    public ResponseEntity<?> getEmployeeByFactoryId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(employeeService.getEmployeesByFactoryId(id));
    }
}