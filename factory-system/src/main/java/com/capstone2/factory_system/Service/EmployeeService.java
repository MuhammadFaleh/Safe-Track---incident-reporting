package com.capstone2.factory_system.Service;

import com.capstone2.factory_system.Api.ApiException;
import com.capstone2.factory_system.Model.Department;
import com.capstone2.factory_system.Model.Employee;
import com.capstone2.factory_system.Model.Factory;
import com.capstone2.factory_system.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final FactoryService factoryService;

    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    public void createEmployee(Employee employee){
        Department d = departmentService.getDepartmentById(employee.getDepartmentId());
        if(d != null && d.getFactoryId().equals(employee.getFactoryId())){
            if(getEmployeeByEmail(employee.getEmail()) == null){
                Employee e = getEmployeeById(employee.getManagerId());
                if(employee.getManagerId() == null ||  e != null && e.getFactoryId().equals(employee.getFactoryId())){
                    employeeRepository.save(employee);
                    return;
                }
                throw new ApiException("invalid manager id");
            }
            throw new ApiException("email exist");
        }
        throw new ApiException("department or factory don't exist");
    }

    public void updateEmployee(Integer id, Employee employee){
        Department d = departmentService.getDepartmentById(employee.getDepartmentId());
        Employee e = getEmployeeById(id);
        if(e == null){
            throw new ApiException("no employee record found");
        }
        if(d != null && d.getFactoryId().equals(employee.getFactoryId())){
            if(e.getEmail().equalsIgnoreCase(employee.getEmail()) || getEmployeeByEmail(employee.getEmail()) == null){
                Employee manager = getEmployeeById(employee.getManagerId());
                if(employee.getManagerId() == null ||  manager != null && manager.getFactoryId().equals(employee.getFactoryId())){
                    e.setDepartmentId(employee.getDepartmentId());
                    e.setEmail(employee.getEmail());
                    e.setFullName(employee.getFullName());
                    e.setGender(employee.getGender());
                    e.setManagerId(employee.getManagerId());
                    e.setFactoryId(employee.getFactoryId());
                    employeeRepository.save(e);
                    return;
                }
                throw new ApiException("invalid manager id");
            }
            throw new ApiException("email exist");
        }
        throw new ApiException("department or factory don't exist");
    }

    public void deleteEmployee(String username, Integer id){
        Employee e = getEmployeeById(id);
        Factory f = factoryService.getFactoryByUsername(username);
        if(e == null){
            throw new ApiException("no record found");
        }
        if(f != null && f.getFactoryId().equals(e.getFactoryId())){
            employeeRepository.delete(e);
            return;
        }
        throw new ApiException("unauthorized");
    }
    public Employee getEmployeeByEmail(String email){
        return employeeRepository.findEmployeeByEmail(email);
    }

    public Employee getEmployeeById(Integer id){
        return employeeRepository.findEmployeeByEmployeeId(id);
    }

    public boolean checkIfSameFactory(Integer id, Integer factoryId){
        Employee e = getEmployeeById(id);

        if(e == null){
            return false;
        }
        if(e.getFactoryId().equals(factoryId)){
            Department d = departmentService.getDepartmentById(e.getDepartmentId());
            if(d.getType().equalsIgnoreCase("Maintenance") || d.getType().equalsIgnoreCase("Management")){
                return true;
            }
            return false;
        }
        return false;
    }

    public List<Employee> getEmployeeByManagerId(Integer id){
        return employeeRepository.findEmployeesByManagerId(id);
    }

    public List<Employee> getEmployeesByDepartmentId(Integer id){
        return employeeRepository.findEmployeesByDepartmentId(id);
    }

    public List<Employee> getEmployeesByFactoryId(Integer id){
        return employeeRepository.findEmployeesByFactoryId(id);
    }
}
