package ShopAppBackend.Controllers;


import ShopAppBackend.Entities.Business;
import ShopAppBackend.Services.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
public class BusinessController {

   private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/businesses")
    public ResponseEntity<List<Business>> GetAllBusiness(){
        return ResponseEntity.ok(businessService.GetAllBusinessFromDatabase());
    }
}
