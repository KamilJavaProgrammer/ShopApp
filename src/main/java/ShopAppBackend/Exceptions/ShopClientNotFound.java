package ShopAppBackend.Exceptions;

public class ShopClientNotFound extends RuntimeException {


    private static final String EXCEPTION_MESSAGE = "Shop Client not found";
    public ShopClientNotFound(){
        super(EXCEPTION_MESSAGE);
    }
}
