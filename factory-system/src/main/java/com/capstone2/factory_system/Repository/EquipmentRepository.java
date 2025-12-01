package com.capstone2.factory_system.Repository;

import com.capstone2.factory_system.Model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    Equipment findEquipmentByEquipmentId(Integer equipmentId);
    @Query("SELECT e FROM Equipment e where e.factoryId=?1 order by e.purchaseDate desc")
    List<Equipment> findEquipmentByFactoryIdNewest(Integer factoryId);
    @Query("select e from Equipment e where e.factoryId=?3 and e.lastMaintenance between ?1 and ?2")
    List<Equipment> findEquipmentByLastMaintenanceDateRangeAndFactoryId(LocalDateTime dateTime1, LocalDateTime dateTime2,Integer factoryId);
    @Query("select e from Equipment e where e.status LIKE %?1% and e.factoryId=?2")
    List<Equipment> findEquipmentByStatusAndFactoryId(String status, Integer factoryId);

}
