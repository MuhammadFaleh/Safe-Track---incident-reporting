package com.capstone2.factory_system.Repository;

import com.capstone2.factory_system.Model.Factory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Integer> {
    Factory findFactoryByFactoryId(Integer factoryId);
    Factory findFactoryByFactoryRecordNumber(String factory_record_number);
    List<Factory> findFactoriesByCity(String city);
    @Query("select f from Factory f where f.createdAt = ?1")
    List<Factory> findFactoriesByDate(LocalDate date);
    Factory findFactoryByUsername(String username);
    Factory findFactoryByEmail(String email);
    List<Factory> findFactoriesByFactoryName(String factoryName);
    Factory findFactoryByFactoryRequestId(Integer factoryRequestId);
}
