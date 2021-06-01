package ShopAppBackend.Repositories;

import ShopAppBackend.Entities.ServiceClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceClientRepository extends JpaRepository<ServiceClient,Long> {
}
