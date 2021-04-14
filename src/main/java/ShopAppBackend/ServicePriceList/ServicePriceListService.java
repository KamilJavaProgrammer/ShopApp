package ShopAppBackend.ServicePriceList;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ServicePriceListService {

    private final ServicePriceListRepo servicePriceListRepo;

    @Autowired
    public ServicePriceListService(ServicePriceListRepo servicePriceListRepo) {
        this.servicePriceListRepo = servicePriceListRepo;
    }

    public ResponseEntity<List<ServicePriceList>> GetAllPriceList() {

        Stream<ServicePriceList> servicePriceListStream = servicePriceListRepo.findAll().stream();
      return ResponseEntity.status(HttpStatus.OK).body(servicePriceListStream.sorted(ServicePriceList::compareTo).collect(Collectors.toList()));

    }
}
