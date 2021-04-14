package ShopAppBackend.Client.ShopClient;

import lombok.Data;

public class ShopClientNotFound extends Throwable {

    public ShopClientNotFound(String error){
        System.out.println(error);
    }
}
