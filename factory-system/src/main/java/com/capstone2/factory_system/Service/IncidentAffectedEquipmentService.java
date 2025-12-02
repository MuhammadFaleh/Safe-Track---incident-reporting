package com.capstone2.factory_system.Service;

import com.capstone2.factory_system.Api.ApiException;
import com.capstone2.factory_system.Model.*;
import com.capstone2.factory_system.Repository.IncidentAffectedEquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentAffectedEquipmentService{
    private final IncidentAffectedEquipmentRepository incidentAffectedEquipmentRepository;
    private final EquipmentService equipmentService;
    private final IncidentReportService incidentReportService;
    private final FactoryService factoryService;


    public List<IncidentAffectedEquipment> getIncidentAffectedEqu(){
        return incidentAffectedEquipmentRepository.findAll();
    }

    public void createIncidentAffectedEqu(IncidentAffectedEquipment equ){
        Equipment e = equipmentService.getEquipmentById(equ.getEquipmentId());
        IncidentReport ir = incidentReportService.getIncidentById(equ.getIncidentId());

        if(ir == null || ir.getStatus().equalsIgnoreCase("Closed")){
            throw new ApiException("incident doesn't exist");
        }
        if(e == null){
            throw new ApiException("equipment doesn't exist");
        }

        if(!e.getFactoryId().equals(ir.getFactoryId())){
            throw new ApiException("factory id doesn't match");

        }

        incidentAffectedEquipmentRepository.save(equ);
        throw new ApiException("success");
    }

    public void updateIncidentAffectedEqu(Integer id, IncidentAffectedEquipment equ){
        IncidentAffectedEquipment iae = getIncidentById(id);
        Equipment e = equipmentService.getEquipmentById(equ.getEquipmentId());
        IncidentReport ir = incidentReportService.getIncidentById(equ.getIncidentId());

        if(iae == null){
            throw new ApiException("no incident matches the id");
        }
        if(ir == null || ir.getStatus().equalsIgnoreCase("Closed")){
            throw new ApiException("incident doesn't exist");
        }
        if(e == null){
            throw new ApiException("equipment doesn't exist");
        }

        if(!e.getFactoryId().equals(ir.getFactoryId())){
            throw new ApiException("factory id doesn't match");
        }

        iae.setNotes(equ.getNotes());
        iae.setSeverity(equ.getSeverity());
        incidentAffectedEquipmentRepository.save(iae);
    }

    public void deleteIncidentAffectedEqu(Integer id, String username) {
        IncidentAffectedEquipment iae = getIncidentById(id);

        Factory f = factoryService.getFactoryByUsername(username);

        if (iae == null) {
            throw new ApiException("no incident matches the id");
        }
        IncidentReport ir = incidentReportService.getIncidentById(iae.getIncidentId());
        if (ir != null) {
            if (f != null && f.getFactoryId().equals(ir.getFactoryId())) {
                incidentAffectedEquipmentRepository.delete(iae);
                throw new ApiException("success");
            }
            throw new ApiException("unauthorized");
        }
        throw new ApiException("no incident report");
    }

    public IncidentAffectedEquipment getIncidentById(Integer id){
        return incidentAffectedEquipmentRepository.findIncidentAffectedEquipmentById(id);
    }

    public List<IncidentAffectedEquipment> getIncidentBySeverityAndIncidentId(Integer id, String severity){
        return incidentAffectedEquipmentRepository.findIncidentAffectedEquipmentBySeverity(id, severity);
    }

    public List<IncidentAffectedEquipment> getByIncidentId(Integer id){
        return incidentAffectedEquipmentRepository.findIncidentAffectedEquipmentByIncidentId(id);

    }

    public IncidentAffectedEquipment getByEquipmentId(Integer id){
        return incidentAffectedEquipmentRepository.findIncidentAffectedEquipmentByEquipmentId(id);

    }
}
