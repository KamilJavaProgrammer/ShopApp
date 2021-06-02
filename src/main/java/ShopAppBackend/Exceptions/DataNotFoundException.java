package ShopAppBackend.Exceptions;

public class DataNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Data not found";
    public DataNotFoundException(){
        super(EXCEPTION_MESSAGE);
    }
}
