package ShopAppBackend.Client.ShopClient;


import ShopAppBackend.Client.Client;
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
    public ShopClientService(ShopClientRepository shopClientRepository, UserRepo userRepo, AddressRepo addressRepo, AddressService addressService, BusinessService businessService) {
        this.shopClientRepository = shopClientRepository;
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
        this.addressService = addressService;
        this.businessService = businessService;
    }


    public ResponseEntity<?> GetAllClient(){
        List<ShopClient> clients = shopClientRepository.findAll();
        System.out.println(clients);
        clients.sort(ShopClient::compareTo);
        System.out.println(clients);
        return ResponseEntity.status(HttpStatus.OK).body(clients);
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



//    @Transactional
//    public HttpStatus UpdateClientData(Client client) {
//
//        Client client1 = clientRepository.getOne(client.getId());
//
//        client1.setName(client.getName());
//        client1.setSurname(client.getSurname());
////        client1.setEmail(client.getEmail());
//        client1.setPhoneNumber(client.getPhoneNumber());
//
//          addressService.SaveAddressToDatabase(client.getBusiness().getAddress());
//          addressService.SaveAddressToDatabase(client.getAddress());
//          businessService.SaveBusinnesToDatabase(client.getBusiness());
//
//        clientRepository.save(client);
//
//        return HttpStatus.NO_CONTENT;
//    }














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
}
