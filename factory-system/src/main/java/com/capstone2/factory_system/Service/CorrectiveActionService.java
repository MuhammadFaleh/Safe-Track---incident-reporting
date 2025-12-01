package com.capstone2.factory_system.Service;

import com.capstone2.factory_system.Model.CorrectiveAction;
import com.capstone2.factory_system.Model.IncidentReport;
import com.capstone2.factory_system.Repository.CorrectiveActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CorrectiveActionService {
    private final CorrectiveActionRepository correctiveActionRepository;
    private final IncidentReportService incidentReportService; //
    private final EmployeeService employeeService;

    public List<CorrectiveAction> getCorrectiveActions(){
        return correctiveActionRepository.findAll();
    }

    public String createCorrectiveAction(CorrectiveAction correctiveAction){
        if(correctiveAction.getStartDate() == null){
            correctiveAction.setStartDate(LocalDateTime.now());
        }
        if(correctiveAction.getEndDate().isBefore(correctiveAction.getStartDate())){
            return "failed to create the report end date is before the start date";

        }
        if(incidentReportService.getIncidentById(correctiveAction.getIncidentId()) == null){
            return "incident doesn't exist";
        }
        correctiveAction.setStatus("Open");
        correctiveActionRepository.save(correctiveAction);
        return "success";
    }

    public String updateCorrectiveAction(Integer id, CorrectiveAction correctiveAction){
        CorrectiveAction ca = getCorrectiveActionById(id);
        if(ca != null){
            if(correctiveAction.getEndDate().isBefore(correctiveAction.getStartDate())){
                return "failed to create the report end date is before the start date";

            }
            ca.setDescription(correctiveAction.getDescription());
            ca.setStartDate(correctiveAction.getStartDate());
            ca.setEndDate(correctiveAction.getEndDate());
            correctiveActionRepository.save(ca);
            return "success";
        }
        return "corrective action doesn't exist";
    }

    public String deleteCorrectiveAction(Integer id){
        CorrectiveAction ca = getCorrectiveActionById(id);
        if(ca != null){
            correctiveActionRepository.delete(ca);
            return "success";
        }
        return "corrective action doesn't exist";
    }

    public CorrectiveAction getCorrectiveActionById(Integer id){
        return correctiveActionRepository.findCorrectiveActionByActionId(id);
    }

    public String changeStatus(Integer id, Integer empId){
        CorrectiveAction ca = getCorrectiveActionById(id);
        IncidentReport incidentReport = incidentReportService.getIncidentById(ca.getIncidentId());
        if(incidentReport != null){
            if(ca.getStatus().equalsIgnoreCase("Open")){
                if(employeeService.checkIfSameFactory(empId, incidentReport.getFactoryId() )){
                    ca.setStatus("Closed");
                    correctiveActionRepository.save(ca);
                    return "success";
                }
                return "unauthorized";
            }
            return "request already closed";
        }
        return "corrective action or incident doesn't exist";
    }
    public List<CorrectiveAction> getCorrectiveActionByStatusAndIncidentId(Integer id, String status){
        return correctiveActionRepository.findCorrectiveActionByStatus(id, status);
    }

    public List<CorrectiveAction> getCorrectiveActionByIncidentId(Integer id){
        return correctiveActionRepository.findCorrectiveActionByIncidentId(id);
    }
}
