package ShopAppBackend.Controllers;


import ShopAppBackend.Services.ProductBasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProductBasketController{

    private final ProductBasketService productBasketService;

    @Autowired
    public ProductBasketController(ProductBasketService productBasketService) {
        this.productBasketService = productBasketService;
    }
}
