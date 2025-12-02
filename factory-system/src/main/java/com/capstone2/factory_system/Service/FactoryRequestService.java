package com.capstone2.factory_system.Service;

import com.capstone2.factory_system.Api.ApiException;
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

    public void createFactoryRequest(FactoryRequest factoryRequest){
        FactoryRequest f = getNewestRequest(factoryRequest.getFactoryRecordNumber());
        if(f != null){
            if(f.getStatus().equalsIgnoreCase("approved")){
                throw new ApiException("factory is already approved");
            }if(f.getStatus().equalsIgnoreCase("pending")){
                throw new ApiException("request is still pending you can update you request");
            }
        }
            factoryRequest.setEndDate(null);
            factoryRequest.setRequestDate(LocalDateTime.now());
            factoryRequest.setCheckedBy(null);
            factoryRequest.setStatus("pending");
            factoryRequestRepository.save(factoryRequest);
    }

    public void updateFactoryRequest(String factoryRecord, FactoryRequest factoryRequest){
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
                        return;
                    }
                throw new ApiException("wrong factory record number");
                }
            throw new ApiException("factory is checked by the admin make another if rejected");
            }
        throw new ApiException("factory record doesn't exist");
    }

    public void deleteFactoryRequest(Integer id){
        FactoryRequest f = getFactoryRequestId(id);
        if(f != null){
            if(f.getStatus().equalsIgnoreCase("pending")){
               factoryRequestRepository.delete(f);
               return;
            }
            throw new ApiException("factory is checked by the admin make another if rejected");
        }
        throw new ApiException("factory record doesn't exist");
    }
    // extra
    public void approve(String factoryRecord, Integer adminId){
        FactoryRequest f = getNewestRequest(factoryRecord);
        if(adminService.getAdminById(adminId) == null){
            throw new ApiException("unauthorized");
        }
        if(f != null){
            if(f.getStatus().equalsIgnoreCase("pending")){
                f.setStatus("approved");
                f.setCheckedBy(adminId);
                f.setEndDate(LocalDateTime.now());
                deletePendingRequests(factoryRecord, f);
                factoryRequestRepository.save(f);
                return;
            }
            throw new ApiException("already checked");
        }
        throw new ApiException("no records found");
    }

    public void reject(String factoryRecord, Integer adminId){
        FactoryRequest f = getNewestRequest(factoryRecord);
        if(adminService.getAdminById(adminId) == null){
            throw new ApiException("unauthorized");
        }
        if(f != null){
            if(f.getStatus().equalsIgnoreCase("pending")){
                f.setStatus("rejected");
                f.setEndDate(LocalDateTime.now());
                f.setCheckedBy(adminId);
                deletePendingRequests(factoryRecord, f);
                factoryRequestRepository.save(f);
                return;
            }
            throw new ApiException("already checked");
        }
        throw new ApiException("no records found");
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
