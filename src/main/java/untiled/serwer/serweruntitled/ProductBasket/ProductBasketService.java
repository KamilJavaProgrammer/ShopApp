package untiled.serwer.serweruntitled.ProductBasket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductBasketService {

    private ProductBasketRepo productBasketRepo;

    @Autowired
    public ProductBasketService(ProductBasketRepo productBasketRepo) {
        this.productBasketRepo = productBasketRepo;
    }
}
