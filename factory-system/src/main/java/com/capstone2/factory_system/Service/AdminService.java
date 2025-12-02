package com.capstone2.factory_system.Service;

import com.capstone2.factory_system.Api.ApiException;
import com.capstone2.factory_system.Model.Admin;
import com.capstone2.factory_system.Repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public List<Admin> getAdmins(){
        return adminRepository.findAll();
    }

    public void createAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public void updateAdmin(Integer id, Admin admin){
        Admin a = getAdminById(id);
        if(a == null){
            throw new ApiException("user doesn't exist");
        }
        a.setEmail(admin.getEmail());
        a.setPassword(admin.getPassword());
        a.setUsername(admin.getUsername());
        adminRepository.save(a);
    }

    public void deleteAdmin(Integer id){
        Admin a = getAdminById(id);
        if(a == null){

            throw new ApiException("user doesn't exist");
        }
        adminRepository.delete(a);
    }

    public Admin getAdminByEmail(String email){
        return adminRepository.findAdminByEmail(email);
    }

    public Admin getAdminById(Integer id){
        return adminRepository.findAdminByAdminId(id);
    }

    public Admin getAdminByUsername(String username){
        return adminRepository.findAdminByUsername(username);
    }
}
