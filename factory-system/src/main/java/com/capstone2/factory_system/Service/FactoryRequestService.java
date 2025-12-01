package com.capstone2.factory_system.Service;

import com.capstone2.factory_system.Model.FactoryRequest;
import com.capstone2.factory_system.Repository.FactoryRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FactoryRequestService {
    final private FactoryRequestRepository factoryRequestRepository;
    final private AdminService adminService;

    public List<FactoryRequest> getFactoryRequests(){
        return factoryRequestRepository.findAll();
    }

    public String createFactoryRequest(FactoryRequest factoryRequest){
        FactoryRequest f = getNewestRequest(factoryRequest.getFactoryRecordNumber());
        if(f != null){
            if(f.getStatus().equalsIgnoreCase("approved")){
                return "factory is already approved";
            }if(f.getStatus().equalsIgnoreCase("rejected")){
                factoryRequest.setEndDate(null);
                factoryRequest.setRequestDate(LocalDateTime.now());
                factoryRequest.setCheckedBy(null);
                factoryRequest.setStatus("pending");
                factoryRequestRepository.save(factoryRequest);
                return "success";
            }
            return "request is still pending you can update you request";
        }
        factoryRequest.setRequestDate(LocalDateTime.now());
        factoryRequest.setEndDate(null);
        factoryRequest.setCheckedBy(null);
        factoryRequest.setStatus("pending");
        factoryRequestRepository.save(factoryRequest);
        return "success";
    }

    public String updateFactoryRequest(String factoryRecord, FactoryRequest factoryRequest){
        FactoryRequest f = getNewestRequest(factoryRecord);
        if(f != null){
            if(f.getStatus().equalsIgnoreCase("pending")){
                if(f.getFactoryRecordNumber().equalsIgnoreCase(factoryRequest.getFactoryRecordNumber())
                        || getFactoryByRecords(factoryRequest.getFactoryRecordNumber()) == null){
                        f.setCity(factoryRequest.getCity());
                        f.setFactoryName(factoryRequest.getFactoryName());
                        f.setFullName(factoryRequest.getFullName());
                        f.setFactoryRecordNumber(factoryRequest.getFactoryRecordNumber());
                        factoryRequestRepository.save(f);
                        return "success";
                    }
                    return "wrong factory record number";
                }
            return "factory is checked by the admin make another if rejected";
            }
        return "factory record doesn't exist";
    }

    public String deleteFactoryRequest(Integer id){
        FactoryRequest f = getFactoryRequestId(id);
        if(f != null){
            if(f.getStatus().equalsIgnoreCase("pending")){
               factoryRequestRepository.delete(f);
               return "success";
            }
            return "factory is checked by the admin make another if rejected";
        }
        return "factory record doesn't exist";
    }
    // extra
    public String approve(String factoryRecord, Integer adminId){
        FactoryRequest f = getNewestRequest(factoryRecord);
        if(adminService.getAdminById(adminId) == null){
            return "unauthorized";
        }
        if(f != null){
            if(f.getStatus().equalsIgnoreCase("pending")){
                f.setStatus("approved");
                f.setCheckedBy(adminId);
                f.setEndDate(LocalDateTime.now());
                deletePendingRequests(factoryRecord, f);
                factoryRequestRepository.save(f);
                return "success";
            }
            return "already checked";
        }
        return "no records found";
    }

    public String reject(String factoryRecord, Integer adminId){
        FactoryRequest f = getNewestRequest(factoryRecord);
        if(adminService.getAdminById(adminId) == null){
            return "unauthorized";
        }
        if(f != null){
            if(f.getStatus().equalsIgnoreCase("pending")){
                f.setStatus("rejected");
                f.setEndDate(LocalDateTime.now());
                f.setCheckedBy(adminId);
                deletePendingRequests(factoryRecord, f);
                factoryRequestRepository.save(f);
                return "success";
            }
            return "already checked";
        }
        return "no records found";
    }

    public List<FactoryRequest> getFactoryByRecords(String factoryRecord){
        return factoryRequestRepository.findFactoryByFactoryRecordNumber(factoryRecord);
    }

    public FactoryRequest getFactoryRequestId(Integer id){
        return factoryRequestRepository.findFactoryRequestByFactoryRequestId(id);
    }

    public FactoryRequest getNewestRequest(String factoryRecord){
        return factoryRequestRepository.findFactoryRequestNewest(factoryRecord);
    }

    public List<FactoryRequest> getFactoriesPending(){
        return factoryRequestRepository.findFactoryRequestByStatus("pending");
    }

    public void deletePendingRequests(String factoryRecord, FactoryRequest leatestFactoryRequest){
        List<FactoryRequest> factoryRequests = getFactoryByRecords(factoryRecord);
        if(leatestFactoryRequest.getStatus().equalsIgnoreCase("approved") ||
                leatestFactoryRequest.getStatus().equalsIgnoreCase("rejected")){
            for (FactoryRequest factoryRequest : factoryRequests){
                if(factoryRequest.getFactoryRequestId().equals(leatestFactoryRequest.getFactoryRequestId())){
                    continue;
                }
                factoryRequestRepository.delete(factoryRequest);
            }
        }
    }
}
