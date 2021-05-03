package ShopAppBackend.User;

public class WrongLoginData extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Wrong Login or Password";

    public WrongLoginData(){
        super(EXCEPTION_MESSAGE);
    }
}
