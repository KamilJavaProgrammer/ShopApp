package ShopAppBackend.Controllers;


import ShopAppBackend.Services.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BusinessController {

   private BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/business")
    public ResponseEntity<?> GetAllBusiness(){
        return ResponseEntity.ok(businessService.GetAllBusinessFromDatabase());
    }
}
