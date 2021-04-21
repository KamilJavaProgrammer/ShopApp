package ShopAppBackend.Business;


import ShopAppBackend.Adress.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {

    private BusinessRepo businessRepo;
    private AddressService addressService;

    @Autowired
    public BusinessService(BusinessRepo businessRepo, AddressService addressService) {
        this.businessRepo = businessRepo;
        this.addressService = addressService;
    }



    public Business CreateNewBusiness(){

        Business business = new Business();
        business.setAddress(addressService.CreateNewAddress("Rozliczeniowy"));
        businessRepo.save(business);
        return business;
    }





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
