package com.capstone2.factory_system.Repository;

import com.capstone2.factory_system.Model.IncidentAffectedEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidentAffectedEquipmentRepository extends JpaRepository<IncidentAffectedEquipment, Integer> {
    IncidentAffectedEquipment findIncidentAffectedEquipmentById(Integer id);

    @Query("select d from IncidentAffectedEquipment d where d.incidentId=?1 and d.severity LIKE %?2%")
    List<IncidentAffectedEquipment> findIncidentAffectedEquipmentBySeverity(Integer incidentId,String severity);
    List<IncidentAffectedEquipment> findIncidentAffectedEquipmentByIncidentId(Integer incidentId);
    IncidentAffectedEquipment findIncidentAffectedEquipmentByEquipmentId(Integer EquipmentId);
}
