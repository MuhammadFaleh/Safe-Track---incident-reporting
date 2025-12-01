package com.capstone2.factory_system.Service;

import com.capstone2.factory_system.Model.Equipment;
import com.capstone2.factory_system.Model.Factory;
import com.capstone2.factory_system.Repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final EmployeeService employeeService;
    private final FactoryService factoryService;

    public List<Equipment> getEquipment(){
        return equipmentRepository.findAll();
    }

    public String createEquipment(Equipment equipment){
        Factory f = factoryService.getFactoryById(equipment.getFactoryId());
        if(f != null){
            if(equipment.getPurchaseDate() == null ){
                equipment.setPurchaseDate(LocalDateTime.now());

            }

            if(equipment.getLastMaintenance() == null ){
                equipment.setLastMaintenance(LocalDateTime.now());
            }
            equipment.setStatus("Working");
            equipmentRepository.save(equipment);
            return "success";
        }
        return "factory doesn't exist";
    }

    public String updateEquipment(Integer id, Equipment equipment){
        Equipment e = getEquipmentById(id);
        if(e!=null){
            e.setCategory(equipment.getCategory());
            e.setLastMaintenance(equipment.getLastMaintenance());
            e.setStatus(equipment.getStatus());
            e.setSerialNumber(equipment.getSerialNumber());
            equipmentRepository.save(e);
            return "success";
        }
        return "no equipment exist";
    }

    public String deleteEquipment(String username, Integer id){
        Equipment e = getEquipmentById(id);
        Factory f = factoryService.getFactoryByUsername(username);
        if(e == null){
            return "no record found";
        }
        if(f != null && f.getFactoryId().equals(e.getFactoryId())){
            equipmentRepository.delete(e);
            return "success";
        }
        return "unauthorized";
    }

    public String setRetired(Integer id, Integer employeeId){
        Equipment e = getEquipmentById(id);
        if(e!=null){
            if(employeeService.checkIfSameFactory(employeeId, e.getFactoryId())){
                e.setStatus("Retired");
                equipmentRepository.save(e);
                return "success";
            }
            return "unauthorized";

        }
        return "no equipment exist";

    }


    public String setUnderRepair(Integer id, Integer employeeId){
        Equipment e = getEquipmentById(id);
        if(e!=null){
            if(employeeService.checkIfSameFactory(employeeId, e.getFactoryId())){
                e.setStatus("Under Repair");
                equipmentRepository.save(e);
                return "success";
            }
            return "unauthorized";

        }
        return "no equipment exist";

    }
    public List<Equipment> getByFactoryId(Integer id){
        return equipmentRepository.findEquipmentByFactoryIdNewest(id);
    }

    public Equipment getEquipmentById(Integer id){
        return equipmentRepository.findEquipmentByEquipmentId(id);
    }

    public List<Equipment> getLastMaintainByRange(LocalDateTime dateTime1, LocalDateTime dateTime2,Integer id){
        return equipmentRepository.findEquipmentByLastMaintenanceDateRangeAndFactoryId(dateTime1,dateTime2,id);
    }

    public List<Equipment> getByStatusAndFactory(String status, Integer id){
        return equipmentRepository.findEquipmentByStatusAndFactoryId(status, id);
    }



}
