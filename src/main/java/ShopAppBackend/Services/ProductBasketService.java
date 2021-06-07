package ShopAppBackend.Services;


import ShopAppBackend.Repositories.ProductBasketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductBasketService {

    private final ProductBasketRepo productBasketRepo;

    @Autowired
    public ProductBasketService(ProductBasketRepo productBasketRepo) {
        this.productBasketRepo = productBasketRepo;
    }
}
