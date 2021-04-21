package ShopAppBackend.User;

public class UserNotFoundException extends Throwable {

    public UserNotFoundException(String codeFault) {
        System.out.println(codeFault);
    }
}
