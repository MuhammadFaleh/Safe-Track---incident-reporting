package com.capstone2.factory_system.Repository;

import com.capstone2.factory_system.Model.IncidentAffectedEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentAffectedEmployeeRepository extends JpaRepository<IncidentAffectedEmployee, Integer> {
    IncidentAffectedEmployee findIncidentAffectedEmployeeById(Integer id);
    @Query("select d from IncidentAffectedEmployee d where d.incidentId=?1 and d.severity LIKE %?2%")
    List<IncidentAffectedEmployee> findIncidentAffectedEmployeeBySeverity(Integer incidentId,String severity);
    List<IncidentAffectedEmployee> findIncidentAffectedEmployeeByIncidentId(Integer incidentId);
    List<IncidentAffectedEmployee> findIncidentAffectedEmployeeByEmployeeId(Integer employeeId);

}
