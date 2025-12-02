package com.capstone2.factory_system.Repository;

import com.capstone2.factory_system.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findAdminByAdminId(Integer id);
    Admin findAdminByEmail(String email);
    Admin findAdminByUsername(String username);
}
