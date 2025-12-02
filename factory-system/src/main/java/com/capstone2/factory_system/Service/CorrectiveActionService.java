package com.capstone2.factory_system.Service;

import com.capstone2.factory_system.Api.ApiException;
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

    public void createCorrectiveAction(CorrectiveAction correctiveAction){
        IncidentReport ir = incidentReportService.getIncidentById(correctiveAction.getIncidentId());
        if(correctiveAction.getStartDate() == null){
            correctiveAction.setStartDate(LocalDateTime.now());
        }
        if(correctiveAction.getEndDate() == null){
            correctiveAction.setEndDate(correctiveAction.getStartDate().plusDays(30));
        }
        if(correctiveAction.getEndDate().isBefore(correctiveAction.getStartDate())){
            throw new ApiException("failed to create the report end date is before the start date");

        }
        if( ir == null  || ir.getStatus().equalsIgnoreCase("Closed")){
            throw new ApiException("incident doesn't exist");
        }
        if(correctiveAction.getStartDate() == null){
            correctiveAction.setStartDate(LocalDateTime.now());
        }
        correctiveAction.setStatus("Open");
        correctiveActionRepository.save(correctiveAction);
    }

    public void updateCorrectiveAction(Integer id, CorrectiveAction correctiveAction){
        CorrectiveAction ca = getCorrectiveActionById(id);
        if(correctiveAction.getStartDate() == null){
            correctiveAction.setStartDate(LocalDateTime.now());
        }
        if(correctiveAction.getEndDate() == null){
            correctiveAction.setEndDate(correctiveAction.getStartDate().plusDays(30));
        }
        if(ca != null){
            if(correctiveAction.getEndDate().isBefore(correctiveAction.getStartDate())){
                throw new ApiException("failed to create the report end date is before the start date");

            }
            ca.setDescription(correctiveAction.getDescription());
            ca.setStartDate(correctiveAction.getStartDate());
            ca.setEndDate(correctiveAction.getEndDate());
            correctiveActionRepository.save(ca);
        }
        throw new ApiException("corrective action doesn't exist");
    }

    public void deleteCorrectiveAction(Integer id){
        CorrectiveAction ca = getCorrectiveActionById(id);
        if(ca != null){
            correctiveActionRepository.delete(ca);
        }
        throw new ApiException("corrective action doesn't exist");
    }

    public CorrectiveAction getCorrectiveActionById(Integer id){
        return correctiveActionRepository.findCorrectiveActionByActionId(id);
    }

    public void changeStatus(Integer id, Integer empId){
        CorrectiveAction ca = getCorrectiveActionById(id);
        IncidentReport incidentReport = incidentReportService.getIncidentById(ca.getIncidentId());
        if(incidentReport != null && ca != null){
            if(ca.getStatus().equalsIgnoreCase("Open")){
                if(employeeService.checkIfSameFactory(empId, incidentReport.getFactoryId() )){
                    ca.setStatus("Closed");
                    ca.setEndDate(LocalDateTime.now());
                    correctiveActionRepository.save(ca);
                    return;

                }
                throw new ApiException("unauthorized");
            }
            throw new ApiException("request already closed");
        }
        throw new ApiException("corrective action or incident doesn't exist");
    }
    public List<CorrectiveAction> getCorrectiveActionByStatusAndIncidentId(Integer id, String status){
        return correctiveActionRepository.findCorrectiveActionByStatus(id, status);
    }

    public List<CorrectiveAction> getCorrectiveActionByIncidentId(Integer id){
        return correctiveActionRepository.findCorrectiveActionByIncidentId(id);
    }
}
