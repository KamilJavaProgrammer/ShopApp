package ShopAppBackend.Repositories;

import ShopAppBackend.Entities.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepo extends JpaRepository<Business,Long> {
}
