package ShopAppBackend.Services;


import ShopAppBackend.Entities.Business;
import ShopAppBackend.Logs.LogsApplication;
import ShopAppBackend.Repositories.AddressRepo;
import ShopAppBackend.Repositories.BusinessRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class BusinessService {

    private final BusinessRepo businessRepo;
    private final AddressService addressService;
    private final LogsApplication logsApplication;



    @Autowired
    public BusinessService(BusinessRepo businessRepo, AddressService addressService, LogsApplication logsApplication) {
        this.businessRepo = businessRepo;
        this.addressService = addressService;
        this.logsApplication = logsApplication;
    }


    public List<Business> GetAllBusinessFromDatabase(){

        List<Business> businessesList = businessRepo.findAll();
        return (businessesList.isEmpty()) ? Collections.emptyList(): businessesList;
    }

    public Business CreateNewBusiness(){

        Business business = new Business();
        business.setAddress(addressService.CreateNewAddress("Rozliczeniowy"));
        businessRepo.save(business);
        logsApplication.SaveLogToDatabase("Create new Business");
        return business;
    }


    @Transactional
    public Business SaveBusinnesToDatabase(Business business){

        if(business != null) {

            Business businessInstant = businessRepo.getOne(business.getId());
            businessInstant.setAddress(business.getAddress());
            businessInstant.setName(business.getName());
            businessInstant.setNip(business.getNip());
            businessInstant.setRegon(business.getRegon());
            businessInstant.setEmail(business.getEmail());
            businessInstant.setAccount(business.getAccount());
            businessInstant.setPhoneNumber(business.getPhoneNumber());
            businessRepo.save(businessInstant);
            return businessInstant;

        }
        else
        {
            throw new IllegalArgumentException();
        }
    }
}
