package ShopAppBackend.ProductBasket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBasketRepo extends JpaRepository<ProductBasket,Long> {
}
