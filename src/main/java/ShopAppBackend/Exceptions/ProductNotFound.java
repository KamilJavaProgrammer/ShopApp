package ShopAppBackend.Exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFound extends Throwable {


    public ProductNotFound(String error){
        this.ViewConsole(error);
    }

   public HttpStatus ViewConsole(String error){
       System.out.println(error);
       return HttpStatus.NOT_FOUND;
   }
}
