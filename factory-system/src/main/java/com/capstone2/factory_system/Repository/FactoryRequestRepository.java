package com.capstone2.factory_system.Repository;

import com.capstone2.factory_system.Model.FactoryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FactoryRequestRepository extends JpaRepository<FactoryRequest, Integer> {
    FactoryRequest findFactoryRequestByFactoryRequestId(Integer factoryRequestId);
    List<FactoryRequest> findFactoryByFactoryRecordNumber(String factoryRecordNumber);
    @Query("SELECT f FROM FactoryRequest f where f.factoryRecordNumber=?1 ORDER BY f.requestDate DESC LIMIT 1")
    FactoryRequest findFactoryRequestNewest(String factoryRecordNumber);
    List<FactoryRequest> findFactoryRequestByCity(String city);
    @Query("select f from FactoryRequest f where f.requestDate between ?1 and ?2")
    List<FactoryRequest> findFactoryRequestByRequestDate(LocalDateTime dateTime, LocalDateTime dateTime2);
    @Query("select f from FactoryRequest f where f.endDate between ?1 and ?2")
    List<FactoryRequest> findFactoryRequestByEndDate(LocalDateTime dateTime, LocalDateTime dateTime2);
    List<FactoryRequest> findFactoryRequestByFactoryName(String factoryName);
    List<FactoryRequest> findFactoryRequestByStatus(String status);
}
