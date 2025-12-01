package com.capstone2.factory_system.Service;

import com.capstone2.factory_system.Model.Employee;
import com.capstone2.factory_system.Model.Factory;
import com.capstone2.factory_system.Model.IncidentReport;
import com.capstone2.factory_system.Repository.IncidentReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentReportService {
    private final IncidentReportRepository incidentReportRepository;
    private final FactoryService factoryService;
    private final EmployeeService employeeService;

    public List<IncidentReport> getIncidentReports(){
        return incidentReportRepository.findAll();
    }

    public String createIncidentReport(IncidentReport incidentReport){
        Employee e = employeeService.getEmployeeById(incidentReport.getReportedBy());
        if(e != null && e.getFactoryId().equals(incidentReport.getFactoryId())){
            if(incidentReport.getDateReported() == null){
                incidentReport.setDateReported(LocalDateTime.now());
            }
            incidentReport.setStatus("Open");
            incidentReportRepository.save(incidentReport);
            return "success";
        }
        return "employee doesn't exist or factory doesn't match";
    }

    public String updateIncidentReport(Integer id, IncidentReport incidentReport){
        IncidentReport ir = getIncidentById(id);
        Employee e = employeeService.getEmployeeById(incidentReport.getReportedBy());
        if(ir == null){
            return "Incident Report doesn't exist";
        }
        if(e != null && e.getFactoryId().equals(incidentReport.getFactoryId())){
            ir.setDescription(incidentReport.getDescription());
            ir.setFactoryId(incidentReport.getFactoryId());
            ir.setReportedBy(incidentReport.getReportedBy());
            ir.setSeverity(incidentReport.getSeverity());
            ir.setStatus("Open");
            incidentReportRepository.save(ir);
            return "success";
        }
        return "employee doesn't exist or factory doesn't match";
    }

    public String deleteIncident(String username, Integer id){
        IncidentReport ir = getIncidentById(id);
        if(ir == null){
            return "incident not found";
        }
        Factory f = factoryService.getFactoryByUsername(username);
        if(f != null && f.getFactoryId().equals(ir.getFactoryId())){
            incidentReportRepository.delete(ir);
            return "success";
        }
        return "factory doesn't match";
    }


    public IncidentReport getIncidentById(Integer id){
        return incidentReportRepository.findIncidentReportByIncidentId(id);
    }

    public List<IncidentReport> getIncidentByDateRange(Integer id, LocalDateTime dateTime1, LocalDateTime dateTime2){
        return incidentReportRepository.findIncidentReportByRequestDate(id, dateTime1, dateTime2);
    }

    public List<IncidentReport> getIncidentByFactoryId(Integer id){
        return incidentReportRepository.findIncidentReportByFactoryId(id);
    }

    public List<IncidentReport> getIncidentByReportedBy(Integer id){
        return incidentReportRepository.findIncidentReportByReportedBy(id);
    }

    public List<IncidentReport> getIncidentBySeverity(Integer id, String severity){
        return incidentReportRepository.findIncidentReportBySeverity(id, severity);
    }

    public List<IncidentReport> getIncidentByType(Integer id, String type){
        return incidentReportRepository.findIncidentReportByType(id, type);
    }

}
