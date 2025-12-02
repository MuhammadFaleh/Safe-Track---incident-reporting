package com.capstone2.factory_system.Repository;

import com.capstone2.factory_system.Model.CorrectiveAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CorrectiveActionRepository extends JpaRepository<CorrectiveAction, Integer> {
    CorrectiveAction findCorrectiveActionByActionId(Integer id);
    List<CorrectiveAction> findCorrectiveActionByIncidentId(Integer incidentId);
    @Query("select c from CorrectiveAction c where c.incidentId=?1 and c.status LIKE %?2%")
    List<CorrectiveAction> findCorrectiveActionByStatus(Integer incidentId, String status);
    @Query("select c from CorrectiveAction c where c.startDate between ?1 and ?2")
    List<CorrectiveAction> findCorrectiveActionStartByDateRange(LocalDateTime dateTime1, LocalDateTime dateTime2);
    @Query("select c from CorrectiveAction c where c.endDate between ?1 and ?2")
    List<CorrectiveAction> findCorrectiveActionEndByDateRange(LocalDateTime dateTime1, LocalDateTime dateTime2);
}
