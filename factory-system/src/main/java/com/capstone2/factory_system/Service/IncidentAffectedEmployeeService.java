package com.capstone2.factory_system.Service;

import com.capstone2.factory_system.Model.Employee;
import com.capstone2.factory_system.Model.Factory;
import com.capstone2.factory_system.Model.IncidentAffectedEmployee;
import com.capstone2.factory_system.Model.IncidentReport;
import com.capstone2.factory_system.Repository.IncidentAffectedEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentAffectedEmployeeService {
    private final IncidentAffectedEmployeeRepository incidentAffectedEmployeeRepository;
    private final EmployeeService employeeService;
    private final IncidentReportService incidentReportService;
    private final FactoryService factoryService;


    public List<IncidentAffectedEmployee> getIncidentAffectedEmp(){
        return incidentAffectedEmployeeRepository.findAll();
    }

    public String createIncidentAffectedEmp(IncidentAffectedEmployee emp){
        Employee e = employeeService.getEmployeeById(emp.getEmployeeId());
        IncidentReport ir = incidentReportService.getIncidentById(emp.getIncidentId());

        if(ir == null || ir.getStatus().equalsIgnoreCase("Closed")){
            return "incident doesn't exist";
        }
        if(e == null){
            return "employee doesn't exist";
        }

        if(!e.getFactoryId().equals(ir.getFactoryId())){
            return "factory id doesn't match";

        }

        incidentAffectedEmployeeRepository.save(emp);
        return "success";
    }

    public String updateIncidentAffectedEmp(Integer id, IncidentAffectedEmployee emp){
        IncidentAffectedEmployee iae = getIncidentById(id);
        Employee e = employeeService.getEmployeeById(emp.getEmployeeId());
        IncidentReport ir = incidentReportService.getIncidentById(emp.getIncidentId());

        if(iae == null){
            return "no incident matches the id";
        }
        if(ir == null || ir.getStatus().equalsIgnoreCase("Closed")){
            return "incident doesn't exist";
        }
        if(e == null){
            return "employee doesn't exist";
        }

        if(!e.getFactoryId().equals(ir.getFactoryId())){
            return "factory id doesn't match";
        }

        iae.setNotes(emp.getNotes());
        iae.setSeverity(emp.getSeverity());
        incidentAffectedEmployeeRepository.save(iae);
        return "success";
    }

    public String deleteIncidentAffectedEmp(Integer id, String username) {
        IncidentAffectedEmployee iae = getIncidentById(id);

        Factory f = factoryService.getFactoryByUsername(username);

        if (iae == null) {
            return "no incident matches the id";
        }
        IncidentReport ir = incidentReportService.getIncidentById(iae.getIncidentId());
        if (ir != null) {
            if (f != null && f.getFactoryId().equals(ir.getFactoryId())) {
                incidentAffectedEmployeeRepository.delete(iae);
                return "success";
            }
            return "unauthorized";
        }
        return "no incident report";
    }

    public IncidentAffectedEmployee getIncidentById(Integer id){
        return incidentAffectedEmployeeRepository.findIncidentAffectedEmployeeById(id);
    }

    public List<IncidentAffectedEmployee> getIncidentBySeverityAndIncidentId(Integer id, String severity){
        return incidentAffectedEmployeeRepository.findIncidentAffectedEmployeeBySeverity(id, severity);
    }

    public List<IncidentAffectedEmployee> getByIncidentId(Integer id){
        return incidentAffectedEmployeeRepository.findIncidentAffectedEmployeeByIncidentId(id);

    }

    public List<IncidentAffectedEmployee> getByEmployeeId(Integer id){
        return incidentAffectedEmployeeRepository.findIncidentAffectedEmployeeByEmployeeId(id);

    }
}
