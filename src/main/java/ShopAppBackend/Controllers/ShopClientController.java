package ShopAppBackend.Controllers;


import ShopAppBackend.Entities.ShopClient;
import ShopAppBackend.Exceptions.ShopClientNotFound;
import ShopAppBackend.Services.ShopClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
@RequestMapping(value = "/shopClients")
@Validated
public class ShopClientController {

    private final ShopClientService shopClientService;

    @Autowired
    public ShopClientController(ShopClientService shopClientService)  {
        this.shopClientService = shopClientService;
    }


    @GetMapping("")
    public ResponseEntity<ShopClient> GetOneClientByTokenJwt(@AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {

        return ResponseEntity.ok(shopClientService.GetOneClientByTokenJwt(user.getName()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ShopClient>> GetAllClientsByShop() {
        return ResponseEntity.ok(shopClientService.GetAllClient());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopClient> GetOneShopClientById(@PathVariable  @NotNull Long id) throws ShopClientNotFound {
        return ResponseEntity.ok(shopClientService.GetOneShopClientFromDatabase(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> DeleteClientById(@PathVariable @NotNull Long id){
        return ResponseEntity.ok(shopClientService.DeleteShopClientById(id));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> AddShopClientToDatabase(@RequestBody ShopClient shopClient){
        return ResponseEntity.ok(shopClientService.AddClientToDatabase(shopClient));
    }


    @DeleteMapping()
    public ResponseEntity<HttpStatus> DeleteClients(@RequestBody List<ShopClient> shopClientList){
        return ResponseEntity.ok(shopClientService.DeleteClients(shopClientList));
    }


    @PatchMapping()
    public ResponseEntity<ShopClient> UpdateClientData(@RequestBody ShopClient shopClient, @AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
        return ResponseEntity.ok(shopClientService.UpdateClientData(shopClient,user.getName()));
    }


}
