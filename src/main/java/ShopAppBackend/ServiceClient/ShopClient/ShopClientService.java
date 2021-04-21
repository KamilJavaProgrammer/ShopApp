package ShopAppBackend.ServiceClient.ShopClient;


import ShopAppBackend.Adress.Address;
import ShopAppBackend.Business.Business;
import ShopAppBackend.ServiceClient.Client;
import ShopAppBackend.User.User;
import ShopAppBackend.User.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ShopAppBackend.Adress.AddressRepo;
import ShopAppBackend.Adress.AddressService;
import ShopAppBackend.Business.BusinessService;

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

    public ShopClient CreateNewShopClient(User user){

        ShopClient shopClient = new ShopClient();
        shopClient.setEmail(user.getEmail());
        shopClient.setState("Zarejestrowany");
        shopClient.setAddress(addressService.CreateNewAddress("Wysyłkowy"));
        shopClient.setBusiness(businessService.CreateNewBusiness());
        shopClientRepository.save(shopClient);
        return shopClient;
    }


//
//    public ResponseEntity<List<ShopClient>> GetAllClient(){
//        List<ShopClient> clients = shopClientRepository.findAll();
//        System.out.println(clients);
//        clients.sort(ShopClient::compareTo);
//        System.out.println(clients);
//        return ResponseEntity.status(HttpStatus.OK).body(clients);
//    }

    public List<ShopClient> GetAllClient(){
        List<ShopClient> clients = shopClientRepository.findAll();
        System.out.println(clients);
        clients.sort(ShopClient::compareTo);
        System.out.println(clients);
        return clients;
    }



    @Transactional
    public ResponseEntity<HttpStatus> DeleteShopClientById(Long id){
        if(id == null){
            throw new IllegalArgumentException();
        }
        else
        {
            shopClientRepository.deleteById(id);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }

    }

    public ResponseEntity<HttpStatus> AddClientToDatabase(ShopClient shopClient){

        if(shopClient != null){
            addressRepo.save(shopClient.getAddress());
            shopClientRepository.save(shopClient);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }



    @Transactional
    public ResponseEntity<HttpStatus> DeleteClients(List<ShopClient> shopClients){
        shopClients.forEach(shopClient -> {
            shopClientRepository.deleteById(shopClient.getId());
        });

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }



    @Transactional
    public  ResponseEntity<ShopClient> UpdateClientData(ShopClient shopClient,String username) {

        ShopClient shopClientInstant = shopClientRepository.getOne(userRepo.getClientIdByUserUsername(username));

        shopClientInstant.setName(shopClient.getName());
        shopClientInstant.setSurname(shopClient.getSurname());
        shopClientInstant.setEmail(shopClient.getEmail());
        shopClientInstant.setPhoneNumber(shopClient.getPhoneNumber());
        shopClientInstant.setAddress(addressService.SaveAddressToDatabase(shopClient.getAddress()));
        shopClientInstant.setBusiness(businessService.SaveBusinnesToDatabase(shopClient.getBusiness()));
        shopClientRepository.save(shopClientInstant);

        return  ResponseEntity.status(HttpStatus.OK).body(shopClient);
    }














//    @Transactional
//    public ResponseEntity<String> AddClientAddress(Client client, String login) {
//
//        try {
//            Client clientInstant = clientRepository.getOne(userRepo.getClientByUsername1(login));
//
//            if (client.getAddress() != null) {
//                addressRepo.save(client.getAddress());
//                clientInstant.setAddress(client.getAddress());
//            } else {
//                addressRepo.save(client.getBusiness().getAddress());
//                clientInstant.getBusiness().setAddress(client.getBusiness().getAddress());
//            }
//            clientRepository.save(clientInstant);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body("OK");
//
//        } catch (Exception e) {
//
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Error" + e.toString());
//        }
//    }






    public Client GetClientByLogin(String login) {

        return shopClientRepository.getOne(userRepo.getClientByUsername1(login));

    }

    public Optional<ShopClient> GetOneShopClientFromDatabase(Long id) throws ShopClientNotFound {

        if(id == null){
            throw new IllegalArgumentException();
        }
        else
        {
            Optional<ShopClient> shopClient = shopClientRepository.findById(id);
            if(shopClient.isPresent()){
                return shopClient;
            }
            else
            {
                throw new ShopClientNotFound("error");
            }
        }
    }


    public ResponseEntity<ShopClient> GetOneClientByTokenJwt() {
        return null;
    }
}
