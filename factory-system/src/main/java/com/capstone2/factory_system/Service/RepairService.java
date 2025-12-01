package com.capstone2.factory_system.Service;

import com.capstone2.factory_system.Model.Equipment;
import com.capstone2.factory_system.Model.Repair;
import com.capstone2.factory_system.Repository.EquipmentRepository;
import com.capstone2.factory_system.Repository.RepairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RepairService{
    private final RepairRepository repairRepository;
    private final EmployeeService employeeService;
    private final EquipmentService equipmentService;
    private final EquipmentRepository equipmentRepository;
    private final FactoryService factoryService;

    public List<Repair> getRepair(){
        return repairRepository.findAll();
    }

    public String createRepair(Repair repair){
        Equipment e = equipmentService.getEquipmentById(repair.getEquipmentId());
        if(e != null && employeeService.checkIfSameFactory(repair.getEmployeeId(),e.getFactoryId())
                && e.getFactoryId().equals(repair.getFactoryId())){
            if(repair.getStartDate() == null){
                repair.setStartDate(LocalDateTime.now());
            }
            repair.setStatus("Open");
            e.setStatus("Under Repair");
            equipmentRepository.save(e);
            repairRepository.save(repair);
            return "success";
        }
        return "equipment doesn't exist or unauthorized";
    }

    public String updateRepair(Integer id, Repair repair){
        Repair r = getRepairById(id);
        if(r == null){
            return "repair request doesn't exist";
        }
        Equipment e = equipmentService.getEquipmentById(repair.getEquipmentId());
        if(e != null && employeeService.checkIfSameFactory(repair.getEmployeeId(),e.getFactoryId())
                && e.getFactoryId().equals(repair.getFactoryId())){
            r.setStatus(repair.getStatus());
            r.setEndDate(repair.getEndDate());
            repairRepository.save(r);
            return "success";
        }
        return "equipment doesn't exist or unauthorized";
    }

    public String deleteRepair(Integer id, Integer employeeId){
        Repair r = getRepairById(id);
        if(r != null && employeeService.checkIfSameFactory(employeeId, r.getFactoryId())){
            repairRepository.delete(r);
            return "success";
        }
        return "repair request not found";
    }

    public String setStatusClose(Integer id, Integer employeeId){
        Repair r = getRepairById(id);
        if(r != null && employeeService.checkIfSameFactory(employeeId, r.getFactoryId())){
            if(r.getStatus().equalsIgnoreCase("Open")){
                Equipment e = equipmentService.getEquipmentById(r.getEquipmentId());
                e.setStatus("Working");
                e.setLastMaintenance(LocalDateTime.now());
                equipmentRepository.save(e);
                r.setStatus("Closed");
                r.setEndDate(LocalDateTime.now());
                repairRepository.save(r);
                return "success";
            }
           return "repair already closed";
        }
        return "repair request not found";
    }

    public Repair getRepairById(Integer id){
        return repairRepository.findRepairByRepairId(id);
    }

    public List<Repair> getRepairByFactoryId(Integer id){
        return repairRepository.findRepairByFactoryId(id);
    }

    public List<Repair> getRepairByEquipmentId(Integer id){
        return repairRepository.findRepairByEquipmentId(id);
    }

    public List<Repair> getRepairByEmployeeId(Integer id){
        return repairRepository.findRepairByEmployeeId(id);
    }

    public List<Repair> getRepairByStatus(Integer factoryId, String status){
        return repairRepository.findRepairByStatus(status, factoryId);
    }

    public List<Repair> getRepairByEndDateRange(LocalDateTime dateTime1, LocalDateTime dateTime2, Integer factoryId){
        return repairRepository.findRepairByEndDateRange(dateTime1,dateTime2,factoryId);
    }

    public List<Repair> getRepairByStartDateRange(LocalDateTime dateTime1, LocalDateTime dateTime2, Integer factoryId){
        return repairRepository.findRepairByStartDateRange(dateTime1,dateTime2,factoryId);
    }
}
