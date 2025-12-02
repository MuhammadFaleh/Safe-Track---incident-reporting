package com.capstone2.factory_system.Repository;

import com.capstone2.factory_system.Model.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Integer> {
    Repair findRepairByRepairId(Integer id);
    @Query("select r from Repair r where r.factoryId=?2 and r.status LIKE %?1%")
    List<Repair> findRepairByStatus(String status, Integer id);
    List<Repair> findRepairByFactoryId(Integer factoryId);
    List<Repair> findRepairByEmployeeId(Integer employeeId);
    List<Repair> findRepairByEquipmentId(Integer equipmentId);
    @Query("select r from Repair r where r.factoryId=?3 and r.startDate between ?1 and ?2")
    List<Repair> findRepairByStartDateRange(LocalDateTime dateTime, LocalDateTime dateTime2, Integer id);
    @Query("select r from Repair r where r.factoryId=?3 and r.endDate between ?1 and ?2")
    List<Repair> findRepairByEndDateRange(LocalDateTime dateTime, LocalDateTime dateTime2, Integer id);

}
