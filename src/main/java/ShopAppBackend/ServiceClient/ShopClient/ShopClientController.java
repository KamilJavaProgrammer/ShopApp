package ShopAppBackend.ServiceClient.ShopClient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/shop/client")
public class ShopClientController {

    private final ShopClientService shopClientService;

    @Autowired
    public ShopClientController(ShopClientService shopClientService)  {
        this.shopClientService = shopClientService;
    }




    @GetMapping("")
    public ResponseEntity<?> GetOneClientByTokenJwt(@AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
        System.out.println(user.getName());
        System.out.println(user.getPrincipal());
        System.out.println(user.getAuthorities());
        System.out.println(user.getCredentials());
        return ResponseEntity.ok(shopClientService.GetOneClientByTokenJwt());
    }




    @GetMapping("/all")
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

    @PostMapping()
    public ResponseEntity<?> AddShopClientToDatabase(@RequestBody ShopClient shopClient){
        return ResponseEntity.ok(shopClientService.AddClientToDatabase(shopClient));
    }


    @DeleteMapping()
    public ResponseEntity<?> DeleteClients(@RequestBody List<ShopClient> shopClientList){
        return ResponseEntity.ok(shopClientService.DeleteClients(shopClientList));
    }


    @PatchMapping()
    public ResponseEntity<?> UpdateClientData(@RequestBody ShopClient shopClient, @AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
        return ResponseEntity.ok(shopClientService.UpdateClientData(shopClient,user.getName()));
    }


//    @GetMapping("/client")
//    @ResponseBody
//    public ResponseEntity<Client> GetAddressByClient(@AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
//        return ResponseEntity.ok(clientService.GetClientByLogin(user.getName()));
//    }
//









}
