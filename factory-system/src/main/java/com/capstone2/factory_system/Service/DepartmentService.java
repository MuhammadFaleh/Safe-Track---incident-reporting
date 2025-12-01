package com.capstone2.factory_system.Service;

import com.capstone2.factory_system.Model.Department;
import com.capstone2.factory_system.Model.Factory;
import com.capstone2.factory_system.Repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final FactoryService factoryService;

    public List<Department> getDepartments(){
        return departmentRepository.findAll();
    }

    public String createDepartment(Department department){
        if(factoryService.getFactoryById(department.getFactoryId()) != null){
            if(!checkDepartmentNameExist(department.getFactoryId(), department.getName())){
                departmentRepository.save(department);
                return "success";
            }
            return "department name already exist";
        }
        return "factory doesn't exist";

    }

    public String updateDepartment(Integer id, Department department, String username){
        Factory f = factoryService.getFactoryByUsername(username);
        if( f == null || !f.getFactoryId().equals(department.getFactoryId())){
            return "factory doesn't exist or you don't have authorization";
        }
        Department d = getDepartmentById(id);
        if(d != null){
                if(d.getName().equalsIgnoreCase(department.getName()) || checkDepartmentNameExist(department.getFactoryId(), department.getName())){
                    d.setName(department.getName());
                    d.setType(department.getType());
                    departmentRepository.save(d);
                    return "success";
                }
                return "department name already exist";

        }
        return "department doesn't exist";
    }

    public String deleteDepartment(Integer id, String username){
        Factory f = factoryService.getFactoryByUsername(username);
        Department d = getDepartmentById(id);
        if(d == null){
            return "department doesn't exist";
        }
        if( f == null || !f.getFactoryId().equals(d.getFactoryId())){
            return "factory doesn't exist or you don't have authorization";
        }
        departmentRepository.delete(d);
        return "success";

    }

    public boolean checkDepartmentNameExist(Integer factoryId, String name){
        return departmentRepository.findDepartmentsByFactoryId(factoryId).stream().anyMatch(e-> e.getName().equalsIgnoreCase(name));
    }

    public List<Department> getDepartmentsByTypeAndFactoryId(Integer factoryId, String type){
        return departmentRepository.findDepartmentByFactoryIdAndType(factoryId,type);
    }

    public Department getDepartmentByNameAndFactoryId(Integer factoryId, String name){
        return departmentRepository.findDepartmentByFactoryIdAndName(factoryId,name);
    }

    public Department getDepartmentById(Integer id){
        return departmentRepository.findDepartmentByDepartmentId(id);
    }
}
