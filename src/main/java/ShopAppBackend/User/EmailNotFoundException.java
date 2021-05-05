package ShopAppBackend.User;

public class EmailNotFoundException extends RuntimeException{

    private static final String EXCEPTION_MESSAGE = "Email not found";
    public EmailNotFoundException(){
        super(EXCEPTION_MESSAGE);
    }
}
