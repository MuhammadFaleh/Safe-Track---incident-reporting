package com.capstone2.factory_system.Service;

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

    public String createAdmin(Admin admin){
        if(getAdminByEmail(admin.getEmail()) != null || getAdminByUsername(admin.getUsername()) != null){
            return "email or username is in use";
        }
        adminRepository.save(admin);
        return "success";
    }

    public String updateAdmin(Integer id, Admin admin){
        Admin a = getAdminById(id);
        if(a != null){
            if(a.getEmail().equalsIgnoreCase(admin.getEmail()) || getAdminByEmail(admin.getEmail()) == null ){
                if(a.getUsername().equalsIgnoreCase(admin.getUsername()) || getAdminByUsername(admin.getUsername()) == null){
                    a.setEmail(admin.getEmail());
                    a.setPassword(admin.getPassword());
                    a.setUsername(admin.getUsername());
                    adminRepository.save(a);
                    return "success";
                }
                return "username or email not available";
            }
        }
        return "user doesn't exist";
    }

    public String deleteAdmin(Integer id){
        Admin a = getAdminById(id);
        if(a != null){
            adminRepository.delete(a);
            return "success";
        }
        return "user doesn't exist";
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
