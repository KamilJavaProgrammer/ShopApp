package ShopAppBackend.Controllers;

import ShopAppBackend.Services.ServiceClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ServiceClientController {

    private ServiceClientService serviceClientService;

    @Autowired
    public ServiceClientController(ServiceClientService serviceClientService) {
        this.serviceClientService = serviceClientService;
    }
}
