package com.capstone2.factory_system.Repository;

import com.capstone2.factory_system.Model.IncidentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IncidentReportRepository extends JpaRepository<IncidentReport, Integer> {
    IncidentReport findIncidentReportByIncidentId(Integer id);
    @Query("select i from IncidentReport i where i.factoryId=?1 and i.type LIKE %?2%")
    List<IncidentReport> findIncidentReportByType(Integer id, String type);
    @Query("select i from IncidentReport i where i.factoryId=?1 and i.severity LIKE %?2%")
    List<IncidentReport> findIncidentReportBySeverity(Integer id, String severity);
    @Query("select i from IncidentReport i where i.factoryId=?1 and i.dateReported between ?2 and ?3")
    List<IncidentReport> findIncidentReportByRequestDate(Integer factoryId,LocalDateTime dateTime, LocalDateTime dateTime2);
    List<IncidentReport> findIncidentReportByFactoryId(Integer factoryId);
    List<IncidentReport> findIncidentReportByReportedBy(Integer reportedBy);
}
