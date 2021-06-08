package ShopAppBackend.Services;


import ShopAppBackend.Entities.Address;
import ShopAppBackend.Logs.LogsApplication;
import ShopAppBackend.Repositories.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AddressService {

    private final AddressRepo addressRepo;
    private final LogsApplication logsApplication;

    @Autowired
    public AddressService(AddressRepo addressRepo,LogsApplication logsApplication) {
        this.addressRepo = addressRepo;
        this.logsApplication = logsApplication;
    }

    public Address CreateNewAddress(String addressType){
        Address address = new Address();
        address.setType(addressType);
        addressRepo.save(address);
        this.logsApplication.SaveLogToDatabase("Create new Address" + addressType);
        return address;
    }



    @Transactional
    public Address SaveAddressToDatabase(Address address){
        if(address != null) {

            Address addressInstant = addressRepo.getOne(address.getId());

            addressInstant.setTown(address.getTown());
            addressInstant.setPostCode(address.getPostCode());
            addressInstant.setPlaceOfresident(address.getPlaceOfresident());
            addressRepo.save(addressInstant);
            this.logsApplication.SaveLogToDatabase("Save Adress");
            return addressInstant;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

}
