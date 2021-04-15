package ShopAppBackend.ServiceClient.ShopClient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/client/shop")
public class ShopClientController {

    private ShopClientService shopClientService;

    @Autowired
    public ShopClientController(ShopClientService shopClientService)  {
        this.shopClientService = shopClientService;
    }


    @GetMapping()
    public ResponseEntity<?> GetAllClientsByShop() {
        return ResponseEntity.ok(shopClientService.GetAllClient());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> GetOneShopClientById(@PathVariable Long id) throws ShopClientNotFound {
        return ResponseEntity.ok(shopClientService.GetOneShopClientFromDatabase(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteClientById(@PathVariable Long id){
        return shopClientService.DeleteShopClientById(id);
    }


    //    @PatchMapping("/client")
//    public ResponseEntity<?> UpdateClientData(@RequestBody Client client) {
//        return ResponseEntity.ok(clientService.UpdateClientData(client));
//    }


//    @GetMapping("/client")
//    @ResponseBody
//    public ResponseEntity<Client> GetAddressByClient(@AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
//        return ResponseEntity.ok(clientService.GetClientByLogin(user.getName()));
//    }
//







   @PostMapping()
    public ResponseEntity<?> AddShopClientToDatabase(@Valid  @RequestBody ShopClient shopClient){
        return ResponseEntity.ok(shopClientService.AddClientToDatabase(shopClient));
   }

   @DeleteMapping()
    public ResponseEntity<?> DeleteClients(@RequestBody List<ShopClient> shopClientList){
        return ResponseEntity.ok(shopClientService.DeleteClients(shopClientList));
   }
}
