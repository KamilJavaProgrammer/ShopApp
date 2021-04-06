package untiled.serwer.serweruntitled.ProductBasket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProductBasketController {

    private ProductBasketService productBasketService;

    @Autowired
    public ProductBasketController(ProductBasketService productBasketService) {
        this.productBasketService = productBasketService;
    }
}
