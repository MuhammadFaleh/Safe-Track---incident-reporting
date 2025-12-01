package com.capstone2.factory_system.Repository;

import com.capstone2.factory_system.Model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findDepartmentByDepartmentId(Integer departmentId);
    List<Department> findDepartmentsByType(String type);
    List<Department> findDepartmentsByFactoryId(Integer factoryId);
    List<Department> findDepartmentsByName(String name);
    @Query("select d from Department d where d.factoryId=?1 and d.name LIKE %?2%")
    Department findDepartmentByFactoryIdAndName(Integer factoryId, String name);
    @Query("select d from Department d where d.factoryId=?1 and d.type LIKE %?2%")
    List<Department> findDepartmentByFactoryIdAndType(Integer factoryId, String type);
}
