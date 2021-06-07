package ShopAppBackend.Services;


import ShopAppBackend.Entities.Section;
import ShopAppBackend.Entities.ShopClient;
import ShopAppBackend.Exceptions.SectionNotFoundException;
import ShopAppBackend.Exceptions.ShopClientNotFound;
import ShopAppBackend.Repositories.ShopClientRepository;
import ShopAppBackend.AbstractSuperclass.Client;
import ShopAppBackend.Entities.User;
import ShopAppBackend.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ShopAppBackend.Repositories.AddressRepo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ShopClientService {



    private final ShopClientRepository shopClientRepository;
    private final UserRepo userRepo;
    private final AddressRepo addressRepo;
    private final AddressService addressService;
    private final BusinessService businessService;


    @Autowired
    public ShopClientService(ShopClientRepository shopClientRepository, UserRepo userRepo, AddressRepo addressRepo, AddressService addressService, BusinessService businessService, AddressService addressService1) {
        this.shopClientRepository = shopClientRepository;
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
        this.addressService = addressService;
        this.businessService = businessService;
    }

    @Transactional
    public ShopClient CreateNewShopClient(User user){

        ShopClient shopClient = new ShopClient();
        shopClient.setEmail(user.getEmail());
        shopClient.setState("Zarejestrowany");
        shopClient.setAddress(addressService.CreateNewAddress("Wysyłkowy"));
        shopClient.setBusiness(businessService.CreateNewBusiness());
        shopClientRepository.save(shopClient);
        return shopClient;
    }


    public List<ShopClient> GetAllClient(){
        List<ShopClient> clients = shopClientRepository.findAll();
        clients.sort(ShopClient::compareTo);
        return clients;
    }



    @Transactional
    public HttpStatus DeleteShopClientById(Long id){
        Optional<ShopClient> section = shopClientRepository.findById(id);
        shopClientRepository.delete(section.orElseThrow(ShopClientNotFound::new));
        return HttpStatus.NO_CONTENT;

    }

    @Transactional
    public HttpStatus AddClientToDatabase(ShopClient shopClient){

        if(shopClient != null){
            addressRepo.save(shopClient.getAddress());
            shopClientRepository.save(shopClient);
            return HttpStatus.NO_CONTENT;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }



    @Transactional
    public HttpStatus DeleteClients(List<ShopClient> shopClients){
        shopClients.forEach(shopClient -> {
            shopClientRepository.deleteById(shopClient.getId());
        });

        return HttpStatus.NO_CONTENT;
    }



    @Transactional
    public  ShopClient UpdateClientData(ShopClient shopClient,String username) {

        ShopClient shopClientInstant = shopClientRepository.getOne(userRepo.getClientIdByUserUsername(username));

        shopClientInstant.setName(shopClient.getName());
        shopClientInstant.setSurname(shopClient.getSurname());
        shopClientInstant.setEmail(shopClient.getEmail());
        shopClientInstant.setPhoneNumber(shopClient.getPhoneNumber());
        shopClientInstant.setAddress(addressService.SaveAddressToDatabase(shopClient.getAddress()));
        shopClientInstant.setBusiness(businessService.SaveBusinnesToDatabase(shopClient.getBusiness()));
        shopClientRepository.save(shopClientInstant);
       return shopClient;
    }

    public ShopClient GetOneShopClientFromDatabase(Long id) throws ShopClientNotFound {

        if(id == null){
            throw new IllegalArgumentException();
        }
        else
        {
            Optional<ShopClient> shopClient = shopClientRepository.findById(id);
            if(shopClient.isPresent()){
                return shopClient.get();
            }
            else
            {
                throw new ShopClientNotFound();
            }
        }
    }


    public ShopClient GetOneClientByTokenJwt(String username) {
        ShopClient shopClientInstant = shopClientRepository.getOne(userRepo.getClientIdByUserUsername(username));

        return shopClientInstant;

    }
}
