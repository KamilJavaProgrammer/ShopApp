package ShopAppBackend.Exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFound extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Product not found";

    public ProductNotFound(){
        super(EXCEPTION_MESSAGE);

    }


}
