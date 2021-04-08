package untiled.serwer.serweruntitled.ServicePriceList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pricelist")
@CrossOrigin(origins = "http://localhost:4200")
public class ServicePriceListController {

    private final ServicePriceListService servicePriceListService;

    @Autowired
    public ServicePriceListController(ServicePriceListService servicePriceListService) {
        this.servicePriceListService = servicePriceListService;
    }

    @GetMapping()
    public ResponseEntity<?> GetAllPriceList(){
        return ResponseEntity.ok(servicePriceListService.GetAllPriceList());
    }
}
