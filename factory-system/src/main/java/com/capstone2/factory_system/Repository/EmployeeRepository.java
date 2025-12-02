package com.capstone2.factory_system.Repository;

import com.capstone2.factory_system.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findEmployeeByEmployeeId(Integer employeeId);
    List<Employee> findEmployeesByFactoryId(Integer factoryId);
    List<Employee> findEmployeesByDepartmentId(Integer departmentId);
    Employee findEmployeeByEmail(String email);
    List<Employee> findEmployeesByManagerId(Integer managerId);

}
