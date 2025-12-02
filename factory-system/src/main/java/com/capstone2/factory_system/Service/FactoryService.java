package com.capstone2.factory_system.Service;

import com.capstone2.factory_system.Api.ApiException;
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

    public void createFactory(Factory factory){
        if(getFactoryByFactoryRecordNumber(factory.getFactoryRecordNumber()) != null){
            throw new ApiException("factory already exist");
        }
        if(getFactoryByEmail(factory.getEmail()) != null || getFactoryByUsername(factory.getUsername()) != null){
            throw new ApiException("username or email already exist");
        }
        FactoryRequest fr = factoryRequestService.getFactoryRequestId(factory.getFactoryRequestId());
        if(fr != null && fr.getStatus().equalsIgnoreCase("approved") && fr.getFactoryRecordNumber().equalsIgnoreCase(factory.getFactoryRecordNumber())){
            factory.setCreatedAt(LocalDate.now());
            factory.setCity(fr.getCity());
            factoryRepository.save(factory);
            return;
        }
        throw new ApiException("factory request not yet approved or information doesn't match");
    }

    public void updateFactory(Integer id, Factory factory){
        Factory f = getFactoryById(id);
        if(getFactoryByEmail(factory.getEmail()) != null && !f.getEmail().equalsIgnoreCase(factory.getEmail())
                || getFactoryByUsername(factory.getUsername()) != null && !f.getUsername().equalsIgnoreCase(factory.getUsername())){
            throw new ApiException("username or email already exist");
        }
        FactoryRequest fr = factoryRequestService.getFactoryRequestId(factory.getFactoryRequestId());
        if(fr != null && fr.getStatus().equalsIgnoreCase("approved")){
            f.setEmail(factory.getEmail());
            f.setUsername(factory.getUsername());
            f.setPassword(factory.getPassword());
            f.setFullName(factory.getFullName());
            f.setFactoryName(factory.getFactoryName());
            factoryRepository.save(f);
            return;
        }
        throw new ApiException("factory request not yet approved");
    }


    public void deleteFactory(Integer id, String username){
        Factory f = getFactoryById(id);
        if(f == null){
            throw new ApiException("factory doesn't exist");
        }
        if(!f.getUsername().equalsIgnoreCase(username)){
            throw new ApiException("not authorized to delete");
        }
        factoryRepository.delete(f);

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
