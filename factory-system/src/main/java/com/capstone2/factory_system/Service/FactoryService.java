package com.capstone2.factory_system.Service;

import com.capstone2.factory_system.Model.Factory;
import com.capstone2.factory_system.Model.FactoryRequest;
import com.capstone2.factory_system.Repository.FactoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FactoryService {
    private final FactoryRepository factoryRepository;
    private final FactoryRequestService factoryRequestService;

    public List<Factory> getFactories(){
        return factoryRepository.findAll();
    }

    public String createFactory(Factory factory){
        if(getFactoryByFactoryRecordNumber(factory.getFactoryRecordNumber()) != null){
            return "factory already exist";
        }
        if(getFactoryByEmail(factory.getEmail()) != null || getFactoryByUsername(factory.getUsername()) != null){
            return "username or email already exist";
        }
        FactoryRequest fr = factoryRequestService.getFactoryRequestId(factory.getFactoryRequestId());
        if(fr != null && fr.getStatus().equalsIgnoreCase("approved")){
            factory.setCreatedAt(LocalDate.now());
            factoryRepository.save(factory);
            return "success";
        }
        return "factory request not yet approved";
    }

    public String updateFactory(Integer id, Factory factory){
        Factory f = getFactoryById(id);
        if(getFactoryByFactoryRecordNumber(factory.getFactoryRecordNumber()) != null && !factory.getFactoryRecordNumber().equalsIgnoreCase(f.getFactoryRecordNumber())){
            return "factory record number is incorrect";
        }
        if(getFactoryByEmail(factory.getEmail()) != null && !f.getEmail().equalsIgnoreCase(factory.getEmail())
                || getFactoryByUsername(factory.getUsername()) != null && !f.getUsername().equalsIgnoreCase(factory.getUsername())){
            return "username or email already exist";
        }
        FactoryRequest fr = factoryRequestService.getFactoryRequestId(factory.getFactoryRequestId());
        if(fr != null && fr.getStatus().equalsIgnoreCase("approved")){
            f.setEmail(factory.getEmail());
            f.setUsername(factory.getUsername());
            f.setCity(factory.getCity());
            f.setPassword(factory.getPassword());
            f.setFullName(factory.getFullName());
            f.setFactoryName(factory.getFactoryName());
            factoryRepository.save(f);
            return "success";
        }
        return "factory request not yet approved";
    }

    public Factory getFactoryById(Integer id){
        return factoryRepository.findFactoryByFactoryId(id);
    }

    public Factory getFactoryByUsername(String username){
        return factoryRepository.findFactoryByUsername(username);
    }

    public Factory getFactoryByEmail(String email){
        return factoryRepository.findFactoryByEmail(email);
    }
    public Factory getFactoryByFactoryRecordNumber(String factoryNumber){
        return factoryRepository.findFactoryByFactoryRecordNumber(factoryNumber);
    }
    public List<Factory> getFactoryByCity(String city){
        return factoryRepository.findFactoriesByCity(city);
    }
    public List<Factory> getFactoryByName(String Name){
        return factoryRepository.findFactoriesByFactoryName(Name);
    }
    public List<Factory> getFactoryByDate(LocalDate date){
        return factoryRepository.findFactoriesByDate(date);
    }

    public Factory getFactoryByRequestId(Integer id){
        return factoryRepository.findFactoryByFactoryRequestId(id);
    }
}
