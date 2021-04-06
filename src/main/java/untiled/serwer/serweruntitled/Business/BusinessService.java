package untiled.serwer.serweruntitled.Business;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import untiled.serwer.serweruntitled.Adress.Address;
import untiled.serwer.serweruntitled.Client.ShopClient.ShopClient;

@Service
public class BusinessService {

    private BusinessRepo businessRepo;

    @Autowired
    public BusinessService(BusinessRepo businessRepo) {
        this.businessRepo = businessRepo;
    }


    public void SaveBusinnesToDatabase(Business business){

        if(business != null) {

            Business businessInstant = businessRepo.getOne(business.getId());

            businessInstant.setName(business.getName());
            businessInstant.setNip(business.getNip());
            businessInstant.setEmail(business.getEmail());
            businessRepo.save(businessInstant);
        }
        else
        {
            throw new NullPointerException();
        }
    }
}
