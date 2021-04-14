package ShopAppBackend.ServicePriceList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicePriceListRepo extends JpaRepository<ServicePriceList,Long> {
}
