package ShopAppBackend.ServiceClient.ShopClient;

public class ShopClientNotFound extends Throwable {

    public ShopClientNotFound(String error){
        System.out.println(error);
    }
}
