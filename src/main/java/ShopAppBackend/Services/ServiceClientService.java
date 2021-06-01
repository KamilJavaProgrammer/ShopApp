package ShopAppBackend.Services;


import ShopAppBackend.Repositories.ServiceClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceClientService {

    private ServiceClientRepository serviceClientRepository;

    @Autowired
    public ServiceClientService(ServiceClientRepository serviceClientRepository) {
        this.serviceClientRepository = serviceClientRepository;
    }
}
