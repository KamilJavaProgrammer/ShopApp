package ShopAppBackend.Adress;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private AddressRepo addressRepo;

    @Autowired
    public AddressService(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    public void SaveAddressToDatabase(Address address){
        if(address != null) {

            Address addressInstant = addressRepo.getOne(address.getId());

            addressInstant.setTown(address.getTown());
            addressInstant.setPostCode(address.getPostCode());
            addressInstant.setPlaceOfresident(address.getPlaceOfresident());
            addressRepo.save(addressInstant);
        }
        else
        {
            throw new NullPointerException();
        }
    }

}